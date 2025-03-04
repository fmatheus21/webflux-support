package br.com.fmatheus.app.model.service.impl;

import br.com.fmatheus.app.model.entity.Person;
import br.com.fmatheus.app.model.repository.PaersonRepository;
import br.com.fmatheus.app.model.service.PersonService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements PersonService {

    private final PaersonRepository repository;

    public PersonServiceImpl(PaersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Person> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<Person> save(Person person) {
        return this.repository.save(person);
    }

    @Override
    public Mono<Person> findById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return this.repository.deleteById(id);
    }
    
}
