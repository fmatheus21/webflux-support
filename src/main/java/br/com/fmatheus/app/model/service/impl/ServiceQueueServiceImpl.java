package br.com.fmatheus.app.model.service.impl;

import br.com.fmatheus.app.model.entity.ServiceQueue;
import br.com.fmatheus.app.model.repository.ServiceQueueRespository;
import br.com.fmatheus.app.model.service.ServiceQueueService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ServiceQueueServiceImpl implements ServiceQueueService {

    private final ServiceQueueRespository respository;

    public ServiceQueueServiceImpl(ServiceQueueRespository respository) {
        this.respository = respository;
    }

    @Override
    public Flux<ServiceQueue> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<ServiceQueue> save(ServiceQueue serviceQueue) {
        return this.respository.save(serviceQueue);
    }

    @Override
    public Mono<ServiceQueue> findById(UUID uuid) {
        return this.respository.findById(uuid);
    }

    @Override
    public Mono<Void> deleteById(UUID uuid) {
        return this.respository.deleteById(uuid);
    }

    @Override
    public Flux<ServiceQueue> findAllByOrderByCreationDateAsc() {
        return this.respository.findAllByOrderByCreationDateAsc();
    }
}
