package br.com.fmatheus.app.controller.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceQueueResponse {
    private UUID id;
    private LocalDateTime creationDate;
    private String title;
    private String problemDescription;
    private CustomerResponse customer;

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerResponse {
        private UUID id;
        private String name;
        private LocalDateTime creationDate;
    }

}
