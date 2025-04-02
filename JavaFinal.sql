CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) CHECK (role IN ('admin', 'trainer', 'member')) NOT NULL
);

CREATE TABLE memberships (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    membership_type VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

CREATE TABLE workout_classes (
    id SERIAL PRIMARY KEY,
    trainer_id INT REFERENCES users(id) ON DELETE SET NULL,
    class_name VARCHAR(100) NOT NULL,
    schedule TIMESTAMP NOT NULL,
    capacity INT CHECK (capacity > 0)
);



