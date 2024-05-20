CREATE TABLE dictionary(
       id UUID PRIMARY KEY,
       code VARCHAR(50) NOT NULL,
       key VARCHAR(50) NOT NULL,
       value VARCHAR(50) NOT NULL,
       description VARCHAR(100)
);

INSERT INTO dictionary(id, code, key, value, description)
VALUES (gen_random_uuid(), 'REQUEST_STATUS', 'CREATED', 'Запрос на продажу создан', null),
       (gen_random_uuid(), 'REQUEST_STATUS', 'ON_EDITING', 'Запрос на продажу на редактировании', null),
       (gen_random_uuid(), 'REQUEST_STATUS', 'ACTIVE', 'Запрос на продажу активен', null),
       (gen_random_uuid(), 'REQUEST_STATUS', 'COMPLETED', 'Запрос на продажу завершен', null);