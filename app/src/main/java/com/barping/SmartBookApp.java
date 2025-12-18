package com.barping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.InputMismatchException;

public class SmartBookApp {
    private final Scanner scanner = new Scanner(System.in);
    private final List<Book> books = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        new SmartBookApp().menu();
    }

    void menu() {
        final String[] c = {"\u001B[1;32m", "\u001B[0m"};
        Scanner input = new Scanner(System.in);
        Locale.setDefault(new Locale("id", "ID"));
        seedInitialBooks();

        System.out.printf("\n[i] Selamat datang di %sSmartBook App%s!\n\n", c[0], c[1]);
        final String[] menu = {"Daftar", "Tambah", "Cari", "Beli", "Riwayat Transaksi", "Keluar"};
        try {
            boolean running = true;
            while (running) {
                System.out.println("Menu Aplikasi:");
                for (int i = 0; i < menu.length; i++) {
                    System.out.printf("[%s%d%s] %s%s\n", c[0], (i + 1), c[1], menu[i], i < 4 ? " Buku" : "");
                }
                System.out.print("\nPilihan Anda: ");
                byte option = input.nextByte();
                System.out.println();
                boolean shouldExit = option == 6;
                ternary(
                    shouldExit,
                    () -> System.out.printf("[i] Terima kasih telah menggunakan %sSmartBook App!%s\n", c[0], c[1]),
                    () -> handleMenuOption(option)
                );
                running = !shouldExit;
            }
        } catch (InputMismatchException e) {
            System.out.printf("[i] Invalid input: Value must be a number from 1 to 6!\n");
        }

        input.close();
    }

    private void handleMenuOption(byte option) {
        Runnable action = option == 1 ? this::showBooks
            : option == 2 ? this::addBook
            : option == 3 ? this::searchBooks
            : option == 4 ? this::buyBook
            : option == 5 ? this::showTransactions
            : () -> System.out.println("[i] Invalid input: Out of range!\n");
        action.run();
    }

    private void seedInitialBooks() {
        books.add(new Book(IdGenerator.nextBookId(), "Pemrograman Java", "Teknologi", 85000, 10));
        books.add(new Book(IdGenerator.nextBookId(), "Dasar Algoritma", "Teknologi", 65000, 7));
        books.add(new Book(IdGenerator.nextBookId(), "Belajar Data Science", "Teknologi", 95000, 5));
    }

    private void showBooks() {
        displayBooks(true);
    }

    private void displayBooks(boolean pauseAfter) {
        final String green = "\u001B[1;32m";
        final String reset = "\u001B[0m";

        System.out.println("Daftar Buku:");
        ternary(
            books.isEmpty(),
            () -> System.out.println("Belum ada buku yang tersimpan."),
            () -> {
                System.out.println("+----------+------------------------------------------+------------------+----------------+-------+");
                System.out.printf("| %sID%s       | %sJudul%s                                    | %sKategori%s         | %sHarga%s          | %sStok%s  |\n",
                    green, reset, green, reset, green, reset, green, reset, green, reset);
                System.out.println("+----------+------------------------------------------+------------------+----------------+-------+");
                for (Book book : books) {
                    System.out.printf("| %-8s | %-40s | %-16s | %14s | %5d |\n",
                        book.getId(),
                        truncate(book.getTitle(), 40),
                        truncate(book.getCategory(), 16),
                        formatRupiah(book.getPrice()),
                        book.getStock());
                }
                System.out.println("+----------+------------------------------------------+------------------+----------------+-------+");
            }
        );
        ternary(pauseAfter, this::pause, noop());
    }

    private String truncate(String text, int maxLength) {
        return text.length() <= maxLength ? text : text.substring(0, maxLength - 3) + "...";
    }

    private void addBook() {
        final String green = "\u001B[1;32m";
        final String reset = "\u001B[0m";

        System.out.println("Tambah Buku Baru");
        String title = readTitle("Judul: ");

        ternary(
            isTitleExists(title),
            () -> {
                System.out.println("\n[!] Buku dengan judul tersebut sudah ada. Silakan gunakan judul yang berbeda.");
                pause();
            },
            () -> {
                String category = readCategory("Kategori: ");
                double price = readDouble("Harga: ", 0, 999999999);
                int stock = readInt("Stok: ", 0, 10000);

                String id = IdGenerator.nextBookId();
                Book book = new Book(id, title, category, price, stock);
                books.add(book);

                System.out.println("\nBuku berhasil ditambahkan:");
                System.out.println("+----------+------------------------------------------+------------------+----------------+-------+");
                System.out.printf("| %sID%s       | %sJudul%s                                    | %sKategori%s         | %sHarga%s          | %sStok%s  |\n",
                    green, reset, green, reset, green, reset, green, reset, green, reset);
                System.out.println("+----------+------------------------------------------+------------------+----------------+-------+");
                System.out.printf("| %-8s | %-40s | %-16s | %14s | %5d |\n",
                    book.getId(),
                    truncate(book.getTitle(), 40),
                    truncate(book.getCategory(), 16),
                    formatRupiah(book.getPrice()),
                    book.getStock());
                System.out.println("+----------+------------------------------------------+------------------+----------------+-------+");
                pause();
            }
        );
    }

    private boolean isTitleExists(String title) {
        return books.stream()
            .anyMatch(book -> book.getTitle().equalsIgnoreCase(title) ? true : false);
    }

    private void searchBooks() {
        final String green = "\u001B[1;32m";
        final String reset = "\u001B[0m";

        System.out.println("Cari Buku Berdasarkan Judul");
        String keyword = readNonEmpty("Masukkan kata kunci: ").toLowerCase();
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            ternary(
                book.getTitle().toLowerCase().contains(keyword),
                () -> results.add(book),
                noop()
            );
        }

        ternary(
            results.isEmpty(),
            () -> System.out.println("Buku tidak ditemukan."),
            () -> {
                System.out.println("Hasil pencarian:");
                System.out.println("+----------+------------------------------------------+------------------+----------------+-------+");
                System.out.printf("| %sID%s       | %sJudul%s                                    | %sKategori%s         | %sHarga%s          | %sStok%s  |\n",
                    green, reset, green, reset, green, reset, green, reset, green, reset);
                System.out.println("+----------+------------------------------------------+------------------+----------------+-------+");
                for (Book book : results) {
                    System.out.printf("| %-8s | %-40s | %-16s | %14s | %5d |\n",
                        book.getId(),
                        truncate(book.getTitle(), 40),
                        truncate(book.getCategory(), 16),
                        formatRupiah(book.getPrice()),
                        book.getStock());
                }
                System.out.println("+----------+------------------------------------------+------------------+----------------+-------+");
            }
        );
        pause();
    }

    private void buyBook() {
        ternary(
            books.isEmpty(),
            () -> {
                System.out.println("Belum ada buku tersedia untuk dibeli.");
                pause();
            },
            () -> {
                System.out.println("Beli Buku");
                displayBooks(false);
                String id = readNonEmpty("Masukkan ID buku: ").toUpperCase();
                Book selected = findBookById(id);
                ternary(
                    selected == null,
                    () -> {
                        System.out.println("ID buku tidak ditemukan.");
                        pause();
                    },
                    () -> ternary(
                        selected.getStock() == 0,
                        () -> {
                            System.out.println("Stok buku habis.");
                            pause();
                        },
                        () -> {
                            System.out.println("Buku dipilih: " + selected.getTitle() + " - " + formatRupiah(selected.getPrice()));
                            int quantity = readInt("Jumlah yang dibeli: ", 1, selected.getStock());

                            double total = selected.getPrice() * quantity;

                            selected.reduceStock(quantity);
                            String trxId = IdGenerator.nextTransactionId();
                            transactions.add(new Transaction(trxId, selected.getId(), selected.getTitle(), quantity, total, LocalDateTime.now()));

                            System.out.println("\nTransaksi berhasil!");
                            System.out.printf("Total bayar: %s%n", formatRupiah(total));
                            pause();
                        }
                    )
                );
            }
        );
    }

    private void showTransactions() {
        final String green = "\u001B[1;32m";
        final String reset = "\u001B[0m";

        System.out.println("Riwayat Transaksi:");
        ternary(
            transactions.isEmpty(),
            () -> System.out.println("Belum ada transaksi."),
            () -> {
                System.out.println("+------------+----------+------------------------------------------+--------+----------------+---------------------+");
                System.out.printf("| %s%-10s%s | %s%-8s%s | %s%-40s%s | %s%-6s%s | %s%-14s%s | %s%-19s%s |\n",
                    green, "ID Trx", reset,
                    green, "ID Buku", reset,
                    green, "Judul Buku", reset,
                    green, "Jumlah", reset,
                    green, "Total", reset,
                    green, "Waktu", reset);
                System.out.println("+------------+----------+------------------------------------------+--------+----------------+---------------------+");
                for (Transaction transaction : transactions) {
                    System.out.println(formatTransactionForTable(transaction));
                }
                System.out.println("+------------+----------+------------------------------------------+--------+----------------+---------------------+");
            }
        );
        pause();
    }

    private String formatTransactionForTable(Transaction transaction) {
        String title = transaction.getBookTitle();
        String formattedTitle = title.length() > 40 ? title.substring(0, 37) + "..." : title;
        String formattedDate = transaction.getCreatedAt().format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        return String.format("| %-10s | %-8s | %-40s | %-6d | %-14s | %-19s |",
            transaction.getId(),
            transaction.getBookId(),
            formattedTitle,
            transaction.getQuantity(),
            formatRupiah(transaction.getTotalPrice()),
            formattedDate);
    }

    private Book findBookById(String id) {
        return books.stream()
            .filter(book -> book.getId().equalsIgnoreCase(id) ? true : false)
            .findFirst()
            .orElse(null);
    }

    private String readTitle(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = scanner.nextLine().trim();
            try {
                String error = text.isEmpty() ? "Judul tidak boleh kosong, coba lagi."
                    : text.length() > 60 ? "Judul tidak boleh lebih dari 60 karakter, coba lagi."
                    : !isValidASCII(text) ? "Judul hanya boleh mengandung karakter ASCII (huruf, angka, spasi, dan tanda baca umum), coba lagi."
                    : null;
                ternary(error != null, () -> {
                    throw new IllegalArgumentException(error);
                }, noop());
                return text;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String readCategory(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = scanner.nextLine().trim();
            String[] words = text.split("\\s+");
            try {
                String error = text.isEmpty() ? "Kategori tidak boleh kosong, coba lagi."
                    : !isValidASCII(text) ? "Kategori hanya boleh mengandung karakter ASCII (huruf, angka, spasi, dan tanda baca umum), coba lagi."
                    : words.length < 1 || words.length > 4 ? "Kategori harus terdiri dari 1-4 kata, coba lagi."
                    : null;
                ternary(error != null, () -> {
                    throw new IllegalArgumentException(error);
                }, noop());
                return capitalizeWords(text);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean isValidASCII(String text) {
        return text.chars()
            .allMatch(c -> (c >= 32 && c <= 126) ? true : false);
    }

    private String capitalizeWords(String text) {
        String[] words = text.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            result.append(i > 0 ? " " : "");
            result.append(word.isEmpty()
                ? ""
                : Character.toUpperCase(word.charAt(0)) + (word.length() > 1 ? word.substring(1).toLowerCase() : ""));
        }
        return result.toString();
    }

    private String formatRupiah(double amount) {
        long value = (long) amount;
        String numStr = String.valueOf(value);
        StringBuilder formatted = new StringBuilder();
        int count = 0;
        for (int i = numStr.length() - 1; i >= 0; i--) {
            formatted.insert(0, count > 0 && count % 3 == 0 ? "." : "");
            formatted.insert(0, numStr.charAt(i));
            count++;
        }
        return "Rp " + formatted.toString();
    }

    private double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine();
            try {
                double number = Double.parseDouble(raw.trim());
                String error = number < min ? String.format("Masukkan nilai minimal %.0f.", min)
                    : number > max ? String.format("Masukkan nilai maksimal %.0f.", max)
                    : null;
                ternary(error != null, () -> {
                    throw new IllegalArgumentException(error);
                }, noop());
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, coba lagi.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = scanner.nextLine().trim();
            try {
                String error = text.isEmpty() ? "Input tidak boleh kosong, coba lagi." : null;
                ternary(error != null, () -> {
                    throw new IllegalArgumentException(error);
                }, noop());
                return text;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine();
            try {
                int number = Integer.parseInt(raw.trim());
                String error = number < min || number > max ? String.format("Masukkan angka antara %d sampai %d.", min, max) : null;
                ternary(error != null, () -> {
                    throw new IllegalArgumentException(error);
                }, noop());
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, coba lagi.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void pause() {
        System.out.print("\nTekan ENTER untuk kembali ke menu...");
        scanner.nextLine();
        System.out.println();
    }

    private static void ternary(boolean condition, Runnable whenTrue, Runnable whenFalse) {
        (condition ? whenTrue : whenFalse).run();
    }

    private static Runnable noop() {
        return () -> {
        };
    }
}
