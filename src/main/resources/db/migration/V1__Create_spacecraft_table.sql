CREATE TABLE spacecraft (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    model VARCHAR(255),
    manufacture_date DATE,
    weight DECIMAL(10, 2)
);