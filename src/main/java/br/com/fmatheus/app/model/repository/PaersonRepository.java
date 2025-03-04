package br.com.fmatheus.app.model.repository;

import br.com.fmatheus.app.model.entity.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaersonRepository extends ReactiveCrudRepository<Person, Integer> {
}
