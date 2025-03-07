CREATE TABLE IF NOT EXISTS service_queue (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    id_attendant UUID,
    id_customer UUID NOT NULL,
    status VARCHAR(30) NOT NULL,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    title VARCHAR(70) NOT NULL,
    problem_description TEXT NOT NULL,
    CONSTRAINT fk_attendant_service FOREIGN KEY (id_attendant) REFERENCES attendant (id) ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_customer_service FOREIGN KEY (id_customer) REFERENCES customer (id) ON UPDATE RESTRICT ON DELETE RESTRICT
);

COMMENT ON COLUMN service_queue.status IS 'Aguardando Atendimento, Em Atendimento e Finalizado';

INSERT INTO service_queue (id_attendant, id_customer, status, creation_date, title, problem_description) VALUES
(NULL, 'd778eb85-81e7-4fc6-b88f-c6ba5f40c856', 'Aguardando Atendimento', '2025-03-03 19:03:25', 'Lorem Ipsum', 'Lorem Ipsum is simply dummy text...'),
(NULL, '20d4d87f-ae48-4729-99b0-4eb0928134ae', 'Aguardando Atendimento', '2025-03-03 19:07:05', 'Lorem Ipsum', 'Lorem Ipsum is simply dummy text...'),
(NULL, '956cae6d-05af-4d94-bcc0-1a4b165a4194', 'Aguardando Atendimento', '2025-03-03 19:10:32', 'Lorem Ipsum', 'Lorem Ipsum is simply dummy text...'),
(NULL, '9b6c990f-8f5b-4018-9d97-f4e875341dcc', 'Aguardando Atendimento', '2025-03-03 19:12:21', 'Lorem Ipsum', 'Lorem Ipsum is simply dummy text...'),
(NULL, '38404aa9-d861-411c-ae68-22a00609d56c', 'Aguardando Atendimento', '2025-03-03 19:13:22', 'Lorem Ipsum', 'Lorem Ipsum is simply dummy text...'),
(NULL, '7ed6a857-e64d-46c6-bc52-f748d060bc77', 'Aguardando Atendimento', '2025-03-03 19:15:33', 'Lorem Ipsum', 'Lorem Ipsum is simply dummy text...'),
(NULL, 'e49cc498-0fba-4581-87f6-c09b59f7f5f5', 'Aguardando Atendimento', '2025-03-03 19:17:21', 'Lorem Ipsum', 'Lorem Ipsum is simply dummy text...'),
(NULL, '6203e6a0-c149-46fd-86dd-cf1fc149a02f', 'Aguardando Atendimento', '2025-03-03 19:20:03', 'Lorem Ipsum', 'Lorem Ipsum is simply dummy text...'),
(NULL, '1b4d2cc4-9e1f-403f-bfa6-f19e6b326f9a', 'Aguardando Atendimento', '2025-03-03 19:22:07', 'Lorem Ipsum', 'Lorem Ipsum is simply dummy text...'),
(NULL, '5c75dda5-dfb4-43cb-b649-b41340ff9294', 'Aguardando Atendimento', '2025-03-03 19:25:10', 'Lorem Ipsum', 'Lorem Ipsum is simply dummy text...');
