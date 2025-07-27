--liquibase formatted sql

INSERT INTO hotels_amenities (hotel_id, amenity)
SELECT id, 'Free WiFi' FROM hotels WHERE name = 'Grand Plaza'
UNION ALL
SELECT id, 'Swimming Pool' FROM hotels WHERE name = 'Grand Plaza'
UNION ALL
SELECT id, 'Spa' FROM hotels WHERE name = 'Grand Plaza';