-- Users Table
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    role VARCHAR(20) CHECK (role IN ('admin', 'trainer', 'member')) NOT NULL
);

-- Memberships Table
CREATE TABLE memberships (
    membership_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    membership_type VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

-- Workout Classes Table
CREATE TABLE workout_classes (
    class_id SERIAL PRIMARY KEY,
    trainer_id INT REFERENCES users(user_id) ON DELETE SET NULL,
    class_name VARCHAR(100) NOT NULL,
    schedule TIMESTAMP NOT NULL,
    capacity INT CHECK (capacity > 0)
);




