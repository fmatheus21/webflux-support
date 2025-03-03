package br.com.fmatheus.app.model.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("person")
public class Person {

    @Id
    @Column("id")
    private Integer id;

    @NotBlank
    @Column("name")
    private String name;

    @NotBlank
    @Column("document")
    private String document;
    
}
