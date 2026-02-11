CREATE TABLE budget_allocation (
    id INT AUTO_INCREMENT NOT NULL,
    budget_id INT NOT NULL,
    category VARCHAR(50) NOT NULL,
    amount DOUBLE NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(budget_id) REFERENCES budget(id)
);