package com.barping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Represents a purchase transaction.
 */
public class Transaction {
    private final String id;
    private final String bookId;
    private final String bookTitle;
    private final int quantity;
    private final double totalPrice;
    private final LocalDateTime createdAt;

    public Transaction(String id, String bookId, String bookTitle, int quantity, double totalPrice, LocalDateTime createdAt) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String formatForList() {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("id", "ID"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return "ID: " + id
                + " | Buku: " + bookTitle
                + " (" + bookId + ")"
                + " | Jumlah: " + quantity
                + " | Total: " + formatter.format(totalPrice)
                + " | Tanggal: " + createdAt.format(dateTimeFormatter);
    }
}
