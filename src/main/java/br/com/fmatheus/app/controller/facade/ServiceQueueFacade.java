package br.com.fmatheus.app.controller.facade;

import br.com.fmatheus.app.controller.converter.ServiceQueueConverter;
import br.com.fmatheus.app.controller.dto.request.ServeNextQueueRequest;
import br.com.fmatheus.app.controller.dto.request.ServiceQueueRequest;
import br.com.fmatheus.app.controller.dto.response.ServiceQueueResponse;
import br.com.fmatheus.app.controller.enumerable.StatusQueueEnum;
import br.com.fmatheus.app.model.entity.ServiceQueue;
import br.com.fmatheus.app.model.service.CustomerService;
import br.com.fmatheus.app.model.service.PersonService;
import br.com.fmatheus.app.model.service.ServiceQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.UUID;


/**
 * Classe responsável por orquestrar a lógica de atendimento da fila de serviços.
 * Fornece métodos para listar, criar e atualizar registros na fila.
 *
 * <p>
 * {@code O que faz Sinks.many().multicast().onBackpressureBuffer()?}
 * Cria um Sink do tipo {@link Sinks.Many} que permite a emissão de múltiplos eventos
 * para múltiplos assinantes (subscribers) simultaneamente.
 * </p>
 * <p>
 * A configuração {@code multicast()} garante que apenas os assinantes conectados no momento
 * da emissão receberão os eventos. Novos assinantes não receberão eventos anteriores à sua conexão.
 * </p>
 * <p>
 * A estratégia {@code onBackpressureBuffer()} permite que os eventos sejam armazenados em um buffer
 * caso os assinantes não consigam consumi-los na mesma velocidade em que são emitidos, prevenindo perda de dados.
 * <p>
 * Esse Sink é útil para streaming de eventos em tempo real, como notificações ou atualizações contínuas.
 */

@Slf4j
@Component
public class ServiceQueueFacade {

    private final ServiceQueueService serviceQueueService;
    private final CustomerService customerService;
    private final PersonService personService;
    private final ServiceQueueConverter serviceQueueConverter;
    private final Sinks.Many<ServiceQueueResponse> serviceQueueResponseSinks;

    /**
     * Construtor da classe que inicializa os serviços necessários.
     *
     * @param serviceQueueService   Serviço responsável por manipular a fila.
     * @param customerService       Serviço responsável pelos clientes.
     * @param personService         Serviço responsável pelos dados da pessoa.
     * @param serviceQueueConverter Conversor de entidade para DTO.
     */
    public ServiceQueueFacade(ServiceQueueService serviceQueueService, CustomerService customerService, PersonService personService, ServiceQueueConverter serviceQueueConverter) {
        this.serviceQueueService = serviceQueueService;
        this.customerService = customerService;
        this.personService = personService;
        this.serviceQueueConverter = serviceQueueConverter;
        this.serviceQueueResponseSinks = Sinks.many().multicast().onBackpressureBuffer();
    }


    /**
     * Lista os registros da fila de atendimento com um determinado status.
     * A resposta é emitida com um delay de 5 segundos para sincronização.
     *
     * <p>Este método realiza as seguintes operações:</p>
     * <ul>
     *     <li>Obtém todos os registros da fila de serviço baseados no status fornecido e ordenados pela data de criação.</li>
     *     <li>Para cada registro, busca as informações associadas ao cliente e sua posição através do médoto {@code positionQueue()}.</li>
     *     <li>Mescla os resultados com os dados emitidos pelo {@code serviceQueueResponseSinks}, com um atraso de 5 segundos entre os elementos.</li>
     * </ul>
     *
     * @param status Status da fila a ser filtrado.
     * @return Um {@link Flux} contendo os {@link ServiceQueueResponse} com as informações dos registros da fila de serviço.
     * Os dados são emitidos com um atraso de 5 segundos entre os elementos, e também inclui as emissões do {@code serviceQueueResponseSinks}.
     */
    public Flux<ServiceQueueResponse> listQueue(StatusQueueEnum status) {
        log.info("Listando a fila de atendimento pelo status [{}]", status.getStatus());
        var result = this.serviceQueueService.findByStatusOrderByCreationDateAsc(status)
                .flatMap(serviceQueue -> this.positionQueue(serviceQueue.getId()));
        return Flux.merge(result, serviceQueueResponseSinks.asFlux()).delayElements(Duration.ofSeconds(5));
    }


    /**
     * Cria uma nova entrada na fila de serviço com base nos dados fornecidos na requisição.
     *
     * <p>Este método converte a requisição {@link ServiceQueueRequest} em uma entidade {@link ServiceQueue},
     * a salva no banco de dados e, em seguida, recupera informações adicionais do cliente e a sua posição ao atendimento através do métod {@code positionQueue()}.
     * O resultado final é convertido em um {@link ServiceQueueResponse}.</p>
     *
     * <p>Além disso, ao concluir com sucesso a criação da entrada na fila de serviço, o resultado é emitido para {@code serviceQueueResponseSinks}.</p>
     *
     * @param request O objeto {@link ServiceQueueRequest} contendo os dados para criação da fila de serviço.
     * @return Um {@link Mono} contendo a resposta {@link ServiceQueueResponse} com os dados completos do serviço criado.
     */
    public Mono<ServiceQueueResponse> create(ServiceQueueRequest request) {
        log.info("Criando novo registro de Suporte.");
        var converter = this.serviceQueueConverter.converterToEntity(request, StatusQueueEnum.AGUARDANDO_ATENDIMENTO);
        var commit = this.serviceQueueService.save(converter)
                .flatMap(serviceQueue -> this.positionQueue(serviceQueue.getId()));
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
        log.info("Obtendo a posicao do cliente de id {}", id);
        // Consulta a posição na fila
        Mono<Integer> positionMono = this.serviceQueueService.findByIdAttendantIsNullAndStatusOrderByCreationDateAsc(StatusQueueEnum.AGUARDANDO_ATENDIMENTO)
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


    /**
     * Inicia o atendimento do próximo cliente na fila.
     *
     * @param request Objeto contendo os dados do atendente que assumirá o atendimento.
     * @return Um {@link Mono} contendo a resposta {@link ServiceQueueResponse} com os dados completos do serviço criado.
     */
    public Mono<ServiceQueueResponse> serveNextQueue(ServeNextQueueRequest request) {
        log.info("Iniciando atendimento ao proximo da fila.");
        var result = this.serviceQueueService.findFirstByStatusOrderByCreationDateAsc(StatusQueueEnum.AGUARDANDO_ATENDIMENTO.getStatus())
                .map(serviceQueue -> this.serviceQueueConverter.converterToEntityServeNextQueue(serviceQueue, request));

        var commit = result.flatMap(this.serviceQueueService::save)
                .flatMap(serviceQueue -> this.customerService.findById(serviceQueue.getIdCustomer())
                        .flatMap(customer -> this.personService.findById(customer.getIdPerson())
                                .map(person -> this.serviceQueueConverter.converterToResponse(serviceQueue, customer, person))
                        ));
        return commit.doOnSuccess(this.serviceQueueResponseSinks::tryEmitNext);

    }
}
