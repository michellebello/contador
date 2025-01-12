CREATE TABLE transaction (
    id INT AUTO_INCREMENT NOT NULL,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    amount DOUBLE NOT NULL,
    account_id INT NOT NULL,
    created_on DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES credentials(id),
    FOREIGN KEY (account_id) REFERENCES account(id)
)