ALTER TABLE rate ADD COLUMN max_value_for_day DOUBLE PRECISION;
ALTER TABLE rate ADD COLUMN min_value_for_day DOUBLE PRECISION;
ALTER TABLE rate ADD COLUMN difference_with_previous DOUBLE PRECISION;

UPDATE rate SET
                max_value_for_day = conversion_rate,
                min_value_for_day = conversion_rate,
                difference_with_previous = conversion_rate;