# Gym Management System

A Java-based console application that manages users, memberships, workout classes, and admin/trainer/member roles for a gym. Built using PostgreSQL as the database backend and follows DAO + Service architecture.

## Features

- **User Management**: Register and login as Admin, Trainer, or Member.
- **Memberships**: Purchase and manage fixed membership packages with credit tracking.
- **Workout Classes**: Create, assign, join, or view classes depending on role.
- **Admin Tools**: Full system control including user and revenue management.
- **Trainer Tools**: Manage personal info, classes, and view enrolled members.
- **Member Tools**: Browse and register for classes, track expenses and membership status.

## Tech Stack

- Java 17+
- PostgreSQL
- Maven (via `mvnd`)
- BCrypt for password hashing

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/stepbad/group7-gym-membership.git
   cd group7-gym-membership

2. Set up the PostgreSQL database using JavaFinal.sql.

3. Update your db.properties file:

    db.url=jdbc:postgresql://localhost:5432/postgres
    db.user=postgres
    db.password=admin123

4. Build the project using Maven Daemon:

    ```bash
    mvnd compile

5. Run the application:

   ```bash
    mvnd exec:java -Dexec.mainClass="com.gym.App"

this will bring you to register/ login menu.