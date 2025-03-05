package br.com.fmatheus.app.controller.converter;

import br.com.fmatheus.app.controller.dto.request.ServeNextQueueRequest;
import br.com.fmatheus.app.controller.dto.request.ServiceQueueRequest;
import br.com.fmatheus.app.controller.dto.response.ServiceQueueResponse;
import br.com.fmatheus.app.controller.enumerable.StatusQueueEnum;
import br.com.fmatheus.app.model.entity.Customer;
import br.com.fmatheus.app.model.entity.Person;
import br.com.fmatheus.app.model.entity.ServiceQueue;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ServiceQueueConverter {


    public ServiceQueue converterToEntity(ServiceQueueRequest request, StatusQueueEnum status) {
        return ServiceQueue.builder()
                .creationDate(LocalDateTime.now())
                .title(request.getTitle())
                .problemDescription(request.getProblemDescription())
                .status(status.getStatus())
                .idCustomer(request.getIdCustomer())
                .build();
    }

    public ServiceQueueResponse converterToResponse(ServiceQueue serviceQueue, Customer customer, Person person) {
        return ServiceQueueResponse.builder()
                .id(serviceQueue.getId())
                .creationDate(serviceQueue.getCreationDate())
                .title(serviceQueue.getTitle())
                .problemDescription(serviceQueue.getProblemDescription())
                .status(serviceQueue.getStatus())
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
                .status(serviceQueue.getStatus())
                .customer(ServiceQueueResponse.CustomerResponse.builder()
                        .id(customer.getId())
                        .creationDate(customer.getCreationDate())
                        .name(person.getName())
                        .build())
                .build();
    }

    public ServiceQueue converterToEntityServeNextQueue(ServiceQueue serviceQueue, ServeNextQueueRequest request) {
        serviceQueue.setIdAttendant(request.getIdAttendant());
        serviceQueue.setStatus(StatusQueueEnum.EM_ATENDIMENTO.getStatus());
        return serviceQueue;
    }


}
