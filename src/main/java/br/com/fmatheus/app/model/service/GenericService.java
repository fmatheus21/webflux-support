package br.com.fmatheus.app.model.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenericService<T, ID> {

    Flux<T> findAll();

    Mono<T> save(T t);

    Mono<T> findById(ID id);

    Mono<Void> deleteById(ID id);
}
