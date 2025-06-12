package org.istad.view;

import org.istad.controller.ProductCartController;
import org.istad.controller.ProductController;
import org.istad.model.dto.ProductResponDto;

import java.util.List;
import java.util.Scanner;

public class ProductView {
    TableUI<ProductResponDto> table = new TableUI<>();
    ProductController productController = new ProductController();
    ProductCartController productCartController = new ProductCartController();
    Scanner scanner = new Scanner(System.in);
    public void productView(){
        System.out.println("""
                ===== Welcome to ... =====
                1. View Product
                2. Add Product to Cart
                3. View Product Cart
                4. Order Product
                5. Search Product
                6. Exit Program
                """);
        System.out.print("Enter your choice : ");
        Integer choice = scanner.nextInt();
        switch (choice) {
            case 1:{
                List<ProductResponDto> product=  productController.getAllProduct();
                table.getTableDisplay(product);
                break;
            }
            case 2:{
                scanner.nextLine();
                System.out.println("Enter product UUID to be added to Cart: ");
                String uuid = scanner.nextLine();
                productCartController.addProductToCart(uuid);
                break;
            }
            case 3:{
                List<ProductResponDto> product=  productCartController.getAllProductInCart();
                table.getTableDisplay(product);
                break;
            }
            case 4:{
                System.out.println("Internal Server Error!");
                break;
            }
            case 5: {
                scanner.nextLine();
                System.out.print("Enter product name for search: ");
                String name = scanner.nextLine();
                List<ProductResponDto> product = productController.getProductByName(name);
                table.getTableDisplay(product);

            }
            case 6:{System.exit(0);}
        }
    }
}
