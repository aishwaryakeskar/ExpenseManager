package com.example.android.expense;

/**
 * Created by Admin on 19-10-18.
 */

public class Record {

    private int itemId;
    private int amount;
    private String itemName;
    private String category;
    private String mode;

    public Record() {
    }

    public Record(String category, int amount) {
        this.category = category;
        this.amount = amount;
    }

    public Record(String name, String mode, String category, int amount) {
        this.mode = mode;
        this.category = category;
        this.amount = amount;
        this.itemName = name;
    }

    public Record(int id, String name, String mode, String category, int amount) {
        this.itemId = id;
        this.mode = mode;
        this.category = category;
        this.amount = amount;
        this.itemName = name;
    }

    public int getAmount() {
        return this.amount;
    }

    // properties
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String date) {
        this.itemName = itemName;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int id) {
        this.itemId = itemId;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}