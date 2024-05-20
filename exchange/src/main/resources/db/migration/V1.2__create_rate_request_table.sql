CREATE TABLE rate_request(
    id UUID PRIMARY KEY,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL,
    status UUID NOT NULL,
    type UUID NOT NULL,
    currency_code VARCHAR(3) NOT NULL,
    rate_date TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_dictionary_status_id FOREIGN KEY(status) REFERENCES dictionary(id),
    CONSTRAINT fk_dictionary_type_id FOREIGN KEY(type) REFERENCES dictionary(id)
);

ALTER TABLE rate ADD COLUMN rate_request_id UUID,
    ADD CONSTRAINT fk_rate_request_id FOREIGN KEY(rate_request_id) REFERENCES rate_request(id);