package ch.fhnw.justme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import ch.fhnw.justme.messages.startprocess.StartProcessFormRequest;
import ch.fhnw.justme.model.ListVariable;
import ch.fhnw.justme.model.OrderingFormVariables;
import ch.fhnw.justme.model.PictureDescription;
import ch.fhnw.justme.model.ValueInfo;
import ch.fhnw.justme.model.Variable;
import ch.fhnw.justme.services.CamundaServices;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckoutActivity extends AppCompatActivity {

    private final static String ACTIVITY = "CheckoutActivity";

    private final static String PROCESS_KEY = "Process_Ordering";

    private List<PictureDescription> possibilities;
    private String producer;

    String creditCardNumber;
    Double totalAmount;
    String customerId;

    TextView cardNumberView;
    TextView amountView;

    Button approveButton;

    CamundaServices service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        possibilities = (List<PictureDescription>) getIntent().getExtras().get("possibilities");
        possibilities = removeItemsWithoutCount(possibilities);
        producer = (String) getIntent().getExtras().get("producer");

        Log.d(ACTIVITY, String.format("received the following possibilities: %S", possibilities.toString()));
        Log.d(ACTIVITY, String.format("received the following producer: %s", producer));

        initializeProcessService();

        SharedPreferences prefs = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        creditCardNumber = prefs.getString(getString(R.string.card_number_key), "");
        customerId = prefs.getString(getString(R.string.customer_id_key), "");
        totalAmount = getIntent().getDoubleExtra("totalAmount", 0.0d);

        setContentView(R.layout.checkout);

        cardNumberView = findViewById(R.id.cardNumber);
        amountView = findViewById(R.id.amount);
        approveButton = findViewById(R.id.approve);

        cardNumberView.setText(creditCardNumber);
        amountView.setText(totalAmount.toString());
        approveButton.setOnClickListener(v -> {
            StartProcessTask task = new StartProcessTask();
            task.execute();
            setContentView(R.layout.thankyou);
        });
    }

    private void initializeProcessService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://andermatt.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(CamundaServices.class);
    }

    private List<PictureDescription> removeItemsWithoutCount(List<PictureDescription> items) {
        List<PictureDescription> result = new ArrayList<PictureDescription>();

        items.forEach(item -> {
            if (item.getCount() > 0) {
                result.add(item);
            }
        });

        return result;
    }

    private class StartProcessTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StartProcessFormRequest req = new StartProcessFormRequest();

            OrderingFormVariables vars = new OrderingFormVariables();
            vars.setCardNumber(new Variable(creditCardNumber));
            ValueInfo valInf = new ValueInfo("application/json");
            vars.setCart(new ListVariable<PictureDescription>(possibilities, valInf));
            vars.setTotalAmount(new Variable(String.format("%.2f", totalAmount)));
            vars.setCustomerId(new Variable(customerId));

            req.setVariables(vars);
            try {
                //service.startProcess(PROCESS_KEY, req).execute();
                service.startProcess(PROCESS_KEY, req).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Did synthesize";
        }
    }

}
