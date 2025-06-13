package org.istad.view;

import org.istad.controller.UserController;
import org.istad.model.dto.UserCreateDto;

import java.io.File;
import java.util.Scanner;

public class UI {

    // ANSI escape codes
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String RED = "\u001B[31m";
    public static final String PURPLE = "\u001B[35m";
    public static final String BOLD = "\u001B[1m";

    public static final int WIDTH = 60;

    Scanner scanner = new Scanner(System.in);
    UserController userController = new UserController();
    UserView userView = new UserView();
    ProductView productView = new ProductView();
    File file = new File("user.txt");

    public void ui() {
        System.setProperty("file.encoding", "UTF-8");
        boolean registered = file.exists();

        if (!registered) {
            printLine(PURPLE);
            printCentered(BOLD + CYAN + "🙏 Welcome to GROUP 2 STORE" + RESET);
            printCentered(RED + "📋 Please sign up to continue!" + RESET);
            printLine(PURPLE);

            System.out.print(YELLOW + "🧑 Username: " + RESET);
            String username = scanner.nextLine();
            System.out.print(YELLOW + "📧 Email: " + RESET);
            String email = scanner.nextLine();
            System.out.print(YELLOW + "🔐 Password: " + RESET);
            String password = scanner.nextLine();

            userController.register(new UserCreateDto(username, email, password));
            printCentered(GREEN + "✅ Registration complete! Welcome aboard!" + RESET);
        }

        while (true) {
            System.out.println();
            printLine(BLUE);
            printCentered(BOLD + GREEN + "🌟 GROUP 2 STORE MENU 🌟" + RESET);
            printLine(BLUE);

            userController.autoLogin();

            System.out.println();
            printCentered(YELLOW + "1️⃣  View Account");
            printCentered(CYAN + "2️⃣  View Store");
            printCentered(RED + "3️⃣  Exit Program" + RESET);
            System.out.println();

            System.out.print(BLUE + "👉 Enter your choice: " + RESET);

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                printCentered(RED + "❌ Invalid input! Please enter a number." + RESET);
                continue;
            }

            System.out.println();

            switch (choice) {
                case 1 -> {
                    printSection("👤 Viewing Account", CYAN);
                    userView.profile();
                }
                case 2 -> {
                    printSection("🛒 Viewing Store", GREEN);
                    productView.productView();
                }
                case 3 -> {
                    printCentered(GREEN + "👋 Goodbye! Thanks for visiting GROUP 2 STORE." + RESET);
                    System.exit(0);
                }
                default -> printCentered(RED + "❌ Invalid choice! Please try again." + RESET);
            }
            pauseAndReturn();
        }
    }

    // Center text
    private void printCentered(String text) {
        int padding = (WIDTH - text.replaceAll("\u001B\\[[;\\d]*m", "").length()) / 2;
        System.out.printf("%" + padding + "s%s%n", "", text);
    }

    // Print line with color
    private void printLine(String color) {
        System.out.println(color + "=".repeat(WIDTH) + RESET);
    }

    // Print section title
    private void printSection(String title, String color) {
        System.out.println();
        printLine(color);
        printCentered(BOLD + color + title + RESET);
        printLine(color);
    }

    // Pause for user before returning to menu
    private void pauseAndReturn() {
        System.out.print(YELLOW + "\n⏎ Press Enter to return to menu..." + RESET);
        scanner.nextLine();
    }
}
