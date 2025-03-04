package br.com.fmatheus.app.model.service.impl;

import br.com.fmatheus.app.model.entity.Customer;
import br.com.fmatheus.app.model.repository.CustomerRepository;
import br.com.fmatheus.app.model.service.CustomerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Customer> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<Customer> save(Customer customer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<Customer> findById(UUID uuid) {
        return this.repository.findById(uuid);
    }

    @Override
    public Mono<Void> deleteById(UUID uuid) {
        throw new UnsupportedOperationException();
    }
}
