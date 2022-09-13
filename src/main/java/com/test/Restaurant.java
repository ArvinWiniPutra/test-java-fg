package com.test;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Restaurant {

    static String fileName;
    static ArrayList<String> listResto;
    static boolean isEditing = false;
    static Scanner input;

    public static void main(String[] args) {

        // initialize
        listResto = new ArrayList<>();
        input = new Scanner(System.in);

        String filePath = System.console() == null ? "/src/listresto.txt" : "/listresto.txt";
        fileName = System.getProperty("user.dir") + filePath;

        System.out.println("FILE: " + fileName);

        // run the program (main loop)
        while (true) {
            showRestoOptions();
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

    static void showRestoOptions() {
        System.out.println("=== LIST Resto ===");
        System.out.println("[1] Lihat Resto");
        System.out.println("[2] Tambah Resto");
        System.out.println("[3] Edit Resto");
        System.out.println("[4] Hapus Resto");
        System.out.println("[5] Keluar");
        System.out.println("---------------------");
        System.out.print("Pilih menu> ");

        String selectedMenu = input.nextLine();

        if (selectedMenu.equals("1")) {
            showResto();
        } else if (selectedMenu.equals("2")) {
            addResto();
        } else if (selectedMenu.equals("3")) {
            editResto();
        } else if (selectedMenu.equals("4")) {
            deleteResto();
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

    // fungsi utk membaca resto
    static void readResto() {

        try {

            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);
    
            // load isi file ke dalam array listResto
            listResto.clear();
            while (fileReader.hasNextLine()) {

                String data = fileReader.nextLine();
                listResto.add(data);

            }
    
        } catch (FileNotFoundException e) {

            System.out.println("Error karena: " + e.getMessage());

        }

    }

    // fungsi utk menampilkan resto
    static void showResto() {

        clearScreen();
        readResto();
        if (listResto.size() > 0) {

            System.out.println("LIST RESTO:");
            int index = 0;
            for (String data : listResto) {

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

    static void addResto() {

        clearScreen();

        System.out.print("Nama Restoran: ");
        String addResto = input.nextLine();
        System.out.print("Alamat: ");
        String addLocation = input.nextLine();
        System.out.print("Kota: ");
        String addCity = input.nextLine();

        try {

            // tulis file
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.append(String.format("%s%n", addResto + ", " + addLocation + ", " + addCity));
            fileWriter.close();
            System.out.println("Berhasil menambahkan data restoran "+ addResto);

        } catch (IOException e) {

            System.out.println("Terjadi kesalahan karena: " + e.getMessage());
        }

    backToOptions();

    }

    static void editResto() {

        isEditing = true;
        showResto();

        try {

            System.out.println("-----------------");
            System.out.print("Pilih Indeks> ");
            int index = Integer.parseInt(input.nextLine());

            if (index > listResto.size()) {

                throw new IndexOutOfBoundsException("Kamu memasukan data yang salah!");

            } else {

                System.out.print("Edit nama: ");
                String newMenu = input.nextLine();
                System.out.print("Edit Alamat: ");
                String newLocation = input.nextLine();
                System.out.print("Edit Kota: ");
                String newCity = input.nextLine();

                // update data
                listResto.set(index, newMenu + ", " + newLocation + ", " + newCity);

                System.out.println(listResto.toString());

                try {

                    FileWriter fileWriter = new FileWriter(fileName, false);

                    // write new data
                    for (String data : listResto) {

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

    static void deleteResto() {

        isEditing = true;
        showResto();

        System.out.println("-----------------");
        System.out.print("Pilih Indeks> ");
        int index = Integer.parseInt(input.nextLine());

        try {

            if (index > listResto.size()) {

                throw new IndexOutOfBoundsException("Kamu memasukan data yang salah!");
                
            } else {

                System.out.println("Kamu akan menghapus:");
                System.out.println(String.format("[%d] %s", index, listResto.get(index)));
                System.out.println("Apa kamu yakin?");
                System.out.print("Jawab (y/t): ");
                String jawab = input.nextLine();

                if (jawab.equalsIgnoreCase("y")) {

                    // hapus data
                    listResto.remove(index);

                    // tulis ulang file
                    try {

                        FileWriter fileWriter = new FileWriter(fileName, false);

                        // write new data
                        for (String data : listResto) {

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
