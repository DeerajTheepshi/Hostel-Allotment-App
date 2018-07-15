package com.example.android.hostellallotment.ModelClasses;

import java.util.List;

public class Main {
    List<Items> items;
    String message;

    public String getMessage() {
        return message;
    }

    public List<Items> getItems() {
        return items;
    }

    public Main(List<Items> items, String message) {
        this.items = items;
        this.message = message;
    }
}
