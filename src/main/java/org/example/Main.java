package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        org.example.InventoryService service = new org.example.InventoryService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== INVENTORY SYSTEM ===");
            System.out.println("1. Add New Product");
            System.out.println("2. Process Sale (Transaction Demo)");
            System.out.println("3. Generate Low Stock Report");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter Quantity: ");
                    int qty = scanner.nextInt();
                    service.addProduct(name, price, qty);
                    break;
                case 2:
                    System.out.print("Enter Product ID: ");
                    int pid = scanner.nextInt();
                    System.out.print("Enter Quantity Sold: ");
                    int sellQty = scanner.nextInt();
                    service.processSale(pid, sellQty);
                    break;
                case 3:
                    System.out.print("Enter Low Stock Threshold (e.g., 10): ");
                    int threshold = scanner.nextInt();
                    service.generateLowStockReport(threshold);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}