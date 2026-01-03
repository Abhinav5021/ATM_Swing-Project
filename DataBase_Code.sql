CREATE DATABASE atm;
USE atm;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(10) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    pin VARCHAR(4) NOT NULL,
    balance INT DEFAULT 0
);

INSERT INTO users(card_number, name, pin, balance) VALUES
('1234567890','Swapnil','1234',5000),
('0987654321','om','2222',8000),
('1234512345','Amit','3333',3000);

CREATE TABLE transactions (
    tid INT AUTO_INCREMENT PRIMARY KEY,
    sender_id INT,
    receiver_id INT,
    amount INT,
    tdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (receiver_id) REFERENCES users(id)
);

SELECT * FROM users;

SELECT * FROM transactions;



