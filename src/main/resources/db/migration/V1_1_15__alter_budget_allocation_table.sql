ALTER TABLE `budget_allocation`
DROP COLUMN `spent`,
ADD COLUMN `spent` DOUBLE DEFAULT 0;