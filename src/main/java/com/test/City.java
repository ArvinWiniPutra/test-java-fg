package com.test;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class City {

    static String fileName;
    static ArrayList<String> listCity;
    static boolean isEditing = false;
    static Scanner input;

    public static void main(String[] args) {

        // initialize
        listCity = new ArrayList<>();
        input = new Scanner(System.in);

        String filePath = System.console() == null ? "/src/listCity.txt" : "/listCity.txt";
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
        System.out.println("=== LIST Kota ===");
        System.out.println("[1] Lihat Kota");
        System.out.println("[2] Tambah Kota");
        System.out.println("[3] Edit Kota");
        System.out.println("[4] Hapus Kota");
        System.out.println("[5] Keluar");
        System.out.println("---------------------");
        System.out.print("Pilih menu> ");

        String selectedMenu = input.nextLine();

        if (selectedMenu.equals("1")) {
            showCity();
        } else if (selectedMenu.equals("2")) {
            addCity();
        } else if (selectedMenu.equals("3")) {
            editCity();
        } else if (selectedMenu.equals("4")) {
            deleteCity();
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

    // fungsi utk membaca city
    static void readCity() {

        try {

            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);
    
            // load isi file ke dalam array listCity
            listCity.clear();
            while (fileReader.hasNextLine()) {

                String data = fileReader.nextLine();
                listCity.add(data);

            }
    
        } catch (FileNotFoundException e) {

            System.out.println("Error karena: " + e.getMessage());

        }

    }

    // fungsi utk menampilkan city
    static void showCity() {

        clearScreen();
        readCity();
        if (listCity.size() > 0) {

            System.out.println("LIST RESTO:");
            int index = 0;
            for (String data : listCity) {

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

    static void addCity() {

        clearScreen();

        System.out.print("Nama Kota: ");
        String addCity = input.nextLine();

        try {

            // tulis file
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.append(String.format("%s%n", addCity ));
            fileWriter.close();
            System.out.println("Berhasil menambahkan data Kota ");

        } catch (IOException e) {

            System.out.println("Terjadi kesalahan karena: " + e.getMessage());
        }

    backToOptions();

    }

    static void editCity() {

        isEditing = true;
        showCity();

        try {

            System.out.println("-----------------");
            System.out.print("Pilih Indeks> ");
            int index = Integer.parseInt(input.nextLine());

            if (index > listCity.size()) {

                throw new IndexOutOfBoundsException("Kamu memasukan data yang salah!");

            } else {

                System.out.print("Edit Kota: ");
                String newCity = input.nextLine();

                // update data
                listCity.set(index, newCity);

                System.out.println(listCity.toString());

                try {

                    FileWriter fileWriter = new FileWriter(fileName, false);

                    // write new data
                    for (String data : listCity) {

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

    static void deleteCity() {

        isEditing = true;
        showCity();

        System.out.println("-----------------");
        System.out.print("Pilih Indeks> ");
        int index = Integer.parseInt(input.nextLine());

        try {

            if (index > listCity.size()) {

                throw new IndexOutOfBoundsException("Kamu memasukan data yang salah!");
                
            } else {

                System.out.println("Kamu akan menghapus:");
                System.out.println(String.format("[%d] %s", index, listCity.get(index)));
                System.out.println("Apa kamu yakin?");
                System.out.print("Jawab (y/t): ");
                String jawab = input.nextLine();

                if (jawab.equalsIgnoreCase("y")) {

                    // hapus data
                    listCity.remove(index);

                    // tulis ulang file
                    try {

                        FileWriter fileWriter = new FileWriter(fileName, false);

                        // write new data
                        for (String data : listCity) {

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
