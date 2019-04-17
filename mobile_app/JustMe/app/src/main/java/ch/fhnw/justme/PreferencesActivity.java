package ch.fhnw.justme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import ch.fhnw.justme.databinding.PreferencesBinding;

public class PreferencesActivity extends AppCompatActivity {

    private static final String ACTIVITY = "PreferencesActivity";
    private final String USERNAME = "username";

    TextView username;
    TextView password;
    SharedPreferences sharedPref;
    private Preferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getPreferences(Context.MODE_PRIVATE);

        if (!sharedPref.contains(USERNAME)) {
            setContentView(R.layout.register);
            username = this.findViewById(R.id.username);
            password = this.findViewById(R.id.password);
        } else {
            Intent watson = new Intent(PreferencesActivity.this, WatsonActivity.class);
            startActivity(watson);
        }
    }

    public void register(View view) {
        boolean errorOccurred = false;

        if (username.getText() == null || username.getText().toString().isEmpty()) {
            username.setError("username required");
            errorOccurred = true;
        }

        if (password.getText() == null || password.getText().toString().isEmpty()) {
            password.setError("password required");
            errorOccurred = true;
        }

        if (errorOccurred) {
            return;
        }
        // TODO: API call: getCredentials(username)
        // TODO: API call: setCredentials(username, password)
        sharedPref.edit().putString(username.getText().toString(), password.getText().toString()).apply();

        this.prefs = new Preferences();
        PreferencesBinding binding = DataBindingUtil.setContentView(this, R.layout.preferences);
        binding.setPref(prefs);
    }

    public void save(View view) {
        // TODO validate fields
        // TODO API savePreferences(prefs)
        Log.d(ACTIVITY, prefs.toString());

        Intent watson = new Intent(PreferencesActivity.this, WatsonActivity.class);
        startActivity(watson);
    }

    public class Preferences {
        private String cardHolderName;
        private String cardNumber;
        private String cvc;
        private String yy;
        private String mm;
        private String addressLineOne;
        private String addressLineTwo;
        private String postcode;
        private String town;

        public Preferences() {}

        public Preferences(String cardHolderName, String cardNumber, String cvc, String yy, String mm, String addressLineOne, String addressLineTwo, String postcode, String town) {
            this.cardHolderName = cardHolderName;
            this.cardNumber = cardNumber;
            this.cvc = cvc;
            this.yy = yy;
            this.mm = mm;
            this.addressLineOne = addressLineOne;
            this.addressLineTwo = addressLineTwo;
            this.postcode = postcode;
            this.town = town;
        }

        public String getCardHolderName() {
            return cardHolderName;
        }

        public void setCardHolderName(String cardHolderName) {
            this.cardHolderName = cardHolderName;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCvc() {
            return cvc;
        }

        public void setCvc(String cvc) {
            this.cvc = cvc;
        }

        public String getYy() {
            return yy;
        }

        public void setYy(String yy) {
            this.yy = yy;
        }

        public String getMm() {
            return mm;
        }

        public void setMm(String mm) {
            this.mm = mm;
        }

        public String getAddressLineOne() {
            return addressLineOne;
        }

        public void setAddressLineOne(String addressLineOne) {
            this.addressLineOne = addressLineOne;
        }

        public String getAddressLineTwo() {
            return addressLineTwo;
        }

        public void setAddressLineTwo(String addressLineTwo) {
            this.addressLineTwo = addressLineTwo;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        @Override
        public String toString() {
            return "Preferences{" +
                    "cardHolderName='" + cardHolderName + '\'' +
                    ", cardNumber='" + cardNumber + '\'' +
                    ", cvc='" + cvc + '\'' +
                    ", yy='" + yy + '\'' +
                    ", mm='" + mm + '\'' +
                    ", addressLineOne='" + addressLineOne + '\'' +
                    ", addressLineTwo='" + addressLineTwo + '\'' +
                    ", postcode='" + postcode + '\'' +
                    ", town='" + town + '\'' +
                    '}';
        }
    }
}
