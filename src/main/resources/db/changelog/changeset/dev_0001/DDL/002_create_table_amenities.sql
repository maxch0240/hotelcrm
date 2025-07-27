--liquibase formatted sql

CREATE TABLE IF NOT EXISTS hotels_amenities (
                                 hotel_id BIGINT NOT NULL,
                                 amenity VARCHAR(100) NOT NULL,
                                 PRIMARY KEY (hotel_id, amenity)
);

ALTER TABLE hotels_amenities
    ADD CONSTRAINT fk_amenities_hotel
        FOREIGN KEY (hotel_id) REFERENCES hotels(id)
            ON DELETE CASCADE;

-- rollback DROP TABLE mvp.hotels_amenities;