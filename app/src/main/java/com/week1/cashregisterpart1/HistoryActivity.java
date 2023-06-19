package com.week1.cashregisterpart1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Initialize the ListView
        ListView listView = findViewById(R.id.product_listview);
        Button backButton = findViewById(R.id.buttonBack);

        // Initialize the adapter and set it to the ListView
        PurchasedProductAdapter adapter = new PurchasedProductAdapter(this, ProductManager.getInstance().getPurchasedProducts());
        listView.setAdapter(adapter);

        // back button to go back to manage activity
        backButton.setOnClickListener(v -> {
            finish();
        });

    }
}