package br.com.fmatheus.app.model.repository;

import br.com.fmatheus.app.model.entity.ServiceQueue;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ServiceQueueRespository extends ReactiveCrudRepository<ServiceQueue, UUID> {

    Flux<ServiceQueue> findAllByOrderByCreationDateAsc();

    Flux<ServiceQueue> findByIdAttendantIsNullAndStatusOrderByCreationDateAsc(String status);

    Flux<ServiceQueue> findByStatusOrderByCreationDateAsc(String status);

    Mono<ServiceQueue> findFirstByStatusOrderByCreationDateAsc(String status);
}
