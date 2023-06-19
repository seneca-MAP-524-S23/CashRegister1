package com.week1.cashregisterpart1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

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

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button buyButton, manageButton;
    private TextView txtProductName, txtProductQuantity, txtTotalPrice;
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
    int productNumeber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        setContentView(R.layout.activity_main);

        // Initialize the ListView
        listView = findViewById(R.id.product_listview);
        txtProductName = findViewById(R.id.textviewProductName);
        txtProductQuantity = findViewById(R.id.textviewQuantity);
        txtTotalPrice = findViewById(R.id.textviewTotalAmount);

        buyButton = findViewById(R.id.buttonOk);
        manageButton = findViewById(R.id.buttonCancel);

        productManager = ProductManager.getInstance();
        builder = new AlertDialog.Builder(this);

        // Create a list of products
        productManager.setUpData();
        productList = productManager.getProducts();

        // Create and set the custom adapter
        adapter = new ProductAdapter(this, productList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected product from the adapter
                selectedProduct = adapter.getItem(position);

                // Display the product details
                productName = selectedProduct.getName();
                productQuantity = selectedProduct.getQuantity();
                productPrice = selectedProduct.getPrice();
                txtProductName.setText(productName);
            }
        });

        editTextProductNumber = findViewById(R.id.number_picker);
        editTextProductNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Handle the action here
                    userInput = editTextProductNumber.getText().toString();
                    // Do something with the entered text

                    if (Integer.parseInt(userInput) > productQuantity)
                        Toast.makeText(getApplicationContext(), "We have less stock", Toast.LENGTH_SHORT).show();
                    else {
                        txtProductQuantity.setText(userInput);
                        productNumeber = Integer.parseInt(userInput);
                        totalPrice = productNumeber * productPrice;
                        txtTotalPrice.setText(String.valueOf(totalPrice));
                    }

                    return true;
                }
                return false;
            }
        });

        buyButton.setOnClickListener(v -> {

            if (editTextProductNumber.getText().toString().equals("") || selectedProduct == null)
                Toast.makeText(MainActivity.this, "Please Select Product and Enter Product Number", Toast.LENGTH_SHORT).show();


            else {
                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage("Are you confirm?").setTitle("Confirm Purchase");

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to confirm this purchase?\nYour Purchase is " + userInput + " " + productName + " for " + String.valueOf(totalPrice))
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                            // Get the current date and time
                            Date currentDate = new Date();
                            // Format the date and time as a string using the SimpleDateFormat object
                            String timestamp = dateFormat.format(currentDate);
                            productManager.purchasedProducts.add(new PurchasedProduct(productName, productNumeber, totalPrice, timestamp));
                            selectedProduct.setQuantity(productQuantity - productNumeber);
                            adapter.notifyDataSetChanged();

                            editTextProductNumber.setText("");

                            txtProductQuantity.setText("Total Qunatity");
                            txtTotalPrice.setText("Total Price");
                            txtProductName.setText("Selected Product");

                            Toast.makeText(MainActivity.this, "Thanks for Purchasing!", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            editTextProductNumber.setText("");
                        }
                    });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Confirm Purchase");
                alert.show();
            }
        });


        // open manage activity
        manageButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ManagerActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.updateData();
    }
}