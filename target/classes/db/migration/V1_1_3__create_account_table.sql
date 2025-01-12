CREATE TABLE account (
    id INT AUTO_INCREMENT NOT NULL,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    number VARCHAR(50) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL,
    balance DOUBLE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES credentials(id)
);
