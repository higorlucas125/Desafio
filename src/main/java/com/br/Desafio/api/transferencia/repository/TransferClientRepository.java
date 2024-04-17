package com.br.Desafio.api.transferencia.repository;

import com.br.Desafio.api.transferencia.model.TransferClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TransferClientRepository extends JpaRepository<TransferClient, UUID> {
}
