CREATE TABLE session (
    id INT AUTO_INCREMENT NOT NULL,
    session VARCHAR(100) NOT NULL,
    username VARCHAR(32) NOT NULL,
    expires_on DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (username) REFERENCES credentials(username)
);
