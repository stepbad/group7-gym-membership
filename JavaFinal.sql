
-- USERS TABLE
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password_hash TEXT NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    role VARCHAR(10) NOT NULL CHECK (role IN ('admin', 'trainer', 'member'))
);

-- MEMBERSHIPS TABLE
CREATE TABLE memberships (
    membership_id SERIAL PRIMARY KEY,
    member_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    start_date DATE DEFAULT CURRENT_DATE,
    end_date DATE,
    status VARCHAR(20),
    fee_paid DECIMAL(10,2)
);

-- WORKOUT CLASSES TABLE
CREATE TABLE workout_classes (
    class_id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    description TEXT,
    trainer_id INT REFERENCES users(user_id) ON DELETE SET NULL
);

-- MEMBER_CLASS TABLE
CREATE TABLE member_class (
    id SERIAL PRIMARY KEY,
    class_id INT REFERENCES workout_classes(class_id) ON DELETE CASCADE,
    member_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE (class_id, member_id)
);

-- Admins
INSERT INTO users (username, password_hash, email, phone, address, role) VALUES
('admin1', '$2a$10$Dow1j5O7UbwEJtRj6ye9D.sMHNYWcW9Yj7OrSncKyIAQqLQwvMCHe', 'admin1@gym.com', '111-111-1111', 'Admin Blvd 1', 'admin'),
('admin2', '$2a$10$Dow1j5O7UbwEJtRj6ye9D.sMHNYWcW9Yj7OrSncKyIAQqLQwvMCHe', 'admin2@gym.com', '222-222-2222', 'Admin Blvd 2', 'admin'),
('admin3', '$2a$10$Dow1j5O7UbwEJtRj6ye9D.sMHNYWcW9Yj7OrSncKyIAQqLQwvMCHe', 'admin3@gym.com', '333-333-3333', 'Admin Blvd 3', 'admin');

-- Trainers
INSERT INTO users (username, password_hash, email, phone, address, role) VALUES
('trainer1', '$2a$10$Dow1j5O7UbwEJtRj6ye9D.sMHNYWcW9Yj7OrSncKyIAQqLQwvMCHe', 'trainer1@gym.com', '444-444-4444', 'Trainer St 1', 'trainer'),
('trainer2', '$2a$10$Dow1j5O7UbwEJtRj6ye9D.sMHNYWcW9Yj7OrSncKyIAQqLQwvMCHe', 'trainer2@gym.com', '555-555-5555', 'Trainer St 2', 'trainer'),
('trainer3', '$2a$10$Dow1j5O7UbwEJtRj6ye9D.sMHNYWcW9Yj7OrSncKyIAQqLQwvMCHe', 'trainer3@gym.com', '666-666-6666', 'Trainer St 3', 'trainer');

-- Members
INSERT INTO users (username, password_hash, email, phone, address, role) VALUES
('member1', '$2a$10$Dow1j5O7UbwEJtRj6ye9D.sMHNYWcW9Yj7OrSncKyIAQqLQwvMCHe', 'member1@gym.com', '777-777-7777', 'Member Ave 1', 'member'),
('member2', '$2a$10$Dow1j5O7UbwEJtRj6ye9D.sMHNYWcW9Yj7OrSncKyIAQqLQwvMCHe', 'member2@gym.com', '888-888-8888', 'Member Ave 2', 'member'),
('member3', '$2a$10$Dow1j5O7UbwEJtRj6ye9D.sMHNYWcW9Yj7OrSncKyIAQqLQwvMCHe', 'member3@gym.com', '999-999-9999', 'Member Ave 3', 'member');
