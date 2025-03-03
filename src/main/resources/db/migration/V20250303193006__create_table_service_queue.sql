CREATE TABLE IF NOT EXISTS public.service_queue(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    id_attendant uuid NOT NULL,
    id_customer uuid NOT NULL,
    creation_date TIMESTAMP NOT NULL DEFAULT now(),
    title character(70) COLLATE pg_catalog."default" NOT NULL,
    problem_description text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_service_queue PRIMARY KEY (id),
    CONSTRAINT fk_attendant_service FOREIGN KEY (id_attendant)
        REFERENCES public.attendant (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
        NOT VALID,
    CONSTRAINT fk_customer_service FOREIGN KEY (id_customer)
        REFERENCES public.customer (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
        NOT VALID
)
