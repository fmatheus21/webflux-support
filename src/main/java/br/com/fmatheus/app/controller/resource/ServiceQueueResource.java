package br.com.fmatheus.app.controller.resource;

import br.com.fmatheus.app.controller.dto.request.ServiceQueueRequest;
import br.com.fmatheus.app.controller.dto.response.ServiceQueueResponse;
import br.com.fmatheus.app.controller.facade.ServiceQueueFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/queue")
public class ServiceQueueResource {

    private final ServiceQueueFacade facade;

    public ServiceQueueResource(ServiceQueueFacade facade) {
        this.facade = facade;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServiceQueueResponse> listQueue() {
        return this.facade.listQueue();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<ServiceQueueResponse> create(@Valid @RequestBody ServiceQueueRequest request) {
        return this.facade.create(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{uuid}")
    public Mono<ServiceQueueResponse> positionQueue(@PathVariable UUID uuid) {
        return this.facade.positionQueue(uuid);
    }
}
