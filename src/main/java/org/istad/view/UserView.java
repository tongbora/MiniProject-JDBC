package org.istad.view;

import org.istad.controller.UserController;
import org.istad.model.dto.UserResponDto;

import java.util.Scanner;

public class UserView {
    private final UserController userController = new UserController();
    private final Scanner scanner = new Scanner(System.in);

    // ANSI colors
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BOLD = "\u001B[1m";
    private static final String BLUE = "\u001B[34m";
    private static final int WIDTH = 50;

    public void profile() {
        while (true) {
            String[] emailPassword = userController.getEmailAndPasswordFromFile();
            boolean isLoggedIn = !(emailPassword[0].equals("null") || emailPassword[1].equals("null"));

            System.out.println();
            printLine(CYAN);
            printCentered("👤 " + BOLD + "Your Account Info" + RESET);
            printLine(CYAN);

            if (isLoggedIn) {
                UserResponDto user = userController.getUserByEmail(emailPassword[0]);
                printCentered(YELLOW + "Username: " + GREEN + user.username());
                printCentered(YELLOW + "Email: " + GREEN + emailPassword[0]);
            } else {
                printCentered(RED + "Not logged in.");
                printCentered(YELLOW + "Username: " + RED + "null");
                printCentered(YELLOW + "Email: " + RED + "null");
            }

            printLine(CYAN);
            System.out.println();
            printCentered("1️⃣  Login");
            printCentered("2️⃣  Logout");
            printCentered("3️⃣  Return to Main Menu");
            System.out.print("\n" + BLUE + "👉 Enter your choice: " + RESET);

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println(RED + "❌ Invalid input. Please enter a number." + RESET);
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print(YELLOW + "📧 Email: " + RESET);
                    String email = scanner.nextLine();
                    String password;
                    if (System.console() != null) {
                        System.out.print(YELLOW + "🔐 Password: " + RESET);
                        char[] passwordChars = System.console().readPassword(); // Secure input
                        password = new String(passwordChars);
                    } else {
                        System.out.print(YELLOW + "🔐 Password (input hidden not supported here): " + RESET);
                        password = scanner.nextLine(); // Fallback for IDEs
                    }

                    if (userController.login(email, password)) {
                        printCentered(GREEN + "✅ Logged in successfully!" + RESET);
                    } else {
                        printCentered(RED + "❌ Login failed! Invalid credentials." + RESET);
                    }
                }
                case 2 -> {
                    if (userController.logout()) {
                        printCentered(GREEN + "✅ Logged out successfully." + RESET);
                    } else {
                        printCentered(RED + "⚠️ Logout failed." + RESET);
                    }
                }
                case 3 -> {
                    return; // Return to main UI
                }
                default -> printCentered(RED + "❌ Invalid choice. Please try again." + RESET);
            }
        }
    }

    // Helpers
    private void printCentered(String text) {
        int padding = (WIDTH - text.replaceAll("\u001B\\[[;\\d]*m", "").length()) / 2;
        System.out.printf("%" + padding + "s%s%n", "", text);
    }

    private void printLine(String color) {
        System.out.println(color + "=".repeat(WIDTH) + RESET);
    }
}
