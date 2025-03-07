CREATE TABLE IF NOT EXISTS attendant (
    id UUID NOT NULL DEFAULT RANDOM_UUID(),
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    id_person INTEGER NOT NULL,
    CONSTRAINT pk_attendant PRIMARY KEY (id),
    CONSTRAINT unique_id_person UNIQUE (id_person),
    CONSTRAINT fk_person_attendant FOREIGN KEY (id_person)
        REFERENCES person (id)
        ON UPDATE RESTRICT
        ON DELETE CASCADE
);

INSERT INTO attendant(id, creation_date, id_person) VALUES
('41ea930d-c1c5-4f30-959e-86f310054d61', CURRENT_TIMESTAMP(), 1);