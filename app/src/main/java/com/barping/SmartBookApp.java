package com.barping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class SmartBookApp {
    private final Scanner scanner = new Scanner(System.in);
    private final List<Book> books = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        new SmartBookApp().run();
    }

    private void run() {
        Locale.setDefault(new Locale("id", "ID"));
        seedInitialBooks();
        boolean running = true;
        while (running) {
            System.out.println("===== SMARTBOOK APP =====");
            System.out.println("1. Lihat daftar buku");
            System.out.println("2. Tambah buku baru");
            System.out.println("3. Cari buku berdasarkan judul");
            System.out.println("4. Beli buku");
            System.out.println("5. Tampilkan riwayat transaksi");
            System.out.println("6. Keluar dari program");

            int choice = readInt("Pilihan Anda: ", 1, 6);
            System.out.println();
            switch (choice) {
                case 1:
                    showBooks();
                    break;
                case 2:
                    // implemented in later steps
                    System.out.println("Fitur tambah buku akan segera tersedia.");
                    pause();
                    break;
                case 3:
                    // implemented in later steps
                    System.out.println("Fitur pencarian akan segera tersedia.");
                    pause();
                    break;
                case 4:
                    // implemented in later steps
                    System.out.println("Fitur pembelian akan segera tersedia.");
                    pause();
                    break;
                case 5:
                    // implemented in later steps
                    System.out.println("Riwayat transaksi akan segera tersedia.");
                    pause();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("Terima kasih telah menggunakan SmartBook App!");
        scanner.close();
    }

    private void seedInitialBooks() {
        books.add(new Book(IdGenerator.nextBookId(), "Pemrograman Java", "Teknologi", 85000, 10));
        books.add(new Book(IdGenerator.nextBookId(), "Dasar Algoritma", "Teknologi", 65000, 7));
        books.add(new Book(IdGenerator.nextBookId(), "Belajar Data Science", "Teknologi", 95000, 5));
    }

    private void showBooks() {
        System.out.println("Daftar Buku:");
        if (books.isEmpty()) {
            System.out.println("Belum ada buku yang tersimpan.");
        } else {
            for (Book book : books) {
                System.out.println(book.formatForList());
            }
        }
        pause();
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine();
            try {
                int number = Integer.parseInt(raw.trim());
                if (number < min || number > max) {
                    System.out.printf("Masukkan angka antara %d sampai %d.%n", min, max);
                } else {
                    return number;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, coba lagi.");
            }
        }
    }

    private void pause() {
        System.out.print("\nTekan ENTER untuk kembali ke menu...");
        scanner.nextLine();
        System.out.println();
    }
}
