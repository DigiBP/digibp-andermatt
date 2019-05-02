package ch.fhnw.justme;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import ch.fhnw.justme.services.CamundaServices;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckoutActivity extends AppCompatActivity {

    private final static String PROCESS_KEY = "Process_Ordering";

    String creditCardNumber;
    Double totalAmount;

    TextView cardNumberView;
    TextView amountView;

    Button approveButton;

    CamundaServices service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeProcessService();

        creditCardNumber = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).getString(getString(R.string.card_number_key), "");
        totalAmount = getIntent().getDoubleExtra("totalAmount", 0.0d);

        setContentView(R.layout.checkout);

        cardNumberView = findViewById(R.id.cardNumber);
        amountView = findViewById(R.id.amount);
        approveButton = findViewById(R.id.approve);

        cardNumberView.setText(creditCardNumber);
        amountView.setText(totalAmount.toString());
        approveButton.setOnClickListener(v -> {
            service.startProcess(PROCESS_KEY, null);
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

}
