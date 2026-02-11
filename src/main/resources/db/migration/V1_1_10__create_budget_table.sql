CREATE TABLE budget (
    id INT AUTO_INCREMENT NOT NULL,
    user_id INT NOT NULL,
    year INT NOT NULL,
    month_num TINYINT NOT NULL,
    total_amount DOUBLE NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (user_id, month_num),
    FOREIGN KEY (user_id) REFERENCES credentials(id)
);