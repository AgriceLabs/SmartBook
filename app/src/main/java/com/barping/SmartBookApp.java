package com.barping;

import java.util.Scanner;
import java.util.InputMismatchException;

public class SmartBookApp {
    static void menu() {
        final String[] c = {"\u001B[1;32m", "\u001B[0m"};
        Scanner input = new Scanner(System.in);
        
        System.out.printf("\n[i] Selamat datang di %sSmartBook App%s!\n", c[0], c[1]);
        final String[] menu = {"Daftar", "Tambah", "Cari", "Beli", "Riwayat Transaksi", "Keluar"};
        try {
            while (true) {
                System.out.println("|-----------------------|");
                System.out.println("| Menu Aplikasi");
                System.out.println("|-----------------------|");
                for (int i = 0; i < menu.length; i++) {
                    if (i < 4) {
                        System.out.printf("|\s[%s%d%s] %s Buku\n", c[0], (i + 1), c[1], menu[i]);
                    } else {
                        System.out.printf("|\s[%s%d%s] %s\n", c[0], (i + 1), c[1], menu[i]);
                    }
                }
                System.out.println("|-----------------------|");
                System.out.print("|\sPilihan Anda: ");
                byte option = input.nextByte();
                System.out.println("|-----------------------|");
                if (option == 6) {
                    System.out.printf("[i] Terima kasih telah menggunakan %sSmartBook App!%s", c[0], c[1]);
                    break;
                }
                switch (option) {
                    case 1:
                        System.out.println("Dummy!");
                        break;
                    case 2:
                        System.out.println("Dummy!");
                        break;
                    case 3:
                        System.out.println("Dummy!");
                        break;
                    case 4:
                        System.out.println("Dummy!");
                        break;
                    case 5:
                        System.out.println("Dummy!");
                        break;
                    default:
                        System.out.println("\n[i] Invalid input: Out of range!");
                        break;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("|-----------------------|");
            System.out.printf("[i] Invalid input: Value must be a number from 1 to 6!");
        }

        input.close();
    }

    public static void main(String[] args) {
        menu();
    }
}
