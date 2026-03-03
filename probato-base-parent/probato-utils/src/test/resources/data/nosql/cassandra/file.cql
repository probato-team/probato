-- Create table
CREATE TABLE testks.users (
    id UUID PRIMARY KEY,
    name TEXT
);

INSERT INTO testks.users (id, name)
VALUES (uuid(), 'User 01');