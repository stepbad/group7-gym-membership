CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,  -- Storing hashed passwords
    email VARCHAR(100) UNIQUE NOT NULL,  -- Email field
    phone VARCHAR(15),  -- Optional, depending on your needs
    address TEXT,  -- Optional, depending on your needs
    role VARCHAR(20) CHECK (role IN ('admin', 'trainer', 'member')) NOT NULL
);



CREATE TABLE memberships (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,  -- Foreign key to users
    membership_type VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);


CREATE TABLE workout_classes (
    id SERIAL PRIMARY KEY,
    trainer_id INT REFERENCES users(id) ON DELETE SET NULL,  -- Foreign key to users (trainer)
    class_name VARCHAR(100) NOT NULL,
    schedule TIMESTAMP NOT NULL,
    capacity INT CHECK (capacity > 0)
);



