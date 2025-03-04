package br.com.fmatheus.app.controller.facade;

import br.com.fmatheus.app.controller.converter.ServiceQueueConverter;
import br.com.fmatheus.app.controller.dto.response.ServiceQueueResponse;
import br.com.fmatheus.app.model.service.CustomerService;
import br.com.fmatheus.app.model.service.PersonService;
import br.com.fmatheus.app.model.service.ServiceQueueService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;


/**
 * {@code O que faz Sinks.many().multicast().onBackpressureBuffer()?}
 * Cria um Sink do tipo {@link Sinks.Many} que permite a emissão de múltiplos eventos
 * para múltiplos assinantes (subscribers) simultaneamente.
 * <p>
 * A configuração {@code multicast()} garante que apenas os assinantes conectados no momento
 * da emissão receberão os eventos. Novos assinantes não receberão eventos anteriores à sua conexão.
 * <p>
 * A estratégia {@code onBackpressureBuffer()} permite que os eventos sejam armazenados em um buffer
 * caso os assinantes não consigam consumi-los na mesma velocidade em que são emitidos, prevenindo perda de dados.
 * <p>
 * Esse Sink é útil para streaming de eventos em tempo real, como notificações ou atualizações contínuas.
 */
@Component
public class ServiceQueueFacade {

    private final ServiceQueueService serviceQueueService;
    private final CustomerService customerService;
    private final PersonService personService;
    private final ServiceQueueConverter serviceQueueConverter;
    private final Sinks.Many<ServiceQueueResponse> serviceQueueResponseSinks;

    public ServiceQueueFacade(ServiceQueueService serviceQueueService, CustomerService customerService, PersonService personService, ServiceQueueConverter serviceQueueConverter) {
        this.serviceQueueService = serviceQueueService;
        this.customerService = customerService;
        this.personService = personService;
        this.serviceQueueConverter = serviceQueueConverter;
        this.serviceQueueResponseSinks = Sinks.many().multicast().onBackpressureBuffer();
    }

    public Flux<ServiceQueueResponse> listQueue() {
        var result = serviceQueueService.findAllByOrderByCreationDateAsc()
                .flatMap(serviceQueue -> customerService.findById(serviceQueue.getIdCustomer())
                        .flatMap(customer -> this.personService.findById(customer.getIdPerson())
                                .map(person -> this.serviceQueueConverter.converterToResponse(serviceQueue, customer, person))
                        ));

        return Flux.merge(result, serviceQueueResponseSinks.asFlux()).delayElements(Duration.ofSeconds(5));
    }
}
