-- Users Table
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    role VARCHAR(20) CHECK (role IN ('admin', 'trainer', 'member')) NOT NULL,
    membership_id INT,
    total_membership_expenses DECIMAL(10,2),
    CONSTRAINT fk_membership FOREIGN KEY (membership_id) REFERENCES memberships(membership_id) ON DELETE SET NULL
);

-- Memberships Table
CREATE TABLE memberships (
    membership_id SERIAL PRIMARY KEY,
    membership_type VARCHAR(50) NOT NULL,
    membership_description TEXT,
    membership_cost DECIMAL(10,2) NOT NULL,
    member_id INT REFERENCES users(user_id) ON DELETE CASCADE
);

-- Workout Classes Table
CREATE TABLE workout_classes (
    workout_class_id SERIAL PRIMARY KEY,
    trainer_id INT REFERENCES users(user_id) ON DELETE SET NULL,
    class_name VARCHAR(100) NOT NULL,
    schedule TIMESTAMP NOT NULL,
    type VARCHAR(100) NOT NULL,
    description TEXT,
    capacity INT CHECK (capacity > 0)
);
