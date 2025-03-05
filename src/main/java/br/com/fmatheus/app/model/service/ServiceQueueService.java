package br.com.fmatheus.app.model.service;

import br.com.fmatheus.app.controller.enumerable.StatusQueueEnum;
import br.com.fmatheus.app.model.entity.ServiceQueue;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ServiceQueueService extends GenericService<ServiceQueue, UUID> {

    Flux<ServiceQueue> findAllByOrderByCreationDateAsc();

    Flux<ServiceQueue> findByIdAttendantIsNullOrderByCreationDateAsc();

    Flux<ServiceQueue> findByStatusOrderByCreationDateAsc(StatusQueueEnum status);

    Mono<ServiceQueue> findFirstByStatusOrderByCreationDateAsc(String status);
}
