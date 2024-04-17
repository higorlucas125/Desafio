package com.br.Desafio.api.transferencia.dto;

import com.br.Desafio.api.transferencia.model.AccountClient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequestDTO {
    private String idCliente;
    private double valor;
    private Conta conta;

    @Getter
    @Setter
    public static class Conta {
        private String idOrigem;
        private String idDestino;
    }

    public TransferRequestDTO(String idCliente, double valor, Conta conta) {
        this.idCliente = idCliente;
        this.valor = valor;
        this.conta = conta;
    }

}
