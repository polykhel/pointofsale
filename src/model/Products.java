package model;

import java.util.ArrayList;

public class Products {

    private String productName;
    private double productPrice;
    private int productAvail;
    public String[] heading;
    public ArrayList<Products> productList;

    public Products() {
        this.productList = new ArrayList();
        productName = "";
        productPrice = 0;
        productAvail = 0;
        heading = new String[3];
    }

    public Products(String name, double price, int avail) {
        this.productList = new ArrayList();
        productName = name;
        productPrice = price;
        productAvail = avail;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductAvail() {
        return productAvail;
    }
    
    public void setProductAvail(int avail) {
        productAvail = avail;
    }
    
    public void setProductPrice(double price) {
        productPrice = price;
    }
    
    public void emptyProductList() {
        productList.clear();
    }

    @Override
    public String toString() {
        return "name: " + productName + " price " + productPrice + " quantity: " + productAvail;
    }
}
