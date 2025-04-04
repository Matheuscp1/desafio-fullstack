ALTER TABLE products
    ALTER COLUMN category_id SET NOT NULL;

ALTER TABLE products
    ALTER
        COLUMN code TYPE VARCHAR(255);

ALTER TABLE products
    ALTER
        COLUMN description TYPE VARCHAR(255);

ALTER TABLE products
    ALTER
        COLUMN name TYPE VARCHAR(100);

ALTER TABLE products
    ALTER
        COLUMN price TYPE DECIMAL;