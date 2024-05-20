CREATE TABLE dictionary(
    id UUID PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    key VARCHAR(50) NOT NULL,
    value VARCHAR(50) NOT NULL,
    description VARCHAR(100)
);

INSERT INTO dictionary(id, code, key, value, description)
VALUES (gen_random_uuid(), 'RATE_REQUEST_STATUS', 'CREATED', 'Запрос создан', null),
       (gen_random_uuid(), 'RATE_REQUEST_STATUS', 'SENT', 'Запрос отправлен', null),
       (gen_random_uuid(), 'RATE_REQUEST_STATUS', 'PROCESSED', 'Запрос в обработке', null),
       (gen_random_uuid(), 'RATE_REQUEST_STATUS', 'FAILED', 'Запрос не выполнен', null),
       (gen_random_uuid(), 'RATE_REQUEST_STATUS', 'SUCCESSFUL', 'Запрос выполнен', null),
       (gen_random_uuid(), 'RATE_REQUEST_INFO', 'ON_DAY', 'Курс валют за день', null),
       (gen_random_uuid(), 'RATE_REQUEST_INFO', 'LATEST', 'Последний курс валют', null);