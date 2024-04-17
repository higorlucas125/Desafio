package com.br.Desafio.api.transferencia.services;

import com.br.Desafio.api.transferencia.dto.ClienteResponseDTO;
import com.br.Desafio.api.transferencia.dto.ContaResponseDTO;
import com.br.Desafio.api.transferencia.dto.TransferRequestDTO;
import com.br.Desafio.api.transferencia.model.TransferClient;
import com.br.Desafio.api.transferencia.repository.TransferClientRepository;
import com.br.Desafio.api.transferencia.services.exception.TransferException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Objects;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    TransferClientRepository transferClientRepository;

    @Autowired
    WebClient webClient;

    //   Impedir que falhas momentâneas das dependências da aplicação impactem a
    //   experiência do cliente;
    @Override
    @Transactional(rollbackFor = TransferException.class)
    public void save(TransferRequestDTO transferRequestDTO) throws TransferException {
        ClienteResponseDTO clientOrigin = checkIfYouCanReceiveTransfer(transferRequestDTO.getIdCliente());
        ClienteResponseDTO clientDestiny = checkIfYouCanReceiveTransfer(transferRequestDTO.getConta().getIdDestino());

        ContaResponseDTO contaOrigin = getAccount(transferRequestDTO.getIdCliente());
        ContaResponseDTO contaDestiny = getAccount(transferRequestDTO.getConta().getIdDestino());

        if(Objects.nonNull(clientDestiny) && Objects.nonNull(clientOrigin) && isActivation(contaOrigin) && isActivation(contaDestiny)) {
            if (validateBalanceLimit(contaOrigin, transferRequestDTO.getValor())) {
                    TransferClient transferClient = new TransferClient();
                    transferClient.getTransfer(transferRequestDTO);

                    // deve atulizar a conta, caso de rollback tenho que volta tudo como era antes.
                    transferClientRepository.save(transferClient);
                    try {
                        notifyBacen();
                    } catch (Exception e) {
                        throw new TransferException("Erro ao notificar o BACEN");
                    }
                } else {
                    throw new TransferException("Transferência não autorizada");
                }
            }
    }


    /*A API de contas retornará o limite diário do cliente, caso o valor seja zero ou menor do
   que o valor da transferência a ser realizada, a transferência não poderá ser realizada;*/
    private ContaResponseDTO getAccount(String idconta) {
        return getAccountOrigen(idconta).blockFirst();
    }


    ClienteResponseDTO checkIfYouCanReceiveTransfer(String  idClient) {
        Flux<ClienteResponseDTO> clienteResponseDTOFlux = getDataFromApi(idClient);
        return clienteResponseDTOFlux.blockFirst();
    }

    //Após a transferência é necessário notificar o BACEN de forma síncrona que a transação
    //   foi concluída com sucesso. A API do BACEN tem controle de rate limit e pode retornar
    //   429 em caso de chamadas que excedam o limite;


    private boolean isActivation(ContaResponseDTO contaResponseDTO) {
        return contaResponseDTO.isAtivo();
    }

    private boolean validateBalanceLimit(ContaResponseDTO contaResponseDTO, double valor) {
        return contaResponseDTO.getSaldo() >= valor;
    }


    public Mono<ResponseEntity<Void>> createPutAccount(ContaResponseDTO contaResponseDTO) {

        return webClient.put()
                .uri("/contas/saldos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(contaResponseDTO)
                .retrieve()
                .toBodilessEntity();
    }

    //Após a transferência é necessário notificar o BACEN de forma síncrona que a transação
    //   foi concluída com sucesso. A API do BACEN tem controle de rate limit e pode retornar
    //   429 em caso de chamadas que excedam o limite;

    public Mono<ResponseEntity<Void>> notifyBacen() {

        return webClient.post()
                .uri("/notificacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity();
    }


    public Flux<ContaResponseDTO> getAccountOrigen(String idConta) {

        return webClient.get()
                .uri(apiUrl -> apiUrl.path("/contas/{id}").build(idConta))
                .retrieve()
                .bodyToFlux(ContaResponseDTO.class);
    }


    //TODO criar a classe que quero retornar
    public Flux<ClienteResponseDTO> getDataFromApi(String id) {

        return webClient.get()
                .uri(apiUrl -> apiUrl.path("/clientes/{id}").build(id))
                .retrieve()
                .bodyToFlux(ClienteResponseDTO.class);
    }
}
