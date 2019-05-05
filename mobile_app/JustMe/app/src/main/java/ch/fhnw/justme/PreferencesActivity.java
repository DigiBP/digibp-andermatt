package ch.fhnw.justme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import ch.fhnw.justme.databinding.PreferencesBinding;
import ch.fhnw.justme.messages.startprocess.StartProcessFormRequest;
import ch.fhnw.justme.messages.startprocess.StartProcessFormResponse;
import ch.fhnw.justme.model.NewCustomerFormVariables;
import ch.fhnw.justme.model.Variable;
import ch.fhnw.justme.services.CamundaServices;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreferencesActivity extends AppCompatActivity {

    private static final String ACTIVITY = "PreferencesActivity";

    SharedPreferences sharedPref;
    private Preferences prefs;

    CamundaServices service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.prefs = new Preferences();
        PreferencesBinding binding = DataBindingUtil.setContentView(this, R.layout.preferences);
        binding.setPref(prefs);

        sharedPref = getPreferences(Context.MODE_PRIVATE);

        initializeProcessService();
    }

    public void save(View view) {
        // TODO validate fields
        Log.d(ACTIVITY, prefs.toString());

        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
        editor.putString(getString(R.string.card_number_key), prefs.getCardNumber());
        editor.putString(getString(R.string.full_name_key), prefs.getFullName());
        editor.commit();

        StartProcessTask task = new StartProcessTask();
        task.execute();

        Intent watson = new Intent(PreferencesActivity.this, WatsonActivity.class);
        startActivity(watson);
    }

    private void initializeProcessService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://andermatt.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(CamundaServices.class);
    }

    private class StartProcessTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            StartProcessFormRequest request = new StartProcessFormRequest();
            NewCustomerFormVariables variables = new NewCustomerFormVariables();
            variables.setFullName(new Variable(prefs.getFullName()));
            variables.setAddressLineOne(new Variable(prefs.getAddressLineOne()));
            variables.setAddressLineTwo(new Variable(prefs.getAddressLineTwo()));
            variables.setCardHolderName(new Variable(prefs.getCardHolderName()));
            variables.setCardNumber(new Variable(prefs.getCardNumber()));
            variables.setCvc(new Variable(prefs.getCvc()));
            variables.setMm(new Variable(prefs.getMm()));
            variables.setYy(new Variable(prefs.getYy()));
            variables.setPostcode(new Variable(prefs.getPostcode()));
            variables.setTown(new Variable(prefs.getTown()));

            request.setVariables(variables);
            request.setWithVariablesInReturn(true);

            Call<StartProcessFormResponse<NewCustomerFormVariables>> call = service.startProcessNewCustomer(request);
            try {
                Response<StartProcessFormResponse<NewCustomerFormVariables>> response = call.execute();
                String customerId = response.body().getVariables().getCustomerId().getValue();

                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();
                editor.putString(getString(R.string.customer_id_key), customerId);
                editor.commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Did store";
        }
    }

    public class Preferences {
        private String fullName;
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

        public Preferences(String fullName, String cardHolderName, String cardNumber, String cvc, String yy, String mm, String addressLineOne, String addressLineTwo, String postcode, String town) {
            this.fullName = fullName;
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

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
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
                    "fullName='" + fullName + '\'' +
                    ", cardHolderName='" + cardHolderName + '\'' +
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
