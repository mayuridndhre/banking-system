package model;

import java.util.Date;

public class Transaction {
    private int id;
    private int accountId;
    private String type;   // deposit / withdraw / transfer
    private double amount;
    private Date date;
    private String description;

    // constructor
    public Transaction(int accountId, String type, double amount, Date date, String description) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
