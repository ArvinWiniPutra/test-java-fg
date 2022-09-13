package com.test;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    static String cityFileName;
    static String menuFileName;
    static String restoFileName;
    static ArrayList<String> listCity;
    static ArrayList<String> listResto;
    static ArrayList<String> listMenu;
    static boolean isEditing = false;
    static Scanner input;

    public static void main(String[] args) {

        // initialize
        listCity = new ArrayList<>();
        input = new Scanner(System.in);

        listResto = new ArrayList<>();
        input = new Scanner(System.in);

        listMenu = new ArrayList<>();
        input = new Scanner(System.in);

        String cityFilePath = System.console() == null ? "/src/listCity.txt" : "/listCity.txt";
        cityFileName = System.getProperty("user.dir") + cityFilePath;

        String menuFilePath = System.console() == null ? "/src/listmenu.txt" : "/listmenu.txt";
        menuFileName = System.getProperty("user.dir") + menuFilePath;

        String restoFilePath = System.console() == null ? "/src/listresto.txt" : "/listresto.txt";
        restoFileName = System.getProperty("user.dir") + restoFilePath;

        System.out.println("FILE: " + cityFileName);
        System.out.println("FILE: " + menuFileName);
        System.out.println("FILE: " + restoFileName);

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
        System.out.println("=== DATA MANAGER ===");
        System.out.println("[1] Data Kota");
        System.out.println("[2] Data Restoran");
        System.out.println("[3] Data Menu");
        System.out.println("[4] Keluar");
        System.out.println("---------------------");
        System.out.print("Pilih menu> ");

        String selectedMenu = input.nextLine();

        if (selectedMenu.equals("1")) {
            showCity();
        } else if (selectedMenu.equals("2")) {
            showResto();
        } else if (selectedMenu.equals("3")) {
            showMenu();
        } else if (selectedMenu.equals("4")) {
            System.out.println("Shutting Down...");
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

    // fungsi utk membaca City
    static void readCity() {

        try {

            File cityFile = new File(cityFileName);
            Scanner cityFileReader = new Scanner(cityFile);
    
            // load isi file ke dalam array listCity
            listCity.clear();
            while (cityFileReader.hasNextLine()) {

                String data = cityFileReader.nextLine();
                listCity.add(data);

            }
    
        } catch (FileNotFoundException e) {

            System.out.println("Error karena: " + e.getMessage());

        }

    }

    // fungsi utk membaca resto
    static void readResto() {

        try {

            File restoFile = new File(restoFileName);
            Scanner restoFileReader = new Scanner(restoFile);
    
            // load isi file ke dalam array listResto
            listResto.clear();
            while (restoFileReader.hasNextLine()) {

                String data = restoFileReader.nextLine();
                listResto.add(data);

            }
    
        } catch (FileNotFoundException e) {

            System.out.println("Error karena: " + e.getMessage());

        }

    }

    // fungsi utk membaca menu
    static void readMenu() {

        try {

            File menuFile = new File(menuFileName);
            Scanner menuFileReader = new Scanner(menuFile);
    
            // load isi file ke dalam array listResto
            listMenu.clear();
            while (menuFileReader.hasNextLine()) {

                String data = menuFileReader.nextLine();
                listMenu.add(data);

            }
    
        } catch (FileNotFoundException e) {

            System.out.println("Error karena: " + e.getMessage());

        }

    }

    // fungsi utk menampilkan City
    static void showCity() {

        clearScreen();
        readCity();
        if (listCity.size() > 0) {

            System.out.println("LIST KOTA:");
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

    // fungsi utk menampilkan resto
    static void showResto() {

        clearScreen();
        readResto();
        if (listResto.size() > 0) {

            System.out.println("LIST KOTA:");
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

    // fungsi utk menampilkan menu
    static void showMenu() {

        clearScreen();
        readMenu();
        if (listMenu.size() > 0) {

            System.out.println("LIST KOTA:");
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
    
}
