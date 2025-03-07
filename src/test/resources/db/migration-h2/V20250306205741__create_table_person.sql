CREATE TABLE IF NOT EXISTS person(
    id integer NOT NULL,
    name character(100) NOT NULL,
    document character(15) NOT NULL,
    CONSTRAINT pk_person PRIMARY KEY (id),
    CONSTRAINT unique_document UNIQUE (document)
);

INSERT INTO person(id, name, document) VALUES
(1, 'Gabriel Moreira Cardoso', '35860848030'),
(2, 'Maria Elana Gomes', '85041530025'),
(3, 'Pietra Aparecida Sophia Pires', '11458493776'),
(4, 'Levi Anderson Nogueira', '81864747226'),
(5, 'Gabriela Marlene Cavalcanti', '16416413477'),
(6, 'Isabelly Mirella Teixeira', '99060615700'),
(7, 'Marcela Raquel Almeida', '82524067939'),
(8, 'Sebastião Cauê Duarte', '54039952774'),
(9, 'Otávio Benício Diogo Bernardes', '31143136802'),
(10, 'Giovanna Marli Manuela Pinto', '86380322930'),
(11, 'Tatiane Heloisa Beatriz Assis', '83949574042'),
(12, 'Iago Theo Augusto Pereira', '93663255565'),
(13, 'Sarah Benedita Carla Assis', '92369669349'),
(14, 'Pedro Henrique Manoel Aparício', '72135326933'),
(15, 'Kevin Breno da Cunha', '02136035274');