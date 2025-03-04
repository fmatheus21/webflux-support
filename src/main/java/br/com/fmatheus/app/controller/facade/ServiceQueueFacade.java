package br.com.fmatheus.app.controller.facade;

import br.com.fmatheus.app.controller.converter.ServiceQueueConverter;
import br.com.fmatheus.app.controller.dto.request.ServiceQueueRequest;
import br.com.fmatheus.app.controller.dto.response.ServiceQueueResponse;
import br.com.fmatheus.app.model.entity.ServiceQueue;
import br.com.fmatheus.app.model.service.CustomerService;
import br.com.fmatheus.app.model.service.PersonService;
import br.com.fmatheus.app.model.service.ServiceQueueService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.UUID;


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


    /**
     * Retorna uma lista de registros da fila de serviço com informações completas de cada registro.
     *
     * <p>Este método realiza as seguintes operações:</p>
     * <ul>
     *     <li>Obtém todos os registros da fila de serviço ordenados pela data de criação.</li>
     *     <li>Para cada registro, busca as informações associadas ao cliente e à pessoa responsável.</li>
     *     <li>Converte cada registro para um {@link ServiceQueueResponse} contendo todos os dados necessários.</li>
     *     <li>Mescla os resultados com os dados emitidos pelo {@code serviceQueueResponseSinks}, com um atraso de 5 segundos entre os elementos.</li>
     * </ul>
     *
     * @return Um {@link Flux} contendo os {@link ServiceQueueResponse} com as informações dos registros da fila de serviço.
     * Os dados são emitidos com um atraso de 5 segundos entre os elementos, e também inclui as emissões do {@code serviceQueueResponseSinks}.
     */
    public Flux<ServiceQueueResponse> listQueue() {
        var result = this.serviceQueueService.findAllByOrderByCreationDateAsc()
                .flatMap(serviceQueue -> this.customerService.findById(serviceQueue.getIdCustomer())
                        .flatMap(customer -> this.personService.findById(customer.getIdPerson())
                                .map(person -> this.serviceQueueConverter.converterToResponse(serviceQueue, customer, person))
                        ));

        return Flux.merge(result, serviceQueueResponseSinks.asFlux()).delayElements(Duration.ofSeconds(5));
    }


    /**
     * Cria uma nova entrada na fila de serviço com base nos dados fornecidos na requisição.
     *
     * <p>Este método converte a requisição {@link ServiceQueueRequest} em uma entidade {@link ServiceQueue},
     * a salva no banco de dados e, em seguida, recupera informações adicionais do cliente e da pessoa
     * associada ao atendimento. O resultado final é convertido em um {@link ServiceQueueResponse}.</p>
     *
     * <p>Além disso, ao concluir com sucesso a criação da entrada na fila de serviço,
     * o resultado é emitido para {@code serviceQueueResponseSinks}.</p>
     *
     * @param request O objeto {@link ServiceQueueRequest} contendo os dados para criação da fila de serviço.
     * @return Um {@link Mono} contendo a resposta {@link ServiceQueueResponse} com os dados completos do serviço criado.
     */
    public Mono<ServiceQueueResponse> create(ServiceQueueRequest request) {
        var converter = this.serviceQueueConverter.converterToEntity(request);
        var commit = this.serviceQueueService.save(converter)
                .flatMap(serviceQueue -> this.customerService.findById(serviceQueue.getIdCustomer())
                        .flatMap(customer -> this.personService.findById(customer.getIdPerson())
                                .map(person -> this.serviceQueueConverter.converterToResponse(serviceQueue, customer, person))
                        ));
        return commit.doOnSuccess(this.serviceQueueResponseSinks::tryEmitNext);
    }


    /**
     * Retorna as informações de um registro da fila de serviço junto com sua posição na fila.
     *
     * <p>O método executa os seguintes passos:</p>
     * <ul>
     *     <li>Obtém a posição do registro na fila ordenada por data de criação.</li>
     *     <li>Busca as informações completas do registro na fila.</li>
     *     <li>Recupera os detalhes do cliente e da pessoa associada ao atendimento.</li>
     *     <li>Retorna um {@link ServiceQueueResponse} contendo todas as informações, incluindo a posição na fila.</li>
     * </ul>
     *
     * @param id O {@link UUID} do registro da fila de serviço cuja posição será determinada.
     * @return Um {@link Mono} contendo {@link ServiceQueueResponse} com as informações do serviço
     * e a posição na fila. Se o registro não for encontrado, retorna um Mono vazio.
     */
    public Mono<ServiceQueueResponse> positionQueue(UUID id) {
        // Consulta a posição na fila
        Mono<Integer> positionMono = this.serviceQueueService.findAllByOrderByCreationDateAsc()
                .index() // Associa um índice a cada item (posição começa em 0)
                .filter(tuple -> tuple.getT2().getId().equals(id)) // Filtra pelo ID
                .map(tuple -> tuple.getT1().intValue() + 1) // Soma 1 para posição começar em 1
                .singleOrEmpty(); // Retorna o primeiro encontrado ou vazio

        // Consulta as informações do registro e inclui a posição
        return positionMono.flatMap(position ->
                this.serviceQueueService.findById(id)
                        .flatMap(serviceQueue -> this.customerService.findById(serviceQueue.getIdCustomer())
                                .flatMap(customer -> this.personService.findById(customer.getIdPerson())
                                        .map(person -> this.serviceQueueConverter.converterToResponseWithPositon(serviceQueue, customer, person, position))
                                )
                        )
        );
    }

}
