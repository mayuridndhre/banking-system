package model;

import java.util.Date;

public class Account {
    private int id;
    private int customerId;
    private String type;   // e.g. Savings, Current
    private double balance;
    private Date createdAt;

    // constructor
    public Account(int customerId, String type, double balance) {
        this.customerId = customerId;
        this.type = type;
        this.balance = balance;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
