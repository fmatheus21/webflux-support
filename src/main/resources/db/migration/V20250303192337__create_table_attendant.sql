CREATE TABLE IF NOT EXISTS public.attendant(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    creation_date TIMESTAMP NOT NULL DEFAULT now(),
    id_person integer NOT NULL,
    CONSTRAINT pk_attendant PRIMARY KEY (id),
    CONSTRAINT unique_id_person UNIQUE (id_person),
    CONSTRAINT fk_person_attendant FOREIGN KEY (id_person)
        REFERENCES public.person (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE CASCADE
        NOT VALID
);

INSERT INTO public.attendant(id, creation_date, id_person) VALUES
('41ea930d-c1c5-4f30-959e-86f310054d61', now(), 1);