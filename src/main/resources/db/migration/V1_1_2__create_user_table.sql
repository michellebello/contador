CREATE TABLE user (
    id INT NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES credentials(id)
);
