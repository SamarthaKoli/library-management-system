# Library Management System

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen.svg)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Enabled-005F0F.svg)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-purple.svg)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36.svg)
![H2 Database](https://img.shields.io/badge/H2-In%20Memory%20DB-orange.svg)

## Project Overview

The **Library Management System** is a full-stack web application built with **Java**, **Spring Boot**, **Spring Data JPA**, **Thymeleaf**, **Bootstrap 5**, **Maven**, and **H2 Database**. It provides a modern, responsive interface for managing a library catalog with book creation, listing, searching, issuing, returning, and deletion.

The application includes a dashboard with live statistics and a clean Bootstrap-powered UI designed for straightforward daily book management.

## Features

- Dashboard with statistics
- Add Book
- View All Books
- Search Books by Title
- Issue Book
- Return Book
- Delete Book
- Persistent database storage
- Confirmation dialogs before delete actions
- Responsive Bootstrap UI

## Technology Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Thymeleaf
- Bootstrap 5
- H2 Database
- Maven
- Git & GitHub

## Project Structure

```text
src/main/java/com/example/library_management
├── controller/
├── service/
├── repository/
├── model/
└── LibraryManagementApplication.java

src/main/resources
├── templates/
└── static/
```

### Key Pages

- `index.html` - Dashboard with statistics
- `books.html` - Book listing, search, and actions
- `add-book.html` - Add new book form

## API Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/books` | View all books as JSON |
| POST | `/books` | Add a new book |
| GET | `/books-ui` | Open the Thymeleaf books page |
| GET | `/add-book` | Open the add book form |
| POST | `/add-book` | Save a new book from the form |
| GET | `/search-book?title=` | Search books by title |
| GET | `/issue-book/{id}` | Issue a book |
| GET | `/return-book/{id}` | Return a book |
| GET | `/delete-book/{id}` | Delete a book |

## Installation & Setup

### 1. Clone the repository

```bash
git clone <your-repository-url>
cd library-management
```

### 2. Open the project

Open the project in **IntelliJ IDEA** or **VS Code**.

### 3. Run the application

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
.\mvnw.cmd spring-boot:run
```

### 4. Open in browser

Open:

[http://localhost:8080](http://localhost:8080/)

## Screenshots

Add screenshots here to showcase the UI.

### Dashboard

![Dashboard Screenshot](docs/screenshots/dashboard.png)

### Books Page

![Books Page Screenshot](docs/screenshots/books.png)

### Add Book Page

![Add Book Screenshot](docs/screenshots/add-book.png)

## Future Enhancements

- User authentication
- Role-based access control
- MySQL/PostgreSQL support
- Book categories
- Pagination and sorting
- Docker deployment

## Author

**Samartha Koli**

Computer Engineering Student

GitHub: <your-github-profile>
