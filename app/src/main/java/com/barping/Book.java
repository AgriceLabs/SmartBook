package com.barping;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Simple representation of a book stored by the SmartBook app.
 */
public class Book {
    private final String id;
    private final String title;
    private final String category;
    private final double price;
    private int stock;

    public Book(String id, String title, String category, double price, int stock) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock(int quantity) {
        stock -= quantity;
    }

    public String formatForList() {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("id", "ID"));
        return "ID: " + id
                + " | Judul: " + title
                + " | Kategori: " + category
                + " | Harga: " + formatter.format(price)
                + " | Stok: " + stock;
    }
}
