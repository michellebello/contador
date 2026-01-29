ALTER TABLE `transaction`
RENAME COLUMN type TO category;

ALTER TABLE `transaction`
ADD COLUMN type_id INT;

ALTER TABLE `transaction`
ADD CONSTRAINT fk_transaction_type
FOREIGN KEY (type_id) REFERENCES transaction_Type(id);


