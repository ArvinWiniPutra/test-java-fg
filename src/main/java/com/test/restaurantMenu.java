package com.test;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class restaurantMenu {

    static String fileName;
    static ArrayList<String> listMenu;
    static boolean isEditing = false;
    static Scanner input;

    public static void main(String[] args) {

        // initialize
        listMenu = new ArrayList<>();
        input = new Scanner(System.in);

        String filePath = System.console() == null ? "/src/listmenu.txt" : "/listmenu.txt";
        fileName = System.getProperty("user.dir") + filePath;

        System.out.println("FILE: " + fileName);

        // run the program (main loop)
        while (true) {
            showOptions();
        }
        
    }

    static void clearScreen(){

        try {

            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {

                // clear screen untuk windows
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start()
                        .waitFor();

            } else {

                // clear screen untuk Linux, Unix, Mac
                Runtime.getRuntime().exec("clear");
                System.out.print("\033[H\033[2J");
                System.out.flush();

            }

        } catch (final Exception e) {

            System.out.println("Error karena: " + e.getMessage());
        
        }

    }

    static void showOptions() {
        System.out.println("=== LIST MENU ===");
        System.out.println("[1] Lihat Menu");
        System.out.println("[2] Tambah Menu");
        System.out.println("[3] Edit Menu");
        System.out.println("[4] Hapus Menu");
        System.out.println("[5] Keluar");
        System.out.println("---------------------");
        System.out.print("Pilih menu> ");

        String selectedMenu = input.nextLine();

        if (selectedMenu.equals("1")) {
            showMenu();
        } else if (selectedMenu.equals("2")) {
            addMenu();
        } else if (selectedMenu.equals("3")) {
            editMenu();
        } else if (selectedMenu.equals("4")) {
            deleteMenu();
        } else if (selectedMenu.equals("5")) {
            System.exit(0);
        } else {
            System.out.println("Kamu salah pilih menu!");
            backToOptions();
        }
    }

    static void backToOptions() {

        System.out.println("");
        System.out.print("Tekan [Enter] untuk kembali..");
        input.nextLine();
        clearScreen();

    }

    // fungsi utk membaca menu
    static void readMenu() {

        try {

            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);
    
            // load isi file ke dalam array listMenu
            listMenu.clear();
            while (fileReader.hasNextLine()) {

                String data = fileReader.nextLine();
                listMenu.add(data);

            }
    
        } catch (FileNotFoundException e) {

            System.out.println("Error karena: " + e.getMessage());

        }

    }

    // fungsi utk menampilkan menu
    static void showMenu() {

        clearScreen();
        readMenu();
        if (listMenu.size() > 0) {

            System.out.println("LIST MENU:");
            int index = 0;
            for (String data : listMenu) {

                System.out.println(String.format("[%d] %s", index, data));
                index++;

            }

        } else {

            System.out.println("Tidak ada data!");

        }

        if (!isEditing) {

            backToOptions();

        }

    }

    static void addMenu() {

        clearScreen();

        System.out.print("Nama Menu: ");
        String addMenu = input.nextLine();
        System.out.print("Harga: ");
        String addPrice = input.nextLine();

        try {

            // tulis file
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.append(String.format("%s%n", addMenu + " " + "Rp." + addPrice));
            fileWriter.close();
            System.out.println("Berhasil menambahkan "+ addMenu + " seharga Rp." + addPrice);

        } catch (IOException e) {

            System.out.println("Terjadi kesalahan karena: " + e.getMessage());
        }

    backToOptions();

    }

    static void editMenu() {

        isEditing = true;
        showMenu();

        try {

            System.out.println("-----------------");
            System.out.print("Pilih Indeks> ");
            int index = Integer.parseInt(input.nextLine());

            if (index > listMenu.size()) {

                throw new IndexOutOfBoundsException("Kamu memasukan data yang salah!");

            } else {

                System.out.print("Edit nama: ");
                String newMenu = input.nextLine();
                System.out.print("Edit harga: ");
                String newPrice = input.nextLine();

                // update data
                listMenu.set(index, newMenu + " " + "Rp." + newPrice);

                System.out.println(listMenu.toString());

                try {

                    FileWriter fileWriter = new FileWriter(fileName, false);

                    // write new data
                    for (String data : listMenu) {

                        fileWriter.append(String.format("%s%n", data));

                    }

                    fileWriter.close();

                    System.out.println("Berhasil diubah!");

                } catch (IOException e) {

                    System.out.println("Terjadi kesalahan karena: " + e.getMessage());

                }

            }

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        isEditing = false;
        backToOptions();

    }

    static void deleteMenu() {

        isEditing = true;
        showMenu();

        System.out.println("-----------------");
        System.out.print("Pilih Indeks> ");
        int index = Integer.parseInt(input.nextLine());

        try {

            if (index > listMenu.size()) {

                throw new IndexOutOfBoundsException("Kamu memasukan data yang salah!");
                
            } else {

                System.out.println("Kamu akan menghapus:");
                System.out.println(String.format("[%d] %s", index, listMenu.get(index)));
                System.out.println("Apa kamu yakin?");
                System.out.print("Jawab (y/t): ");
                String jawab = input.nextLine();

                if (jawab.equalsIgnoreCase("y")) {

                    // hapus data
                    listMenu.remove(index);

                    // tulis ulang file
                    try {

                        FileWriter fileWriter = new FileWriter(fileName, false);

                        // write new data
                        for (String data : listMenu) {

                            fileWriter.append(String.format("%s%n", data));

                        }

                        fileWriter.close();

                        System.out.println("Berhasil dihapus!");

                    } catch (IOException e) {

                        System.out.println("Terjadi kesalahan karena: " + e.getMessage());

                    }
                }
            }
        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

        isEditing = false;
        backToOptions();

    }
    
}
