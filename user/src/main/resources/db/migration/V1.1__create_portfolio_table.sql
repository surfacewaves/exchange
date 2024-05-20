CREATE TABLE portfolio(
    id UUID PRIMARY KEY,
    base_currency VARCHAR(3) NOT NULL,
    total DOUBLE PRECISION NOT NULL,
    user_id UUID NOT NULL,
    last_updated_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT  fk_user_id FOREIGN KEY(user_id) REFERENCES user_(id)
);