-- DROP existing tables in reverse dependency order
DROP TABLE IF EXISTS member_class CASCADE;
DROP TABLE IF EXISTS workout_classes CASCADE;
DROP TABLE IF EXISTS memberships CASCADE;
DROP TABLE IF EXISTS users CASCADE;

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
    workout_class_id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    description TEXT,
    trainer_id INT REFERENCES users(user_id) ON DELETE SET NULL
);

-- MEMBER_CLASS TABLE
CREATE TABLE member_class (
    id SERIAL PRIMARY KEY,
    workout_class_id INT REFERENCES workout_classes(workout_class_id) ON DELETE CASCADE,
    member_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE (workout_class_id, member_id)
);

-- Insert Admins
INSERT INTO users (username, password_hash, email, phone, address, role) VALUES
('admin1', '$2a$10$kKcK4E8cMB3Y4fbEQfWODuUwV7W6dyLFa6WxKQ0HSHBlJfIasrbCu', 'admin1@gym.com', '111-111-1111', 'Admin Blvd 1', 'admin'),
('admin2', '$2a$10$kKcK4E8cMB3Y4fbEQfWODuUwV7W6dyLFa6WxKQ0HSHBlJfIasrbCu', 'admin2@gym.com', '222-222-2222', 'Admin Blvd 2', 'admin'),
('admin3', '$2a$10$kKcK4E8cMB3Y4fbEQfWODuUwV7W6dyLFa6WxKQ0HSHBlJfIasrbCu', 'admin3@gym.com', '333-333-3333', 'Admin Blvd 3', 'admin');

-- Insert Trainers
INSERT INTO users (username, password_hash, email, phone, address, role) VALUES
('trainer_a', '$2a$10$kKcK4E8cMB3Y4fbEQfWODuUwV7W6dyLFa6WxKQ0HSHBlJfIasrbCu', 'trainer_a@gym.com', '444-444-4444', 'Trainer St 1', 'trainer'),
('trainer_b', '$2a$10$kKcK4E8cMB3Y4fbEQfWODuUwV7W6dyLFa6WxKQ0HSHBlJfIasrbCu', 'trainer_b@gym.com', '555-555-5555', 'Trainer St 2', 'trainer'),
('trainer_c', '$2a$10$kKcK4E8cMB3Y4fbEQfWODuUwV7W6dyLFa6WxKQ0HSHBlJfIasrbCu', 'trainer_c@gym.com', '666-666-6666', 'Trainer St 3', 'trainer');

-- Insert Members
INSERT INTO users (username, password_hash, email, phone, address, role) VALUES
('member_x', '$2a$10$kKcK4E8cMB3Y4fbEQfWODuUwV7W6dyLFa6WxKQ0HSHBlJfIasrbCu', 'member_x@gym.com', '777-777-7777', 'Member Ave 1', 'member'),
('member_y', '$2a$10$kKcK4E8cMB3Y4fbEQfWODuUwV7W6dyLFa6WxKQ0HSHBlJfIasrbCu', 'member_y@gym.com', '888-888-8888', 'Member Ave 2', 'member'),
('member_z', '$2a$10$kKcK4E8cMB3Y4fbEQfWODuUwV7W6dyLFa6WxKQ0HSHBlJfIasrbCu', 'member_z@gym.com', '999-999-9999', 'Member Ave 3', 'member');

-- Insert Workout Classes with assigned trainers (trainer IDs 4, 5, 6)
INSERT INTO workout_classes (type, description, trainer_id) VALUES
('Yoga Basics', 'Introductory yoga session for beginners', 4),
('HIIT Blast', 'High intensity interval training', 5),
('Strength Training', 'Weightlifting and muscle building', 6);

-- Assign members to classes
-- member IDs 7, 8, 9
-- workout_class_ids 1, 2, 3

INSERT INTO member_class (workout_class_id, member_id) VALUES
(1, 7),
(1, 8),
(2, 7),
(2, 9),
(3, 8);
