CREATE SEQUENCE hibernate_sequence START 1;

CREATE TABLE app_user (
                       id BIGINT PRIMARY KEY DEFAULT nextval('hibernate_sequence'),
                       password VARCHAR(255),
                       username VARCHAR(64) NOT NULL UNIQUE
);