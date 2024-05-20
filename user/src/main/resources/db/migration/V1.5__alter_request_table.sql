ALTER TABLE request ADD COLUMN original_amount INTEGER NOT NULL;
ALTER TABLE request ADD COLUMN current_amount INTEGER NOT NULL;
ALTER TABLE request ADD COLUMN target_currency VARCHAR(3) NOT NULL;
ALTER TABLE request ADD COLUMN status UUID NOT NULL,
    ADD CONSTRAINT fk_dictionary_status_id FOREIGN KEY(status) REFERENCES dictionary(id);