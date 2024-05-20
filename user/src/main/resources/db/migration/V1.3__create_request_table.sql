CREATE TABLE request(
    id UUID PRIMARY KEY,
    created_user_id UUID NOT NULL,
    active_name VARCHAR(100) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    last_updated_datetime TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_created_user_id FOREIGN KEY(created_user_id) REFERENCES user_(id)
);

CREATE TABLE user_request(
    user_id UUID NOT NULL,
    request_id UUID NOT NULL,
    CONSTRAINT fk_bought_user_id FOREIGN KEY(user_id) REFERENCES user_(id),
    CONSTRAINT fk_request_id FOREIGN KEY(request_id) REFERENCES request(id)
);