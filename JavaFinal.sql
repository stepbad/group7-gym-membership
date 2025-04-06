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
    membership_type VARCHAR(50) NOT NULL,
    membership_description TEXT,
    membership_cost DECIMAL(10,2) NOT NULL,
    member_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    start_date DATE DEFAULT CURRENT_DATE,
    end_date DATE
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
