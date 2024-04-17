package com.br.Desafio.api.transferencia.model;

import com.br.Desafio.api.transferencia.dto.TransferRequestDTO;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "transfer_client")
public class TransferClient {

    public TransferClient() {

    }

    public TransferClient( String idCliente, double valor, AccountClient accountClient) {
        this.idCliente = idCliente;
        this.valor = valor;
        this.accountClient = accountClient;
    }

    public TransferClient(String idCliente, double valor) {
        this.idCliente = idCliente;
        this.valor = valor;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "id_cliente")
    private String idCliente;

    @Column(name = "valor")
    private double valor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_client_id", referencedColumnName = "id")
    private AccountClient accountClient;

    public TransferClient getTransfer(TransferRequestDTO transferRequestDTO) {
        return new TransferClient(transferRequestDTO.getIdCliente(), transferRequestDTO.getValor());
    }

}
