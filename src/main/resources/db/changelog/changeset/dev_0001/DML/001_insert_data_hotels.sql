--liquibase formatted sql

INSERT INTO hotels (name, description, brand, house_number, street, city, country, post_code, phone, email, check_in, check_out)
VALUES (
           'Grand Plaza',
           'Luxury 5-star hotel with ocean views',
           'Luxury Collection',
           123,
           'Ocean Drive',
           'Miami',
           'USA',
           '33139',
           '+1-305-1234567',
           'info@grandplaza.com',
           '15:00',
           '11:00'
       );