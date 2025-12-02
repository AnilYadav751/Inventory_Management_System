# INVENTORY MANAGEMENT SYSTEM #

A robust CLI-based backend application built with Java and PostgreSQL to manage product inventory, process sales transactions, and generate stock reports. 
This project demonstrates raw JDBC connectivity, ACID transaction management, and database schema design without relying on ORM frameworks, showcasing a deep understanding
of core backend fundamentals.


 _KEY FEATURES_

* Product Management: ADD, UPDATE, and TRACK product details directly in the database.
* Transaction Processing: Handles complex sales transactions with ACID compliance. Automatically updates stock levels and logs sales history in a single atomic operation.
* Smart Reporting: Generates dynamic reports for low-stock items to assist in inventory restock planning.
* Raw JDBC Implementation: Uses custom SQL queries for high-performance data manipulation, bypassing the overhead of frameworks like Hibernate.
* Data Integrity: Implements `commit` and `rollback` logic to ensure data consistency during sales failures or system errors.


_TECH STACK_

* Language: Java (JDK 21)
* Database:  PostgreSQL
* Connectivity:  JDBC (Java Database Connectivity)
* Build Tool:  Maven
* IDE:  IntelliJ IDEA


_PROJECT STRUCTURE_

Inventory-Management-System/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/
│   │           └── example/
│   │               ├── Main.java             // Entry point (Menu System)
│   │               ├── InventoryService.java // Business Logic (Transactions)
│   │               └── DBConnection.java     // Database Connectivity
├── pom.xml                                   // Maven Dependencies
├── README.md                                 // Documentation
└── .gitignore                                // Git configuration



**FOLLOW these STEPS to set up the project locally.**

1. PRE-REQUISITES:
   
Java Development Kit (JDK) 21 or higher.
PostgreSQL installed and running.
Maven installed (or use IntelliJ's built-in Maven).

2. DATABASE SETUP:
Create the inventory_db database and run the following SQL script to create the necessary tables:

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL CHECK (quantity >= 0)
);

CREATE TABLE sales_log (
    sale_id SERIAL PRIMARY KEY,
    product_id INT REFERENCES products(id),
    quantity_sold INT NOT NULL,
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2)
); 
 
3. INSERT SAMPLE DATA(Optional):
You can insert any sample/dummy data as per your choice, for example the data given below can be inserted-

INSERT INTO products (name, price, quantity) VALUES 
('Gaming Laptop', 50000.00, 10),
('Mechanical Keyboard', 450.00, 50),
('Wireless Mouse', 250.50, 20),
('27-inch Monitor', 3000.00, 5),    
('USB-C Cable', 120.00, 100),
('Webcam', 850.00, 8) ;            


4. CONFIGURATION:
Open src/main/java/org/example/DBConnection.java and update the password field with your PostgreSQL password-

private static final String PASSWORD = "your_postgres_password";


5. BUILD AND RUN:
Run the application using your IDE or the command line.


7. EXAMPLE USAGE:
Here is a log of a successful transaction and reporting session-

    === INVENTORY SYSTEM ===
1. Add New Product
2. Process Sale (Transaction Demo)
3. Generate Low Stock Report
4. Exit
   
Enter choice: 2

Enter Product ID: 4
Enter Quantity Sold: 1
 Sale processed successfully! Total: 3000.0

--- LOW STOCK REPORT (Threshold: 10) ---
ID    Name                 Quantity  
----------------------------------------
4     27-inch Monitor      4         
6     Webcam               8


# Why this project?
I built this project to master the fundamentals of backend development—specifically how Java applications communicate directly with relational databases, 
handle complex SQL queries, and manage safe transactions in a multi-step process.

Created by
-ANIL YADAV
-https://github.com/AnilYadav751
