package com.br.Desafio.api.transferencia.controller;

import com.br.Desafio.api.transferencia.dto.TransferRequestDTO;
import com.br.Desafio.api.transferencia.services.TransferService;
import com.br.Desafio.api.transferencia.services.exception.TransferException;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransferController {

    private Logger logger = Logger.getLogger(TransferController.class);

    @Autowired
    TransferService transferService;


    @PostMapping("/transfer")
    public ResponseEntity<String> effectTransfer (@RequestBody TransferRequestDTO transferRequestDTO) {
        Assert.notNull(transferRequestDTO, "TransferRequestDTO cannot be null");

        try {
            transferService.save(transferRequestDTO);
            return ResponseEntity.ok().build();
        } catch ( TransferException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
