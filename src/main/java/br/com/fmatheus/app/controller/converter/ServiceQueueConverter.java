package br.com.fmatheus.app.controller.converter;

import br.com.fmatheus.app.controller.dto.request.ServiceQueueRequest;
import br.com.fmatheus.app.controller.dto.response.ServiceQueueResponse;
import br.com.fmatheus.app.model.entity.Customer;
import br.com.fmatheus.app.model.entity.Person;
import br.com.fmatheus.app.model.entity.ServiceQueue;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ServiceQueueConverter {


    public ServiceQueue converterToEntity(ServiceQueueRequest request) {
        return ServiceQueue.builder()
                .creationDate(LocalDateTime.now())
                .title(request.getTitle())
                .problemDescription(request.getProblemDescription())
                .idCustomer(request.getIdCustomer())
                .build();
    }

    public ServiceQueueResponse converterToResponse(ServiceQueue serviceQueue, Customer customer, Person person) {
        return ServiceQueueResponse.builder()
                .id(serviceQueue.getId())
                .creationDate(serviceQueue.getCreationDate())
                .title(serviceQueue.getTitle())
                .problemDescription(serviceQueue.getProblemDescription())
                .customer(ServiceQueueResponse.CustomerResponse.builder()
                        .id(customer.getId())
                        .creationDate(customer.getCreationDate())
                        .name(person.getName())
                        .build())
                .build();
    }

    public ServiceQueueResponse converterToResponseWithPositon(ServiceQueue serviceQueue, Customer customer, Person person, Integer position) {
        return ServiceQueueResponse.builder()
                .id(serviceQueue.getId())
                .creationDate(serviceQueue.getCreationDate())
                .title(serviceQueue.getTitle())
                .problemDescription(serviceQueue.getProblemDescription())
                .positionQueue(position)
                .customer(ServiceQueueResponse.CustomerResponse.builder()
                        .id(customer.getId())
                        .creationDate(customer.getCreationDate())
                        .name(person.getName())
                        .build())
                .build();
    }


}
