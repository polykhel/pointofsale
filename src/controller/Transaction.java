package controller;

import java.util.ArrayList;
import model.Order;

public class Transaction {

    private ArrayList<Order> orderList;
    private final double VATpercent;
    private double subTotal;
    private double VATvalue;
    private double totalPriceWithVAT;
    private double amountPaid;
    private double change;
    public int transNumber;
    public boolean started;
    public boolean complete;

    public Transaction() {
        orderList = new ArrayList();
        transNumber = 1;
        VATpercent = .12;
        VATvalue = 0;
        totalPriceWithVAT = 0;
    }

    public void setSubtotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public void setVATvalue(double VATvalue) {
        this.VATvalue = VATvalue;
    }

    public void setTotalPriceWithVat(double totalPrice) {
        this.totalPriceWithVAT = totalPrice;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getSubtotal() {
        return subTotal;
    }

    public double getVATvalue() {
        return VATvalue;
    }

    public double getTotalPrice() {
        return totalPriceWithVAT;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public double getChange() {
        return change;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void addToOrderList(String name, double price, int quantity, double tPrice) {
        orderList.add(new Order(name, price, quantity, tPrice));
    }

    public void resetOrderList() {
        orderList.clear();
    }

    public void compute() {
        orderList.stream().forEach((order) -> {
            subTotal += order.getTotalPrice();
        });
        VATvalue = subTotal * VATpercent;
        totalPriceWithVAT = subTotal + VATvalue;
    }

}
