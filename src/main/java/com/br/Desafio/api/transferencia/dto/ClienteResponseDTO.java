package com.br.Desafio.api.transferencia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponseDTO {

    private String id;
    private String nome;
    private String telefone;
    private String tipoPessoa;

    public ClienteResponseDTO(String id, String nome, String telefone, String tipoPessoa) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.tipoPessoa = tipoPessoa;
    }

    public ClienteResponseDTO() {
    }
}
