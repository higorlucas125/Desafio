package com.br.Desafio.api.transferencia.services;

import com.br.Desafio.api.transferencia.dto.TransferRequestDTO;
import com.br.Desafio.api.transferencia.services.exception.TransferException;


public interface TransferService {

    void save(TransferRequestDTO transferRequestDTO) throws TransferException;

}
