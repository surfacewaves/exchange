CREATE TABLE portfolio_item(
    id UUID PRIMARY KEY,
    active_name VARCHAR(100) NOT NULL,
    active_amount INTEGER NOT NULL,
    portfolio_id UUID NOT NULL,
    last_updated_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_portfolio_id FOREIGN KEY(portfolio_id) REFERENCES portfolio(id)
);