CREATE TABLE rate(
    id UUID PRIMARY KEY,
    date TIMESTAMP WITH TIME ZONE NOT NULL,
    base_code VARCHAR(3) NOT NULL,
    conversion_code VARCHAR(3) NOT NULL,
    conversion_rate double precision NOT NULL
);