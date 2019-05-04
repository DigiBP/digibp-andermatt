package ch.fhnw.justme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.fhnw.justme.model.PictureDescription;

public class ShoppingActivity extends AppCompatActivity implements AmountChangedListener {

    private final static String ACTIVITY = "ShoppingActivity";
    private List<PictureDescription> possibilities;
    private String producer;

    private RecyclerView recyclerView;
    private StoreItemAdapter adapter;

    double totalSum;
    TextView sumTextView;

    ImageButton checkoutButton;

    private List<PictureDescription> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cart = new ArrayList<PictureDescription>();

        possibilities = (List<PictureDescription>) getIntent().getExtras().get("possibilities");
        producer = (String) getIntent().getExtras().get("producer");

        Log.d(ACTIVITY, String.format("received the following possibilities: %S", possibilities.toString()));
        Log.d(ACTIVITY, String.format("received the following producer: %s", producer));

        setContentView(R.layout.shopping);

        sumTextView = findViewById(R.id.shopping_cart_total);
        checkoutButton = findViewById(R.id.buy);

        checkoutButton.setOnClickListener(v -> startCheckoutActivity());

        recyclerView = findViewById(R.id.items);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);

        adapter = new StoreItemAdapter(possibilities, this);
        recyclerView.setAdapter(adapter);
    }

    private void startCheckoutActivity() {
        Intent intent = new Intent(ShoppingActivity.this, CheckoutActivity.class);
        intent.putExtra("totalAmount", totalSum);
        intent.putExtra("producer", producer);
        intent.putExtra("possibilities", new ArrayList<PictureDescription>(possibilities));
        startActivity(intent);
    }

    @Override
    public void onAmountChanged(double value) {
        totalSum += value;
        sumTextView.setText(Double.toString(totalSum));
    }
 }
