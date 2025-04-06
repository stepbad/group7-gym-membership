-- DROP existing tables in reverse dependency order
DROP TABLE IF EXISTS member_class CASCADE;
DROP TABLE IF EXISTS workout_classes CASCADE;
DROP TABLE IF EXISTS memberships CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- CREATE tables

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password_hash TEXT NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    role VARCHAR(10) NOT NULL CHECK (role IN ('admin', 'trainer', 'member'))
);

CREATE TABLE memberships (
    membership_id SERIAL PRIMARY KEY,
    member_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    start_date DATE DEFAULT CURRENT_DATE,
    end_date DATE,
    status VARCHAR(20),
    fee_paid DECIMAL(10,2)
);

CREATE TABLE workout_classes (
    class_id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    description TEXT,
    trainer_id INT REFERENCES users(user_id) ON DELETE SET NULL
);

CREATE TABLE member_class (
    id SERIAL PRIMARY KEY,
    class_id INT REFERENCES workout_classes(class_id) ON DELETE CASCADE,
    member_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE (class_id, member_id)
);
