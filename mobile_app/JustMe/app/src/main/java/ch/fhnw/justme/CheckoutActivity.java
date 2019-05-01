package ch.fhnw.justme;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {

    String creditCardNumber;
    Double totalAmount;

    TextView cardNumberView;
    TextView amountView;

    Button approveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        creditCardNumber = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).getString(getString(R.string.card_number_key), "");
        totalAmount = getIntent().getDoubleExtra("totalAmount", 0.0d);

        setContentView(R.layout.checkout);

        cardNumberView = findViewById(R.id.cardNumber);
        amountView = findViewById(R.id.amount);
        approveButton = findViewById(R.id.approve);

        cardNumberView.setText(creditCardNumber);
        amountView.setText(totalAmount.toString());
        approveButton.setOnClickListener(v -> {
            setContentView(R.layout.thankyou);
        });
    }

}
