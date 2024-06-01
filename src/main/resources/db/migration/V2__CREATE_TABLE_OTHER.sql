CREATE TABLE competition (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             date_start DATE NOT NULL,
                             date_end DATE NOT NULL
);
CREATE TABLE participant (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(255) NOT NULL
);
CREATE TABLE result (
                        id SERIAL PRIMARY KEY,
                        score VARCHAR(255) NOT NULL,
                        competition_id INT,
                        participant_id INT,
                        FOREIGN KEY (competition_id) REFERENCES competition(id),
                        FOREIGN KEY (participant_id) REFERENCES participant(id)
);
