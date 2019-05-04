package ch.fhnw.justme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneHelper;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneInputStream;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.android.library.audio.utils.ContentType;
import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper;
import com.ibm.watson.developer_cloud.android.library.camera.GalleryHelper;
import com.ibm.watson.developer_cloud.assistant.v2.Assistant;
import com.ibm.watson.developer_cloud.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.developer_cloud.assistant.v2.model.DialogRuntimeResponseGeneric;
import com.ibm.watson.developer_cloud.assistant.v2.model.MessageInput;
import com.ibm.watson.developer_cloud.assistant.v2.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v2.model.MessageResponse;
import com.ibm.watson.developer_cloud.assistant.v2.model.RuntimeEntity;
import com.ibm.watson.developer_cloud.assistant.v2.model.SessionResponse;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.fhnw.justme.messages.getvariable.GetVariableResponse;
import ch.fhnw.justme.messages.message.CorrelateMessageRequest;
import ch.fhnw.justme.messages.startprocess.StartProcessFormRequest;
import ch.fhnw.justme.messages.startprocess.StartProcessFormResponse;
import ch.fhnw.justme.model.Message;
import ch.fhnw.justme.model.PictureDescription;
import ch.fhnw.justme.model.SuggestionsFormVariables;
import ch.fhnw.justme.model.Variable;
import ch.fhnw.justme.services.CamundaServices;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WatsonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ChatEntryAdapter adapter;

    private ArrayList<Message> chatEntries = new ArrayList<>();

    private TextView inputMessage;
    private Button btnRecord;

    private Context context;

    private final static String ACTIVITY = "WatsonActivity";

    StreamPlayer streamPlayer = new StreamPlayer();
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int RECORD_REQUEST_CODE = 101;
    private MicrophoneInputStream capture;
    private MicrophoneHelper microphoneHelper;

    private Assistant watsonAssistant;
    private SessionResponse watsonAssistantSession;
    private SpeechToText speechService;
    private TextToSpeech textToSpeech;
    private boolean listening = false;

    GalleryHelper galleryHelper;
    CameraHelper cameraHelper;

    CamundaServices service;
    private static final String processKey = "Process_Suggestions";
    private String processInstanceId;
    private static final String WAIT_MESSAGE = "WaitMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.watson_activity);

        recyclerView = findViewById(R.id.chat_history);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setStackFromEnd(true);
        recyclerView.setLayoutManager(layout);

        this.inputMessage = this.findViewById(R.id.editText);
        this.btnRecord = this.findViewById(R.id.mic_button);

        this.context = getApplicationContext();

        adapter = new ChatEntryAdapter(chatEntries);
        recyclerView.setAdapter(adapter);

        microphoneHelper = new MicrophoneHelper(this);
        galleryHelper = new GalleryHelper(this);
        cameraHelper = new CameraHelper(this);

        ensureAudioRecordPermission();
        ensureFileReadPermission();

        initChatbot();
        initializeProcessService();
    }

    private void initializeProcessService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://andermatt.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(CamundaServices.class);
    }

    private void ensureAudioRecordPermission() {
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(ACTIVITY, "Permission to record denied");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    MicrophoneHelper.REQUEST_PERMISSION);
        } else {
            Log.i(ACTIVITY, "Permission to record was already granted");
        }
    }

    private void ensureFileReadPermission() {
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(ACTIVITY, "Permission to record denied");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    GalleryHelper.PICK_IMAGE_REQUEST);
        } else {
            Log.i(ACTIVITY, "Permission to record was already granted");
        }
    }

    private void initChatbot() {
        watsonAssistant = new Assistant("2019-08-04", new IamOptions.Builder()
                .apiKey(context.getString(R.string.assistant_apikey))
                .build());
        watsonAssistant.setEndPoint(context.getString(R.string.assistant_url));

        // TODO: commented out to save a bit requests for testing
        sendChatbotMessage(new Message(this.inputMessage.getText().toString().trim(), Message.Sender.USER));

        textToSpeech = new TextToSpeech();
        textToSpeech.setIamCredentials(new IamOptions.Builder()
                .apiKey(context.getString(R.string.TTS_apikey))
                .build());
        textToSpeech.setEndPoint(context.getString(R.string.TTS_url));

        speechService = new SpeechToText();
        speechService.setIamCredentials(new IamOptions.Builder()
                .apiKey(context.getString(R.string.STT_apikey))
                .build());
        speechService.setEndPoint(context.getString(R.string.STT_url));
    }

    public void sendMessage(View view) {
        if (checkInternetConnection()) {
            TextView text = this.findViewById(R.id.editText);
            Message message = new Message(text.getText().toString(), Message.Sender.USER);
            this.chatEntries.add(message);
            this.adapter.notifyDataSetChanged();

            sendChatbotMessage(message);
        }
    }

    public void recordAudio(View view) {
        this.recordMessage();
    }

    private void sendChatbotMessage(final Message inputMessage) {
        this.inputMessage.setText("");
        adapter.notifyDataSetChanged();

        Thread thread = new Thread(() -> {
            try {
                if (watsonAssistantSession == null) {
                    ServiceCall<SessionResponse> call = watsonAssistant.createSession(new CreateSessionOptions.Builder().assistantId(context.getString(R.string.assistant_id)).build());
                    watsonAssistantSession = call.execute();
                }

                MessageInput input = new MessageInput.Builder()
                        .text(inputMessage.getText())
                        .build();
                MessageOptions options = new MessageOptions.Builder()
                        .assistantId(context.getString(R.string.assistant_id))
                        .input(input)
                        .sessionId(watsonAssistantSession.getSessionId())
                        .build();
                MessageResponse response = watsonAssistant.message(options).execute();
                Log.i(ACTIVITY, "run: "+response);
                if (response != null &&
                        response.getOutput() != null &&
                        !response.getOutput().getGeneric().isEmpty() &&
                        DialogRuntimeResponseGeneric.ResponseType.TEXT.equals(response.getOutput().getGeneric().get(0).getResponseType())) {

                    Message resMessage = new Message(response.getOutput().getGeneric().get(0).getText(), Message.Sender.BOT);
                    chatEntries.add(resMessage);

                    // the conversation has ended - start the process
                    if (response.getOutput().getGeneric().size() > 1 && DialogRuntimeResponseGeneric.ResponseType.PAUSE.equals(response.getOutput().getGeneric().get(1).getResponseType())) {
                        startClothingRecommendationProcess(processKey, response.getOutput().getEntities());
                    }

                    // speak the message
                    new SayTask().execute(resMessage.getText());

                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                        if (adapter.getItemCount() > 1) {
                            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, adapter.getItemCount() - 1);

                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }

    private void startClothingRecommendationProcess(String key, List<RuntimeEntity> entities) {
        StartProcessFormRequest request = new StartProcessFormRequest();
        SuggestionsFormVariables vars = new SuggestionsFormVariables();

        for (int i = 0; i < entities.size(); i++) {
            if ("clothing".equals(entities.get(i).getEntity())) {
                vars.setClothing(new Variable(entities.get(i).getValue()));
            }

            if ("color".equals(entities.get(i).getEntity())) {
                vars.setColor(new Variable(entities.get(i).getValue()));
            }
        }

        request.setVariables(vars);

        Call<StartProcessFormResponse> call = service.startProcess(key, request);
        Log.d(ACTIVITY, String.format("What did I built? %s", call.request()));
        try {
            Response<StartProcessFormResponse> response = call.execute();
            processInstanceId = response.body().getId();
            checkVariables();
            Log.d(ACTIVITY, String.format("What did I get? %s", response.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkVariables() {
        Call<GetVariableResponse> call = service.getVariables(processInstanceId);
        boolean ready = false;
        List<PictureDescription> possibilities = null;
        String producer = null;
        try {
            Response<GetVariableResponse> res = call.execute();
            Log.d(ACTIVITY, String.format("received variables: %s", res.body().toString()));
            ready = "true".equals(res.body().getReadyForPickup().getValue());
            ObjectMapper mapper = new ObjectMapper();
            possibilities =  mapper.readValue(res.body().getPossibilities().getValue(), new TypeReference<List<PictureDescription>>(){});
            Log.d(ACTIVITY, String.format("Read the following possibilities: %s", possibilities.toString()));

            producer = res.body().getProducer().getValue();
            Log.d(ACTIVITY, String.format("Read the following producer: %s", producer));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ready) {
            triggerWaitMessage(possibilities, producer);
        } else {
            runOnUiThread(() -> {
                Handler handler = new Handler();
                handler.postDelayed(() -> checkVariables(), 1000);
            });
        }
    }

    private void triggerWaitMessage(List<PictureDescription> possibilities, String producer) {
        Log.d(ACTIVITY, "triggering wait message");
        CorrelateMessageRequest req = new CorrelateMessageRequest(WAIT_MESSAGE, processInstanceId);
        Call call = service.correlateMessage(req);
        try {
            Response res = call.execute();
            if (res.isSuccessful()) {
                Intent shopping = new Intent(WatsonActivity.this, ShoppingActivity.class);
                Log.d(ACTIVITY, String.format("passing possibilities to ShoppingActivity: %s", possibilities));
                shopping.putExtra("possibilities", new ArrayList<PictureDescription>(possibilities));
                shopping.putExtra("producer", producer);
                startActivity(shopping);
            } else {
                Log.e(ACTIVITY, String.format("Something went wrong; response: %s", res.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Speech-to-Text Record Audio permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case MicrophoneHelper.REQUEST_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission to record audio denied", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    /**
     * Check Internet Connection
     *
     * @return
     */
    private boolean checkInternetConnection() {
        // get Connectivity Manager object to check connection
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        // Check for network connections
        if (isConnected) {
            return true;
        } else {
            Toast.makeText(this, " No Internet Connection available ", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    private class SayTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            streamPlayer.playStream(textToSpeech.synthesize(new SynthesizeOptions.Builder()
                    .text(params[0])
                    .voice(SynthesizeOptions.Voice.EN_US_LISAVOICE)
                    .accept(SynthesizeOptions.Accept.AUDIO_WAV)
                    .build()).execute());
            return "Did synthesize";
        }
    }

    //Record a message via Watson Speech to Text
    private void recordMessage() {
        if (!listening) {
            capture = microphoneHelper.getInputStream(true);
            new Thread(() -> {
                try {
                    speechService.recognizeUsingWebSocket(getRecognizeOptions(capture), new MicrophoneRecognizeDelegate());
                } catch (Exception e) {
                    showError(e);
                }
            }).start();
            listening = true;
            Toast.makeText(WatsonActivity.this, "Listening....Click to Stop", Toast.LENGTH_LONG).show();

        } else {
            try {
                microphoneHelper.closeInputStream();
                listening = false;
                Toast.makeText(WatsonActivity.this, "Stopped Listening....Click to Start", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //Private Methods - Speech to Text
    private RecognizeOptions getRecognizeOptions(InputStream audio) {
        return new RecognizeOptions.Builder()
                .audio(audio)
                .contentType(ContentType.OPUS.toString())
                .model(RecognizeOptions.Model.EN_GB_BROADBANDMODEL)
                .interimResults(true)
                .inactivityTimeout(2000)
                .build();
    }

    //Watson Speech to Text Methods.
    private class MicrophoneRecognizeDelegate extends BaseRecognizeCallback {
        @Override
        public void onTranscription(SpeechRecognitionResults speechResults) {
            if (speechResults.getResults() != null && !speechResults.getResults().isEmpty()) {
                String text = speechResults.getResults().get(0).getAlternatives().get(0).getTranscript();
                showMicText(text);
            }
        }

        @Override
        public void onError(Exception e) {
            showError(e);
            enableMicButton();
        }

        @Override
        public void onDisconnected() {
            enableMicButton();
        }

    }

    private void showMicText(final String text) {
        runOnUiThread(() -> inputMessage.setText(text));
    }

    private void enableMicButton() {
        runOnUiThread(() -> btnRecord.setEnabled(true));
    }

    private void showError(final Exception e) {
        runOnUiThread(() -> {
            Toast.makeText(WatsonActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        });
    }

    public void galleryUpload(View view) {
        galleryHelper.dispatchGalleryIntent();
        //cameraHelper.dispatchTakePictureIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryHelper.PICK_IMAGE_REQUEST) {
            System.out.println(galleryHelper.getFile(resultCode, data));
            classifyImage(galleryHelper.getFile(resultCode, data));
        }

        if (requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
            System.out.println(cameraHelper.getFile(resultCode));
            classifyImage(cameraHelper.getFile(resultCode));
        }
    }

    private void displayImageClassificationResult(List<ClassResult> classifications) {
        classifications.sort((ClassResult res1, ClassResult res2) -> (int) Math.signum(res1.getScore() - res2.getScore()));

        StringBuilder sb = new StringBuilder();
        classifications.forEach((ClassResult r) -> {
            sb.append(r.getClassName());
            sb.append(' ');
        });

        Log.d(ACTIVITY, String.format("classifications: %s", sb.toString()));

        this.sendChatbotMessage(new Message(sb.toString(), Message.Sender.USER));
    }

    private void classifyImage(File picture) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(picture.getPath(), options);

        chatEntries.add(new Message(bitmap, Message.Sender.USER_IMAGE));

        if (picture == null) {
            Log.d(ACTIVITY, "classifyImage started without an argument");
            return;
        }

        Thread thread = new Thread(() -> {

            Log.d(ACTIVITY, String.format("classifyImage started with argument %s", picture.toString()));

            Log.d(ACTIVITY, "Image? " + picture.getUsableSpace());

            IamOptions iamOptions = new IamOptions.Builder()
                    .apiKey(context.getString(R.string.VR_apikey))
                    .build();

            VisualRecognition visualRecognition = new VisualRecognition("2019-04-10", iamOptions);

            ClassifyOptions classifyOptions = null;
            try {
                classifyOptions = new ClassifyOptions.Builder()
                        .imagesFile(picture)
                        .build();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            assert classifyOptions != null;

            try {
                ClassifiedImages result = visualRecognition.classify(classifyOptions).execute();

                runOnUiThread(() -> {
                    displayImageClassificationResult(result.getImages().get(0).getClassifiers().get(0).getClasses());
                    if (adapter.getItemCount() > 1) {
                        recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, adapter.getItemCount() - 1);
                    }
                });
            } catch (RuntimeException e) {
                runOnUiThread(() -> {
                    chatEntries.add(new Message("This picture looks odd. Can you try something else?", Message.Sender.BOT));
                    this.adapter.notifyDataSetChanged();
                    e.printStackTrace();
                });
            }
        });
        thread.start();
    }
}
