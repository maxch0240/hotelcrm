--liquibase formatted sql

INSERT INTO hotels_amenities (hotel_id, amenity)
SELECT id, 'Free WiFi' FROM hotels WHERE name = 'Grand Plaza'
UNION ALL
SELECT id, 'Swimming Pool' FROM hotels WHERE name = 'Grand Plaza'
UNION ALL
SELECT id, 'Spa' FROM hotels WHERE name = 'Grand Plaza'

UNION ALL
SELECT id, 'Free WiFi' FROM hotels WHERE name = 'DoubleTree by Hilton Minsk'
UNION ALL
SELECT id, 'On-site restaurant' FROM hotels WHERE name = 'DoubleTree by Hilton Minsk'
UNION ALL
SELECT id, 'Fitness center' FROM hotels WHERE name = 'DoubleTree by Hilton Minsk'
UNION ALL
SELECT id, 'Pet-friendly rooms' FROM hotels WHERE name = 'DoubleTree by Hilton Minsk'
UNION ALL
SELECT id, 'Meeting rooms' FROM hotels WHERE name = 'DoubleTree by Hilton Minsk'

UNION ALL
SELECT id, 'Free WiFi' FROM hotels WHERE name = 'DoubleTree by Hilton Minsk2222222222';
