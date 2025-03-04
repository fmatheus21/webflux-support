package br.com.fmatheus.app.model.service;

import br.com.fmatheus.app.model.entity.ServiceQueue;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ServiceQueueService extends GenericService<ServiceQueue, UUID> {

    Flux<ServiceQueue> findAllByOrderByCreationDateAsc();
}
