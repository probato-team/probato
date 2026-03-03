-- Create table
CREATE TABLE users (
    id UUID PRIMARY KEY,
    name TEXT
);

INSERT INTO users (id, name)
VALUES (uuid(), 'User 01');