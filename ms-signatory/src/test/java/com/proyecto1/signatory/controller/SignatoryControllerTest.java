package com.proyecto1.signatory.controller;

import com.proyecto1.signatory.entity.Signatory;
import com.proyecto1.signatory.service.SignatoryService;
import com.proyecto1.signatory.service.impl.SignatoryServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = SignatoryController.class)
@Import(SignatoryServiceImpl.class)
public class SignatoryControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private SignatoryService signatoryService;

    @Test
     void createDepositTest() {
        Signatory signatoryMono = Signatory.builder()
                .id(ObjectId.get().toString())
                .name("yasmin")
                .lastName("zegarra")
                .docNumber("8797897797")
                .transactionId("342873574h")
                .build();
        Mockito.when(signatoryService.create(signatoryMono)).thenReturn(Mono.just(signatoryMono));
        webTestClient.post().uri("/signatory/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(signatoryMono))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(signatoryService,times(1)).create(signatoryMono);
    }

    @Test
     void updateDepositTest() {
        Signatory signatoryMono = Signatory.builder()
                .id(ObjectId.get().toString())
                .name("yasmin")
                .lastName("zegarra")
                .docNumber("8797897797")
                .transactionId("342873574h")
                .build();
        String id = "6767668789fds9";
        Mockito.when(signatoryService.update(signatoryMono, id))
                .thenReturn(Mono.just(signatoryMono));

        webTestClient.put().uri(uriBuilder -> uriBuilder
                        .path("/signatory/update/{id}")
                        .build(id))
                .body(Mono.just(signatoryMono), Signatory.class)
                .exchange()
                .expectStatus().isOk();
        Mockito.verify(signatoryService,times(1)).update(signatoryMono,id);
    }

    @Test
     void findAll() {

        Signatory signatoryMono = Signatory.builder()
                .id(ObjectId.get().toString())
                .name("yasmin")
                .lastName("zegarra")
                .docNumber("8797897797")
                .transactionId("342873574h")
                .build();
        String id = "6767668789fds9";

        Mockito.when(signatoryService.findAll()).thenReturn(Flux.just(signatoryMono));

        webTestClient.get()
                .uri("/signatory/findAll")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Signatory.class);

        Mockito.verify(signatoryService,times(1)).findAll();
    }

    @Test
     void FindById() {

        Signatory signatory = new Signatory();
        signatory.setId("12buhvg24uhjknv2");
        signatory.setName("zoraida");
        signatory.setLastName("oyarce");
        signatory.setDocNumber("76767669");
        signatory.setTransactionId("678676898wdfs");

        Mockito.when(signatoryService.findById("12buhvg24uhjknv2")).thenReturn(Mono.just(signatory));

        webTestClient.get()
                .uri("/signatory/find/{id}","12buhvg24uhjknv2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.id").isEqualTo("12buhvg24uhjknv2")
                .jsonPath("$.name").isEqualTo("zoraida")
                .jsonPath("$.lastName").isEqualTo("oyarce")
                .jsonPath("$.docNumber").isEqualTo("76767669")
                .jsonPath("$.transactionId").isEqualTo("678676898wdfs");


        Mockito.verify(signatoryService,times(1)).findById("12buhvg24uhjknv2");
    }

    @Test
     void Delete() {

        Signatory signatoryMono = Signatory.builder()
                .id(ObjectId.get().toString())
                .name("yasmin")
                .lastName("zegarra")
                .docNumber("8797897797")
                .transactionId("342873574h")
                .build();
        String id = "6767668789fds9";

        Mockito.when(signatoryService.delete(id))
                .thenReturn(Mono.just(signatoryMono));

        webTestClient.delete().uri("/signatory/delete/{id}", id)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(signatoryService,times(1)).delete(id);

    }
}
