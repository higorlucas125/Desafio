package com.br.Desafio.api.transferencia.services.exception;

public class TransferException extends Exception{
    public TransferException(String message) {
        super(message);
    }

    public TransferException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferException(Throwable cause) {
        super(cause);
    }
}
