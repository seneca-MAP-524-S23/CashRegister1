package com.week1.cashregisterpart1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ManagerActivity extends AppCompatActivity {
    private Button restockButton, backButton, historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        // Initialize the ListView and Buttons
        restockButton = findViewById(R.id.buttonRestock);
        backButton = findViewById(R.id.buttonBack);
        historyButton = findViewById(R.id.buttonHistory);

        // click listeners
        clickListeners();
    }

    private void clickListeners() {
        restockButton.setOnClickListener(v -> {
            Intent intent = new Intent(ManagerActivity.this, RestockActivity.class);
            startActivity(intent);
        });

        backButton.setOnClickListener(v -> finish());

        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(ManagerActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }
}