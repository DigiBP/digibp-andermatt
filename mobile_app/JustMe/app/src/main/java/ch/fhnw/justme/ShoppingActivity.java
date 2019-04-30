package ch.fhnw.justme;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ShoppingActivity extends AppCompatActivity {

    private final static String ACTIVITY = "ShoppingActivity";
    private List<String> possibilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        possibilities = getIntent().getStringArrayListExtra("possibilities");

        Log.d(ACTIVITY, possibilities.toString());


        setContentView(R.layout.shopping);
    }
}
