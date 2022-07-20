package com.proyecto1.withdrawal.controller;

import com.proyecto1.withdrawal.entity.Withdrawal;
import com.proyecto1.withdrawal.service.WithdrawalService;
import com.proyecto1.withdrawal.service.impl.WithdrawalServiceImpl;
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

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = WithdrawalController.class)
@Import(WithdrawalServiceImpl.class)
public class WithDrawalControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private WithdrawalService withdrawalService;

    @Test
     void createWithDrawalTest() {
        Withdrawal withdrawalMono = Withdrawal.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .withdrawalAmount(BigDecimal.valueOf(2000))
                .description("demo withdrawal")
                .transactionId("342873574h")
                .build();
        Mockito.when(withdrawalService.create(Mockito.any())).thenReturn(Mono.just(withdrawalMono));
        webTestClient.post().uri("/withdrawal/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(withdrawalMono))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(withdrawalService,times(1)).create(withdrawalMono);
    }

    @Test
     void updateWithDrawalTest() {
        Withdrawal withdrawalMono = Withdrawal.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .withdrawalAmount(BigDecimal.valueOf(2000))
                .description("demo withdrawal")
                .transactionId("342873574h")
                .build();
        String id = "6767668789fds9";
        Mockito.when(withdrawalService.update(withdrawalMono, id))
                .thenReturn(Mono.just(withdrawalMono));

        webTestClient.put().uri(uriBuilder -> uriBuilder
                        .path("/withdrawal/update/{id}")
                        .build(id))
                .body(Mono.just(withdrawalMono), Withdrawal.class)
                .exchange()
                .expectStatus().isOk();
        Mockito.verify(withdrawalService,times(1)).update(withdrawalMono,id);
    }

    @Test
     void findAll() {

        Withdrawal withdrawalMono = Withdrawal.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .withdrawalAmount(BigDecimal.valueOf(2000))
                .description("demo withdrawal")
                .transactionId("342873574h")
                .build();

        Mockito.when(withdrawalService.findAll()).thenReturn(Flux.just(withdrawalMono));

        webTestClient.get()
                .uri("/withdrawal/findAll")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Withdrawal.class);

        Mockito.verify(withdrawalService,times(1)).findAll();
    }

    @Test
     void FindById() {

        Withdrawal signatory = new Withdrawal();
        signatory.setId("12buhvg24uhjknv2");
        signatory.setDate(LocalDate.now());
        signatory.setWithdrawalAmount(BigDecimal.valueOf(2000));
        signatory.setDescription("demo withdrawal");
        signatory.setTransactionId("678676898wdfs");

        Mockito.when(withdrawalService.findById("12buhvg24uhjknv2")).thenReturn(Mono.just(signatory));

        webTestClient.get()
                .uri("/withdrawal/find/{id}","12buhvg24uhjknv2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.id").isEqualTo("12buhvg24uhjknv2")
                .jsonPath("$.date").isEqualTo("2022-07-20")
                .jsonPath("$.withdrawalAmount").isEqualTo(BigDecimal.valueOf(2000))
                .jsonPath("$.description").isEqualTo("demo withdrawal")
                .jsonPath("$.transactionId").isEqualTo("678676898wdfs");


        Mockito.verify(withdrawalService,times(1)).findById("12buhvg24uhjknv2");
    }

    @Test
     void Delete() {

        Withdrawal withdrawalMono = Withdrawal.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .withdrawalAmount(BigDecimal.valueOf(2000))
                .description("demo withdrawal")
                .transactionId("342873574h")
                .build();
        String id = "6767668789fds9";

        Mockito.when(withdrawalService.delete(id))
                .thenReturn(Mono.just(withdrawalMono));

        webTestClient.delete().uri("/withdrawal/delete/{id}", id)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(withdrawalService,times(1)).delete(id);

    }
}
