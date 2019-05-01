package ch.fhnw.justme;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.fhnw.justme.model.PictureDescription;

public class ShoppingActivity extends AppCompatActivity implements AmountChangedListener {

    private final static String ACTIVITY = "ShoppingActivity";
    private List<PictureDescription> possibilities;

    private RecyclerView recyclerView;
    private StoreItemAdapter adapter;

    double totalSum;
    TextView sumTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        possibilities = (List<PictureDescription>) getIntent().getExtras().get("possibilities");

        Log.d(ACTIVITY, possibilities.toString());


        setContentView(R.layout.shopping);

        sumTextView = findViewById(R.id.shopping_cart_total);

        recyclerView = findViewById(R.id.items);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);

        adapter = new StoreItemAdapter(possibilities, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAmountChanged(double value) {
        totalSum += value;
        sumTextView.setText(Double.toString(totalSum));
    }
 }
