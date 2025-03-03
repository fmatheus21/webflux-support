CREATE TABLE IF NOT EXISTS public.customer(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    creation_date TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

INSERT INTO public.customer(id, creation_date) VALUES
('d778eb85-81e7-4fc6-b88f-c6ba5f40c856', now());