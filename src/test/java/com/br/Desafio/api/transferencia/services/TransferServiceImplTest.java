//package com.br.Desafio.api.transferencia.services;
//
//import com.br.Desafio.api.transferencia.dto.ClienteResponseDTO;
//import com.br.Desafio.api.transferencia.dto.ContaResponseDTO;
//import com.br.Desafio.api.transferencia.dto.TransferRequestDTO;
//import com.br.Desafio.api.transferencia.dto.TransferenciaResponseDTO;
//import com.br.Desafio.api.transferencia.model.AccountClient;
//import com.br.Desafio.api.transferencia.model.TransferClient;
//import com.br.Desafio.api.transferencia.repository.TransferClientRepository;
//import com.br.Desafio.api.transferencia.services.exception.TransferException;
//import com.github.tomakehurst.wiremock.WireMockServer;
//import com.github.tomakehurst.wiremock.client.WireMock;
//import com.google.gson.Gson;
//import org.aspectj.lang.annotation.After;
//import org.junit.jupiter.api.AfterEach;
//import org.slf4j.Logger;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.util.ReflectionTestUtils;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import reactor.core.publisher.Mono;
//import reactor.test.StepVerifier;
//
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.spy;
//import static org.mockito.Mockito.when;
//
//public class TransferServiceImplTest {
//
//    private WireMockServer wireMockServer;
//
//    private TransferServiceImpl transferService;
//
//    private Gson gson = new Gson();
//
//
//    @BeforeEach
//    void setUp() {
//        // Inicializar o servidor WireMock
//        wireMockServer = new WireMockServer(8088);
//        wireMockServer.start();
//
//        TransferClientRepository transferClientRepository = mock(TransferClientRepository.class);
//
//        // Configurar a URL base do WebClient para apontar para o servidor WireMock
//        WebClient webClient = WebClient.builder()
//                .baseUrl(wireMockServer.baseUrl())
//                .build();
//
//        transferService = spy(TransferServiceImpl.class);
//
//        ReflectionTestUtils.setField(transferService, "webClient", webClient);
//        ReflectionTestUtils.setField(transferService,"transferClientRepository",transferClientRepository);
//
//        when(transferClientRepository.save(any())).thenReturn(new TransferClient());
//    }
//
//    @AfterEach
//    void finish (){
//      wireMockServer.stop();
//    }
//
//    @Test
//    public void notifyBance_whenTransferIsSuccess() throws TransferException {
//
//        TransferRequestDTO transferRequestDTO = new TransferRequestDTO("123", 456, new TransferRequestDTO.Conta("123","234") );
//
//        mockEndpointBacen();
//
//        transferService.save(transferRequestDTO);
//
//
//        Mono<ResponseEntity<Void>> result = transferService.notifyBacen();
//
//        StepVerifier.create(result)
//                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.OK)
//                .verifyComplete();
//    }
//
//    private void mockEndpointBacen() {
//        wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/notificacoes"))
//                .willReturn(WireMock.aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("{}")));
//
//        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/clientes/123"))
//                .willReturn(WireMock.aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", "application/json")
//                        .withBody(gson.toJson(getClientDto()))));
//
//        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/clientes/234"))
//            .willReturn(WireMock.aResponse()
//                .withStatus(200)
//                .withHeader("Content-Type", "application/json")
//                .withBody(gson.toJson(getClientDto()))));
//
//
//        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/contas/123"))
//                .willReturn(WireMock.aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", "application/json")
//                        .withBody(gson.toJson(getContaDto()))));
//
//        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/contas/234"))
//            .willReturn(WireMock.aResponse()
//                .withStatus(200)
//                .withHeader("Content-Type", "application/json")
//                .withBody(gson.toJson(getContaDto()))));
//    }
//
//    private ClienteResponseDTO getClientDto (){
//        return new ClienteResponseDTO("123", "João", "Silva", "12345678900");
//    }
//    private ContaResponseDTO getContaDto (){
//        return new ContaResponseDTO("123", 1000.0, 200.0,true);
//    }
//}
