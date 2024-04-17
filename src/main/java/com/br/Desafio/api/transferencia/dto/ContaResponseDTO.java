package com.br.Desafio.api.transferencia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaResponseDTO {

    private String id;
    private double saldo;
    private double limiteDiario;
    private boolean ativo;

    public ContaResponseDTO(String id, double saldo, double limiteDiario, boolean ativo) {
        this.id = id;
        this.saldo = saldo;
        this.limiteDiario = limiteDiario;
        this.ativo = ativo;
    }

    public ContaResponseDTO() {
    }

}
