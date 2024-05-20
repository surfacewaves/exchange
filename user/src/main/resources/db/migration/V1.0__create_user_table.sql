CREATE TABLE user_(
    id UUID PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    registered_datetime TIMESTAMP WITH TIME ZONE NOT NULL
);