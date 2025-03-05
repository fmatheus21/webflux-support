package br.com.fmatheus.app.controller.enumerable;

import lombok.Getter;

@Getter
public enum StatusQueueEnum {

    AGUARDANDO_ATENDIMENTO("Aguardando Atendimento"),
    EM_ATENDIMENTO("Em Atendimento"),
    FINALIZADO("Finalizado");

    private final String status;

    StatusQueueEnum(String status) {
        this.status = status;
    }


}
