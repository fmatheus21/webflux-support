CREATE TABLE IF NOT EXISTS public.person(
    id integer NOT NULL,
    name character(100) COLLATE pg_catalog."default" NOT NULL,
    document character(15) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_person PRIMARY KEY (id),
    CONSTRAINT unique_docuement UNIQUE (document)
);

INSERT INTO public.person(id, name, document) VALUES
(1, 'Gabriel Moreira Cardoso', '35860848030'),
(2, 'Maria Elana Gomes', '85041530025');