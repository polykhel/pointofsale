package model;

public class Order {
    
    private String name;
    private double price;
    private int quantity;
    private double totalPrice;
    
    public Order() {
        name = "";
        price = 0.0;
        quantity = 0;
        totalPrice = 0;
    }
    
    public Order(String name, double price, int quantity, double tPrice) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        totalPrice = tPrice;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    @Override
    public String toString() {
        return "name: " + name + " quantity: " + quantity;
    }
}
