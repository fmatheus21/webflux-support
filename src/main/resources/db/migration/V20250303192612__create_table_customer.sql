CREATE TABLE IF NOT EXISTS public.customer(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    creation_date TIMESTAMP NOT NULL DEFAULT now(),
    id_person integer NOT NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id),
    CONSTRAINT uniquex_id_person UNIQUE (id_person),
    CONSTRAINT fk_person_customer FOREIGN KEY (id_person)
        REFERENCES public.person (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE CASCADE
        NOT VALID
);

INSERT INTO public.customer(id, creation_date, id_person) VALUES
('d778eb85-81e7-4fc6-b88f-c6ba5f40c856', now(), 2),
('20d4d87f-ae48-4729-99b0-4eb0928134ae', now(), 3),
('956cae6d-05af-4d94-bcc0-1a4b165a4194', now(), 4),
('9b6c990f-8f5b-4018-9d97-f4e875341dcc', now(), 5),
('38404aa9-d861-411c-ae68-22a00609d56c', now(), 6),
('7ed6a857-e64d-46c6-bc52-f748d060bc77', now(), 7),
('e49cc498-0fba-4581-87f6-c09b59f7f5f5', now(), 8),
('6203e6a0-c149-46fd-86dd-cf1fc149a02f', now(), 9),
('1b4d2cc4-9e1f-403f-bfa6-f19e6b326f9a', now(), 10),
('5c75dda5-dfb4-43cb-b649-b41340ff9294', now(), 11),
('eaa1cbfb-d5dc-4e5a-ad40-b9647cbf23e7', now(), 12),
('060c053b-f746-4eee-a63d-24dcd19daa0b', now(), 13),
('13be8e4c-8f13-44d8-9eb3-8c7cb3b07f42', now(), 14),
('61b932b0-c706-4471-b05d-1b18b8e87fac', now(), 15);