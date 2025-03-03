package br.com.fmatheus.app.model.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("service_queue")
public class ServiceQueue {

    @Id
    @Column("id")
    private UUID id;

    @NotNull
    @Column("creation_date")
    private LocalDateTime creationDate;

    @NotBlank
    @Column("title")
    private String title;

    @NotBlank
    @Column("problem_description")
    private String problemDescription;

    @NotNull
    @Column("id_attendant")
    private Attendant attendant;

    @NotNull
    @Column("id_customer")
    private Customer customer;

}
