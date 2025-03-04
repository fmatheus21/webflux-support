package br.com.fmatheus.app.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceQueueRequest {

    @NotNull
    private String title;

    @NotNull
    private String problemDescription;

    @NotNull
    private UUID idCustomer;
    
}
