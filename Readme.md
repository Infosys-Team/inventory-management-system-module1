#Readme first

Inventory Management System

This is a team-based Inventory Management System developed using Spring Boot for the backend and HTML, CSS, JavaScript for the frontend.
The system focuses on basic inventory operations with role-based access for Admin and Employee users.

⚠️ Note:
The original package name com.inventory.inventory-backend was invalid.
This project uses com.inventory.inventory_backend instead.

🛠 Technologies Used
Backend

Java

Spring Boot

Spring Data JPA

MySQL (MySQL Workbench)

Frontend

HTML

CSS

JavaScript

📁 Project Structure

Backend developed using Spring Boot

Frontend static files located at:

src/main/resources/static


Database managed using MySQL Workbench

REST APIs used for communication between frontend and backend

📦 System Modules (3 Modules)
1️⃣ User Management Module

User Registration

User Login

Role-based access (Admin / Employee)

OTP-based password reset

2️⃣ Inventory Management Module

Add new inventory items

Update item quantity

View available stock

Track low-stock items

Categorize items (e.g., medicines, equipment, supplies)

3️⃣ Dashboard & Access Module

Admin Dashboard

Employee Dashboard

Role-based redirection after login

Session handling using browser localStorage

🔐 User Roles
👑 Admin

Add and update inventory items

View all stock details

Monitor low-stock alerts

Access Admin Dashboard

👨‍💼 Employee

View inventory items

Check stock availability

Access Employee Dashboard

🗄️ Database Details

Database Name: inven_db

Database Tool: MySQL Workbench

Main Table: users

Inventory-related tables store item details, quantity, and category

▶️ How to Run the Project
Step 1: Start Database

Open MySQL Workbench

Start MySQL server

Ensure inven_db database exists

Step 2: Run Backend

Open the project in IDE (IntelliJ / Eclipse)

Run:

InventoryBackendApplication.java


OR using terminal:

mvn spring-boot:run

Step 3: Open Application

Open browser and visit:

http://localhost:8080/login.html

🔒 Security Note

Session handling is done using browser localStorage

No Spring Security or JWT is used

This project is intended for:

Learning purposes

Academic submission

Internship projects

❌ Not recommended for production use

👨‍👩‍👦 Team Project

This project is developed as a team project, where:

Backend development is handled using Spring Boot

Frontend development is handled using HTML, CSS, JavaScript

Code is shared and managed using GitHub for collaboration

🎯 Purpose of the Project

Academic project

Internship submission

Learn Spring Boot & REST APIs

Understand basic Inventory Management

Team collaboration using GitHub

✅ END






# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.9/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.9/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.9/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.9/reference/data/sql.html#data.sql.jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

