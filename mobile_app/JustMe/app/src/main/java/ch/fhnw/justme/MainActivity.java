package ch.fhnw.justme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.ApiStreamObserver;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.AudioEncoding;
import com.google.cloud.dialogflow.v2.DetectIntentRequest;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.InputAudioConfig;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.StreamingDetectIntentRequest;
import com.google.cloud.dialogflow.v2.StreamingDetectIntentResponse;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.protobuf.ByteString;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ChatEntryAdapter adapter;

    private ArrayList<Message> chatEntries = new ArrayList<>();

    private SessionsClient sessionsClient;
    private SessionName sessionName;

    private final static String LANGUAGE_CODE = "en-GB";

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private MediaRecorder recorder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initV2Chatbot();

        recyclerView = findViewById(R.id.chat_history);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setStackFromEnd(true);
        recyclerView.setLayoutManager(layout);

        // initial
        QueryResult res = null;
        try {
            res = detectIntentTexts("Hello", LANGUAGE_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.chatEntries.add(new Message(res.getFulfillmentText(), Message.Sender.BOT));

        adapter = new ChatEntryAdapter(chatEntries);
        recyclerView.setAdapter(adapter);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

    public void sendMessage(View view) throws Exception {
        TextView text = this.findViewById(R.id.editText);
        this.chatEntries.add(new Message(text.getText().toString(), Message.Sender.USER));
        this.adapter.notifyDataSetChanged();

        QueryResult res = detectIntentTexts(text.getText().toString(), "en-GB");
        this.chatEntries.add(new Message(res.getFulfillmentText(), Message.Sender.BOT));
        this.adapter.notifyDataSetChanged();
    }

    private boolean recording = false;

    public void recordAudio(View view) throws Exception {
        File file = new File(getExternalCacheDir(), "audiostreaming.3gp");
        if (!recording) {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile(file.getName());
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                recorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            recorder.start();

            recording = true;
        } else {
            recorder.stop();
            recorder.release();
            recorder = null;

            QueryResult res = detectIntentAudio(file.getPath(), LANGUAGE_CODE);
            this.chatEntries.add(new Message(res.getFulfillmentText(), Message.Sender.BOT));
            this.adapter.notifyDataSetChanged();

            recording = false;
            file.delete();
        }
    }

    private void initV2Chatbot() {
        try {
            InputStream stream = getResources().openRawResource(R.raw.justme_fb9d71df4e3a);
            GoogleCredentials cred = GoogleCredentials.fromStream(stream);
            String projectId = ((ServiceAccountCredentials) cred).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(cred)).build();
            this.sessionsClient = SessionsClient.create(sessionsSettings);
            this.sessionName = SessionName.of(projectId, "my-first-session");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the result of detect intent with an audio file as input.
     *
     * Using the same `session_id` between requests allows continuation of the conversation.
     *
     * @param audioFilePath Path to the audio file.
     * @param languageCode  Language code of the query.
     * @return QueryResult for the request.
     */
    public QueryResult detectIntentAudio(
            String audioFilePath,
            String languageCode)
            throws Exception {

        // Note: hard coding audioEncoding and sampleRateHertz for simplicity.
        // Audio encoding of the audio content sent in the query request.
        AudioEncoding audioEncoding = AudioEncoding.AUDIO_ENCODING_LINEAR_16;
        int sampleRateHertz = 16000;

        // Instructs the speech recognizer how to process the audio content.
        InputAudioConfig inputAudioConfig = InputAudioConfig.newBuilder()
                .setAudioEncoding(audioEncoding) // audioEncoding = AudioEncoding.AUDIO_ENCODING_LINEAR_16
                .setLanguageCode(languageCode) // languageCode = "en-US"
                .setSampleRateHertz(sampleRateHertz) // sampleRateHertz = 16000
                .build();

        // Build the query with the InputAudioConfig
        QueryInput queryInput = QueryInput.newBuilder().setAudioConfig(inputAudioConfig).build();

        // Read the bytes from the audio file
        byte[] inputAudio = Files.readAllBytes(Paths.get(audioFilePath));

        // Build the DetectIntentRequest
        DetectIntentRequest request = DetectIntentRequest.newBuilder()
                .setSession(this.sessionName.toString())
                .setQueryInput(queryInput)
                .setInputAudio(ByteString.copyFrom(inputAudio))
                .build();

        // Performs the detect intent request
        DetectIntentResponse response = this.sessionsClient.detectIntent(request);

        // Display the query result
        QueryResult queryResult = response.getQueryResult();
        System.out.println("====================");
        System.out.format("Query Text: '%s'\n", queryResult.getQueryText());
        System.out.format("Detected Intent: %s (confidence: %f)\n",
                queryResult.getIntent().getDisplayName(), queryResult.getIntentDetectionConfidence());
        System.out.format("Fulfillment Text: '%s'\n", queryResult.getFulfillmentText());
        System.out.format("What is in my intent? %s", queryResult.getIntent().toString());

        return queryResult;
    }

    /**
     * Returns the result of detect intent with texts as inputs.
     *
     * Using the same `session_id` between requests allows continuation of the conversation.
     *
     * @param text        The text intents to be detected based on what a user says.
     * @param languageCode Language code of the query.
     * @return The QueryResult for each input text.
     */
    public QueryResult detectIntentTexts(
            String text,
            String languageCode) throws Exception {

        // Set the text (hello) and language code (en-US) for the query
        TextInput.Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode(languageCode);

        // Build the query with the TextInput
        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
        // Performs the detect intent request
        DetectIntentResponse response = this.sessionsClient.detectIntent(this.sessionName, queryInput);

        // Display the query result
        QueryResult queryResult = response.getQueryResult();

        System.out.println("====================");
        System.out.format("Query Text: '%s'\n", queryResult.getQueryText());
        System.out.format("Detected Intent: %s (confidence: %f)\n",
                queryResult.getIntent().getDisplayName(), queryResult.getIntentDetectionConfidence());
        System.out.format("Fulfillment Text: '%s'\n", queryResult.getFulfillmentText());

        return queryResult;
    }

    /**
     * Returns the result of detect intent with streaming audio as input.
     *
     * Using the same `session_id` between requests allows continuation of the conversation.
     *
     * @param audioFilePath The audio file to be processed.
     * @param languageCode  Language code of the query.
     * @return The List of StreamingDetectIntentResponses to the input audio inputs.
     */
    public List<StreamingDetectIntentResponse> detectIntentStream(
            String audioFilePath,
            String languageCode) throws Throwable {
        // Start bi-directional StreamingDetectIntent stream.
        final CountDownLatch notification = new CountDownLatch(1);
        final List<Throwable> responseThrowables = new ArrayList<>();
        final List<StreamingDetectIntentResponse> responses = new ArrayList<>();

        // Note: hard coding audioEncoding and sampleRateHertz for simplicity.
        // Audio encoding of the audio content sent in the query request.
        AudioEncoding audioEncoding = AudioEncoding.AUDIO_ENCODING_LINEAR_16;
        int sampleRateHertz = 16000;

        // Instructs the speech recognizer how to process the audio content.
        InputAudioConfig inputAudioConfig = InputAudioConfig.newBuilder()
                .setAudioEncoding(audioEncoding) // audioEncoding = AudioEncoding.AUDIO_ENCODING_LINEAR_16
                .setLanguageCode(languageCode) // languageCode = "en-US"
                .setSampleRateHertz(sampleRateHertz) // sampleRateHertz = 16000
                .build();

        ApiStreamObserver<StreamingDetectIntentResponse> responseObserver =
                new ApiStreamObserver<StreamingDetectIntentResponse>() {
                    @Override
                    public void onNext(StreamingDetectIntentResponse response) {
                        // Do something when receive a response
                        responses.add(response);
                    }

                    @Override
                    public void onError(Throwable t) {
                        // Add error-handling
                        responseThrowables.add(t);
                    }

                    @Override
                    public void onCompleted() {
                        // Do something when complete.
                        notification.countDown();
                    }
                };

        // Performs the streaming detect intent callable request
        ApiStreamObserver<StreamingDetectIntentRequest> requestObserver =
                sessionsClient.streamingDetectIntentCallable().bidiStreamingCall(responseObserver);

        // Build the query with the InputAudioConfig
        QueryInput queryInput = QueryInput.newBuilder().setAudioConfig(inputAudioConfig).build();

        try (FileInputStream audioStream = new FileInputStream(audioFilePath)) {
            // The first request contains the configuration
            StreamingDetectIntentRequest request = StreamingDetectIntentRequest.newBuilder()
                    .setSession(this.sessionName.toString())
                    .setSingleUtterance(true)
                    .setQueryInput(queryInput)
                    .build();

            // Make the first request
            requestObserver.onNext(request);

            // Following messages: audio chunks. We just read the file in fixed-size chunks. In reality
            // you would split the user input by time.
            byte[] buffer = new byte[4096];
            int bytes;
            while ((bytes = audioStream.read(buffer)) != -1) {
                requestObserver.onNext(
                        StreamingDetectIntentRequest.newBuilder()
                                .setInputAudio(ByteString.copyFrom(buffer, 0, bytes))
                                .build());
            }
        } catch (RuntimeException e) {
            // Cancel stream.
            requestObserver.onError(e);
        }
        // Half-close the stream.
        requestObserver.onCompleted();
        // Wait for the final response (without explicit timeout).
        notification.await();
        // Process errors/responses.
        if (!responseThrowables.isEmpty()) {
            throw responseThrowables.get(0);
        }
        if (responses.isEmpty()) {
            throw new RuntimeException("No response from Dialogflow.");
        }

        for (StreamingDetectIntentResponse response : responses) {
            if (response.hasRecognitionResult()) {
                System.out.format(
                        "Intermediate transcript: '%s'\n", response.getRecognitionResult().getTranscript());
            }
        }

        // Display the last query result
        QueryResult queryResult = responses.get(responses.size() - 1).getQueryResult();
        System.out.println("====================");
        System.out.format("Query Text: '%s'\n", queryResult.getQueryText());
        System.out.format("Detected Intent: %s (confidence: %f)\n",
                queryResult.getIntent().getDisplayName(), queryResult.getIntentDetectionConfidence());
        System.out.format("Fulfillment Text: '%s'\n", queryResult.getFulfillmentText());

        return responses;
    }
}
