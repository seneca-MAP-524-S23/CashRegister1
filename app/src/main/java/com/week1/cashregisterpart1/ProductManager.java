package com.week1.cashregisterpart1;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private static ProductManager instance = null;
    static ArrayList<PurchasedProduct> purchasedProducts;
    public static List<Product> productList;

    // get the instance of the product manager
    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }

    // set up the product list data initially
    public void setUpData() {
        purchasedProducts = new ArrayList<>();
        // Create a list of products
        productList = new ArrayList<>();
        productList.add(new Product("Product 1", 5, 9.99));
        productList.add(new Product("Product 2", 3, 12.99));
        productList.add(new Product("Product 3", 8, 19.99));
        productList.add(new Product("Product 4", 5, 9.99));
        productList.add(new Product("Product 5", 3, 12.99));
        productList.add(new Product("Product 6", 8, 19.99));
        productList.add(new Product("Product 7", 5, 9.99));
        productList.add(new Product("Product 8", 3, 12.99));
        productList.add(new Product("Product 9", 8, 19.99));
    }

    // get the product list
    public List<Product> getProducts() {
        return productList;
    }

    // get the purchased product list
    public static ArrayList<PurchasedProduct> getPurchasedProducts() {
        return purchasedProducts;
    }

    // add the purchased product to the list
    public static void updateProduct(Product product, int index) {
        productList.set(index, product);
    }
}