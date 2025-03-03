package br.com.fmatheus.app.model.entity;

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
@Table("customer")
public class Customer {

    @Id
    @Column("id")
    private UUID id;

    @NotNull
    @Column("creation_date")
    private LocalDateTime creationDate;

    @NotNull
    @Column("id_person")
    private Person person;


}
