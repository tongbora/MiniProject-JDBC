package org.istad.view;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import org.istad.controller.OrderController;
import org.istad.controller.ProductCartController;
import org.istad.controller.ProductController;
import org.istad.model.dto.ProductResponDto;

import java.util.List;
import java.util.Scanner;

public class ProductView {
    private final ProductController productController = new ProductController();
    private final ProductCartController productCartController = new ProductCartController();
    private final OrderController orderController = new OrderController();
    private final Scanner scanner = new Scanner(System.in);

    // Enhanced Color Palette
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String DIM = "\u001B[2m";
    private static final String UNDERLINE = "\u001B[4m";

    // Standard Colors
    private static final String BLACK = "\u001B[30m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";

    // Bright Colors
    private static final String BRIGHT_BLACK = "\u001B[90m";
    private static final String BRIGHT_RED = "\u001B[91m";
    private static final String BRIGHT_GREEN = "\u001B[92m";
    private static final String BRIGHT_YELLOW = "\u001B[93m";
    private static final String BRIGHT_BLUE = "\u001B[94m";
    private static final String BRIGHT_PURPLE = "\u001B[95m";
    private static final String BRIGHT_CYAN = "\u001B[96m";
    private static final String BRIGHT_WHITE = "\u001B[97m";

    // Background Colors
    private static final String BG_BLUE = "\u001B[44m";
    private static final String BG_GREEN = "\u001B[42m";
    private static final String BG_YELLOW = "\u001B[43m";

    public void productView() {
        System.setProperty("file.encoding", "UTF-8");

        while (true) {
            displayWelcomeHeader();
            displayMainMenu();

            System.out.print(BOLD + BRIGHT_CYAN + "Enter your choice: " + RESET);
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                displayNotification("ERROR", "Please enter a valid number (1-5)!", BRIGHT_RED);
                continue;
            }

            switch (choice) {
                case 1 -> {
                    displayLoadingMessage("Loading products...");
                    List<ProductResponDto> products = productController.getAllProduct();
                    displayAllProductCategories(products);
                }

                case 2 -> {
                    displayAddToCartInterface();
                }

                case 3 -> {
                    displayLoadingMessage("Loading your cart...");
                    List<ProductResponDto> cart = productCartController.getAllProductInCart();
                    displayShoppingCart(cart);
                }
                case 4 -> {
                    System.out.println("Enter uuid to remove product from cart:");
                    productCartController.removeProductFromCart(scanner.nextLine());
                }
                case 5 -> {
                    displayOrderInterface();
                }

                case 6 -> {
                    displayExitMessage();
                    return;
                }

                default -> displayNotification("WARNING", "Invalid choice! Please select between 1-5.", BRIGHT_YELLOW);
            }

            displayContinuePrompt();
        }
    }

    private void displayWelcomeHeader() {
        AsciiTable headerTable = new AsciiTable();
        headerTable.addRule();
        headerTable.addRow("");
        headerTable.addRow(BOLD + BG_BLUE + WHITE + "  [STORE] PREMIUM STORE MANAGEMENT SYSTEM  " + RESET);
        headerTable.addRow(DIM + "           Your Gateway to Excellence           " + RESET);
        headerTable.addRow("");
        headerTable.addRule();

        headerTable.getRenderer().setCWC(new CWC_LongestLine());
        headerTable.setTextAlignment(TextAlignment.CENTER);

        System.out.println("\n" + headerTable.render());
    }

    private void displayMainMenu() {
        AsciiTable menuTable = new AsciiTable();
        menuTable.addRule();
        menuTable.addRow(BOLD + BRIGHT_BLUE + "MAIN MENU" + RESET, "");
        menuTable.addRule();
        menuTable.addRow(BRIGHT_GREEN + "1" + RESET, "[PRODUCTS] View All Products");
        menuTable.addRow(BRIGHT_BLUE + "2" + RESET, "[CART+] Add Product to Cart");
        menuTable.addRow(BRIGHT_PURPLE + "3" + RESET, "[VIEW] View Shopping Cart");
        menuTable.addRow(BRIGHT_PURPLE + "4" + RESET, "[VIEW] Delete product from cart");
        menuTable.addRow(BRIGHT_YELLOW + "5" + RESET, "[ORDER] Place Order");
        menuTable.addRow(BRIGHT_RED + "6" + RESET, "[EXIT] Exit to Main Menu");
        menuTable.addRule();

        menuTable.getRenderer().setCWC(new CWC_LongestLine());
        System.out.println(menuTable.render());
    }

    private void displayAllProductCategories(List<ProductResponDto> products) {
        // Category Statistics Header
        displayCategoryStats(products);

        // Display each category
        displayProductCategory("[BEVERAGES]", "DRINK", products, BRIGHT_CYAN);
        displayProductCategory("[FOOD ITEMS]", "FOOD", products, BRIGHT_GREEN);
        displayProductCategory("[FRESH FRUITS]", "FRUIT", products, BRIGHT_YELLOW);
    }

    private void displayCategoryStats(List<ProductResponDto> products) {
        long drinkCount = products.stream().filter(p -> p.category().equalsIgnoreCase("DRINK")).count();
        long foodCount = products.stream().filter(p -> p.category().equalsIgnoreCase("FOOD")).count();
        long fruitCount = products.stream().filter(p -> p.category().equalsIgnoreCase("FRUIT")).count();

        AsciiTable statsTable = new AsciiTable();
        statsTable.addRule();
        statsTable.addRow(BOLD + "[STATS] INVENTORY OVERVIEW" + RESET, "", "", "");
        statsTable.addRule();
        statsTable.addRow("Category", "Beverages", "Food", "Fruits");
        statsTable.addRow("Count", BRIGHT_CYAN + drinkCount + RESET, BRIGHT_GREEN + foodCount + RESET, BRIGHT_YELLOW + fruitCount + RESET);
        statsTable.addRule();

        statsTable.getRenderer().setCWC(new CWC_LongestLine());
        statsTable.setTextAlignment(TextAlignment.CENTER);
        System.out.println(statsTable.render());
    }

    private void displayProductCategory(String title, String category, List<ProductResponDto> products, String color) {
        List<ProductResponDto> filteredProducts = products.stream()
                .filter(p -> p.category().equalsIgnoreCase(category))
                .toList();

        if (filteredProducts.isEmpty()) {
            displayNotification("[EMPTY]", title + " category has no products available.", BRIGHT_BLACK);
            return;
        }

        // Category Header with Border
        AsciiTable headerTable = new AsciiTable();
        headerTable.addRule();
        headerTable.addRow(BOLD + color + title + RESET);
        headerTable.addRule();
        headerTable.getRenderer().setCWC(new CWC_LongestLine());
        headerTable.setTextAlignment(TextAlignment.CENTER);
        System.out.println("\n" + headerTable.render());

        // Products Table with Enhanced Design
        AsciiTable productTable = new AsciiTable();
        productTable.addRule();
        productTable.addRow(
                BOLD + UNDERLINE + "UUID" + RESET,
                BOLD + UNDERLINE + "Product Name" + RESET,
                BOLD + UNDERLINE + "Price ($)" + RESET,
                BOLD + UNDERLINE + "Stock" + RESET,
                BOLD + UNDERLINE + "Status" + RESET
        );
        productTable.addRule();

        double totalValue = 0;
        for (ProductResponDto product : filteredProducts) {
            String stockStatus = getStockStatus(product.quantity());
            String stockColor = getStockColor(product.quantity());
            String priceFormatted = String.format("%.2f", product.price());
            // Display full UUID for easy copying
            String fullUuid = product.uuid();

            totalValue += product.price() * product.quantity();

            productTable.addRow(
                    BRIGHT_CYAN + fullUuid + RESET,
                    product.productName(),
                    color + "$" + priceFormatted + RESET,
                    stockColor + product.quantity() + RESET,
                    stockStatus
            );
            productTable.addRule();
        }

        // Category Summary Row
        productTable.addRow(
                BOLD + "TOTAL ITEMS: " + filteredProducts.size() + RESET,
                "",
                "",
                "",
                BOLD + color + String.format("VALUE: $%.2f", totalValue) + RESET
        );
        productTable.addRule();

        productTable.getRenderer().setCWC(new CWC_LongestLine());
        System.out.println(productTable.render());
    }

    private void displayAddToCartInterface() {
        AsciiTable interfaceTable = new AsciiTable();
        interfaceTable.addRule();
        interfaceTable.addRow(BOLD + BRIGHT_BLUE + "[CART+] ADD PRODUCT TO CART" + RESET);
        interfaceTable.addRule();
        interfaceTable.addRow("Enter the UUID of the product you want to add:");
        interfaceTable.addRow(DIM + "[TIP] Select and copy the full UUID from the product list above" + RESET);
        interfaceTable.addRow(DIM + "[TIP] Use Ctrl+C to copy, Ctrl+V to paste" + RESET);
        interfaceTable.addRule();

        interfaceTable.getRenderer().setCWC(new CWC_LongestLine());
        interfaceTable.setTextAlignment(TextAlignment.CENTER);
        System.out.println(interfaceTable.render());

        System.out.print(BOLD + BRIGHT_CYAN + "Product UUID: " + RESET);
        String uuid = scanner.nextLine().trim();

        if (uuid.isEmpty()) {
            displayNotification("ERROR", "UUID cannot be empty!", BRIGHT_RED);
            return;
        }

        try {
            productCartController.addProductToCart(uuid);
            displayNotification("SUCCESS", "Product added to cart successfully!", BRIGHT_GREEN);
        } catch (Exception e) {
            displayNotification("ERROR", "Failed to add product. Please check the UUID and try again.", BRIGHT_RED);
        }
    }

    private void displayShoppingCart(List<ProductResponDto> cart) {
        if (cart.isEmpty()) {
            displayNotification("[EMPTY CART]", "Your shopping cart is empty. Add some products first!", BRIGHT_YELLOW);
            return;
        }

        // Cart Header
        AsciiTable headerTable = new AsciiTable();
        headerTable.addRule();
        headerTable.addRow(BOLD + BG_GREEN + WHITE + "  [CART] YOUR SHOPPING CART  " + RESET);
        headerTable.addRule();
        headerTable.getRenderer().setCWC(new CWC_LongestLine());
        headerTable.setTextAlignment(TextAlignment.CENTER);
        System.out.println("\n" + headerTable.render());

        // Cart Items
        AsciiTable cartTable = new AsciiTable();
        cartTable.addRule();
        cartTable.addRow(
                BOLD + "#" + RESET,
                BOLD + "Product Name" + RESET,
                BOLD + "Unit Price" + RESET,
                BOLD + "Quantity" + RESET,
                BOLD + "Subtotal" + RESET,
                BOLD + "Category" + RESET
        );
        cartTable.addRule();

        double grandTotal = 0;
        int itemNumber = 1;

        for (ProductResponDto product : cart) {
            double subtotal = product.price() * product.quantity();
            grandTotal += subtotal;

            cartTable.addRow(
                    String.valueOf(itemNumber++),
                    product.productName(),
                    BRIGHT_GREEN + String.format("$%.2f", product.price()) + RESET,
                    String.valueOf(product.quantity()),
                    BOLD + BRIGHT_GREEN + String.format("$%.2f", subtotal) + RESET,
                    getCategoryEmoji(product.category()) + " " + product.category()
            );
            cartTable.addRule();
        }

        // Cart Total
        cartTable.addRow(
                "",
                BOLD + "GRAND TOTAL" + RESET,
                "",
                BOLD + cart.size() + " items" + RESET,
                BOLD + BG_GREEN + WHITE + String.format(" $%.2f ", grandTotal) + RESET,
                ""
        );
        cartTable.addRule();

        cartTable.getRenderer().setCWC(new CWC_LongestLine());
        System.out.println(cartTable.render());
    }

    private void displayOrderInterface() {
        AsciiTable confirmTable = new AsciiTable();
        confirmTable.addRule();
        confirmTable.addRow(BOLD + BRIGHT_YELLOW + "[ORDER] ORDER CONFIRMATION" + RESET);
        confirmTable.addRule();
        confirmTable.addRow("Are you ready to place your order?");
        confirmTable.addRow(DIM + "This will process all items in your cart" + RESET);
        confirmTable.addRule();

        confirmTable.getRenderer().setCWC(new CWC_LongestLine());
        confirmTable.setTextAlignment(TextAlignment.CENTER);
        System.out.println(confirmTable.render());

        try {
            displayLoadingMessage("Processing your order...");
            orderController.makeOrder();

            displayNotification("[SUCCESS]", "Order completed successfully!", BRIGHT_GREEN);

            List<ProductResponDto> ordered = orderController.getAllProductInOrder();
            displayOrderSummary(ordered);

        } catch (Exception e) {
            displayNotification("ERROR", "Order failed. Please try again.", BRIGHT_RED);
        }
    }

    private void displayOrderSummary(List<ProductResponDto> orderedProducts) {
        if (orderedProducts.isEmpty()) {
            return;
        }

        AsciiTable headerTable = new AsciiTable();
        headerTable.addRule();
        headerTable.addRow(BOLD + BG_YELLOW + BLACK + "  [RECEIPT] ORDER RECEIPT  " + RESET);
        headerTable.addRule();
        headerTable.getRenderer().setCWC(new CWC_LongestLine());
        headerTable.setTextAlignment(TextAlignment.CENTER);
        System.out.println("\n" + headerTable.render());

        AsciiTable receiptTable = new AsciiTable();
        receiptTable.addRule();
        receiptTable.addRow(
                BOLD + "Item" + RESET,
                BOLD + "Product" + RESET,
                BOLD + "Price" + RESET,
                BOLD + "Qty" + RESET,
                BOLD + "Total" + RESET
        );
        receiptTable.addRule();

        double orderTotal = 0;
        int itemNum = 1;

        for (ProductResponDto product : orderedProducts) {
            double itemTotal = product.price() * product.quantity();
            orderTotal += itemTotal;

            receiptTable.addRow(
                    String.valueOf(itemNum++),
                    product.productName(),
                    String.format("$%.2f", product.price()),
                    String.valueOf(product.quantity()),
                    BOLD + String.format("$%.2f", itemTotal) + RESET
            );
            receiptTable.addRule();
        }

        receiptTable.addRow(
                "",
                BOLD + "ORDER TOTAL" + RESET,
                "",
                BOLD + orderedProducts.size() + " items" + RESET,
                BOLD + BRIGHT_GREEN + String.format("$%.2f", orderTotal) + RESET
        );
        receiptTable.addRule();

        receiptTable.getRenderer().setCWC(new CWC_LongestLine());
        System.out.println(receiptTable.render());

        displayNotification("[THANK YOU]", "Thank you for your business!", BRIGHT_PURPLE);
    }

    private void displayNotification(String title, String message, String color) {
        AsciiTable notificationTable = new AsciiTable();
        notificationTable.addRule();
        notificationTable.addRow(BOLD + color + title + RESET);
        notificationTable.addRule();
        notificationTable.addRow(message);
        notificationTable.addRule();

        notificationTable.getRenderer().setCWC(new CWC_LongestLine());
        notificationTable.setTextAlignment(TextAlignment.CENTER);
        System.out.println("\n" + notificationTable.render());
    }

    private void displayLoadingMessage(String message) {
        System.out.print(BRIGHT_BLUE + "[LOADING] " + message + RESET);
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(300);
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(" Done!");
    }

    private void displayContinuePrompt() {
        System.out.print(DIM + "\nPress Enter to continue..." + RESET);
        scanner.nextLine();
    }

    private void displayExitMessage() {
        AsciiTable exitTable = new AsciiTable();
        exitTable.addRule();
        exitTable.addRow(BOLD + BRIGHT_PURPLE + "[GOODBYE] GOODBYE!" + RESET);
        exitTable.addRule();
        exitTable.addRow("Thank you for using Premium Store System");
        exitTable.addRow(DIM + "Returning to Main Menu..." + RESET);
        exitTable.addRule();

        exitTable.getRenderer().setCWC(new CWC_LongestLine());
        exitTable.setTextAlignment(TextAlignment.CENTER);
        System.out.println("\n" + exitTable.render());
    }

    // Helper Methods
    private String getStockStatus(int quantity) {
        if (quantity == 0) return BRIGHT_RED + "[X] OUT OF STOCK" + RESET;
        if (quantity <= 5) return BRIGHT_YELLOW + "[!] LOW STOCK" + RESET;
        if (quantity <= 20) return BRIGHT_BLUE + "[*] IN STOCK" + RESET;
        return BRIGHT_GREEN + "[+] WELL STOCKED" + RESET;
    }

    private String getStockColor(int quantity) {
        if (quantity == 0) return BRIGHT_RED;
        if (quantity <= 5) return BRIGHT_YELLOW;
        if (quantity <= 20) return BRIGHT_BLUE;
        return BRIGHT_GREEN;
    }

    private String getCategoryEmoji(String category) {
        return switch (category.toUpperCase()) {
            case "DRINK" -> "[DRINK]";
            case "FOOD" -> "[FOOD]";
            case "FRUIT" -> "[FRUIT]";
            default -> "[ITEM]";
        };
    }
}