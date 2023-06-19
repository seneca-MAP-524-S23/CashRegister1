package com.week1.cashregisterpart1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RestockActivity extends AppCompatActivity {
    private ListView listView;
    private Button okButton, backButton;
    private EditText editTextProductNumber;
    private ProductAdapter adapter;
    public static List<Product> productList;

    public static ProductManager productManager;
    int productQuantity;
    double productPrice;
    private AlertDialog.Builder builder;
    String userInput;
    double totalPrice;
    String productName;
    Product selectedProduct;

    int productIndex;
    int productNumeber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restock);

        // Initialize the ListView
        listView = findViewById(R.id.product_listview);
        okButton = findViewById(R.id.buttonOk);
        backButton = findViewById(R.id.buttonBack);
        editTextProductNumber = findViewById(R.id.number_picker);

        productManager = ProductManager.getInstance();
        builder = new AlertDialog.Builder(this);

        // get a list of products
        productList = productManager.getProducts();

        // Create and set the custom adapter
        adapter = new ProductAdapter(this, productList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected product from the adapter
            selectedProduct = adapter.getItem(position);
            productIndex = position;

            // Display the product details
            productName = selectedProduct.getName();
            productQuantity = selectedProduct.getQuantity();
            productPrice = selectedProduct.getPrice();
        });

        editTextProductNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Handle the action here
                userInput = editTextProductNumber.getText().toString();
                // Do something with the entered text

                if (Integer.parseInt(userInput) < 1)
                    Toast.makeText(getApplicationContext(), "please enter correct values", Toast.LENGTH_SHORT).show();
                else {
                    productNumeber = Integer.parseInt(userInput);
                    totalPrice = productNumeber * productPrice;
                }

                return true;
            }
            return false;
        });

        okButton.setOnClickListener(v -> {

            if (editTextProductNumber.getText().toString().equals("") || selectedProduct == null)
                Toast.makeText(this, "Please Select Product and Enter Product Number", Toast.LENGTH_SHORT).show();
            else {
                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage("Are you confirm?").setTitle("Confirm restock");

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to confirm this restock?\nYour product is " + userInput + " " + productName + " for " + String.valueOf(totalPrice))
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        // Get the current date and time
                        Date currentDate = new Date();
                        // Format the date and time as a string using the SimpleDateFormat object
                        String timestamp = dateFormat.format(currentDate);

                        Product product = new Product(productName, productNumeber, productPrice);
                        productManager.updateProduct(product, productIndex);
                        adapter.notifyDataSetChanged();

                        editTextProductNumber.setText("");

                        Toast.makeText(this, "Thanks for restock!", Toast.LENGTH_SHORT).show();

                    })
                    .setNegativeButton("No", (dialog, id) -> {
                        //  Action for 'NO' Button
                        editTextProductNumber.setText("");
                    });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Confirm Restock");
                alert.show();
            }
        });

        // back to manage activity
        backButton.setOnClickListener(v -> finish());
    }
}