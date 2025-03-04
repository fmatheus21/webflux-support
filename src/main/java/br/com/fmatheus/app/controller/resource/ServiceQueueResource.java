package br.com.fmatheus.app.controller.resource;

import br.com.fmatheus.app.controller.dto.response.ServiceQueueResponse;
import br.com.fmatheus.app.controller.facade.ServiceQueueFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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
}
