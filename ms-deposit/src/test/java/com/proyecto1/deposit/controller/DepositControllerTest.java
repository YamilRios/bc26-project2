package com.proyecto1.deposit.controller;

import com.proyecto1.deposit.dto.DepositDTO;
import com.proyecto1.deposit.entity.Deposit;
import com.proyecto1.deposit.service.DepositService;
import com.proyecto1.deposit.service.impl.DepositServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
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
@WebFluxTest(controllers = DepositController.class)
@Import(DepositServiceImpl.class)
public class DepositControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    DepositService depositService;

    @Test
    void createDepositTest() {
        DepositDTO depositMono = DepositDTO.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .depositAmount(BigDecimal.valueOf(200))
                .description("demo 1")
                .transactionId("234234jnjk2344")
                .build();

        Deposit deposit = new Deposit();
        BeanUtils.copyProperties(depositMono,deposit);

        Mockito.when(depositService.create(depositMono)).thenReturn(Mono.just(deposit));
        webTestClient.post().uri("/deposit/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(depositMono))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(depositService,times(1)).create(depositMono);
    }

    @Test
    void updateDepositTest() {
        DepositDTO depositMono = DepositDTO.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .depositAmount(BigDecimal.valueOf(200))
                .description("demo 1")
                .transactionId("234234jnjk2344")
                .build();
        String id = "6767668789fds9";

        Deposit deposit = new Deposit();
        BeanUtils.copyProperties(depositMono,deposit);

        Mockito.when(depositService.update(depositMono, id))
                .thenReturn(Mono.just(deposit));

        webTestClient.put().uri(uriBuilder -> uriBuilder
                        .path("/deposit/update/{id}")
                        .build(id))
                .body(Mono.just(depositMono), Deposit.class)
                .exchange()
                .expectStatus().isOk();
        Mockito.verify(depositService,times(1)).update(depositMono,id);
    }

    @Test
    void findAll() {
        DepositDTO depositMono = DepositDTO.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .depositAmount(BigDecimal.valueOf(200))
                .description("demo 1")
                .transactionId("234234jnjk2344")
                .build();

        Deposit deposit = new Deposit();
        BeanUtils.copyProperties(depositMono,deposit);

        Mockito.when(depositService.findAll()).thenReturn(Flux.just(deposit));

        webTestClient.get()
                .uri("/deposit/findAll")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Deposit.class);

        Mockito.verify(depositService,times(1)).findAll();
    }

    @Test
     void FindById() {

        Deposit deposit = new Deposit();
        deposit.setId("12buhvg24uhjknv2");
        deposit.setDate(LocalDate.now());
        deposit.setDepositAmount(BigDecimal.valueOf(200));
        deposit.setDescription("demo2");
        deposit.setTransactionId("2323423424");

        Mockito.when(depositService.findById("12buhvg24uhjknv2")).thenReturn(Mono.just(deposit));

        webTestClient.get()
                .uri("/deposit/find/{id}","12buhvg24uhjknv2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.id").isEqualTo("12buhvg24uhjknv2")
                .jsonPath("$.date").isEqualTo("2022-07-20")
                .jsonPath("$.depositAmount").isEqualTo(BigDecimal.valueOf(200))
                .jsonPath("$.description").isEqualTo("demo2")
                .jsonPath("$.transactionId").isEqualTo("2323423424");


        Mockito.verify(depositService,times(1)).findById("12buhvg24uhjknv2");
    }

    @Test
     void Delete() {

        Deposit depositMono = Deposit.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .depositAmount(BigDecimal.valueOf(200))
                .description("demo 1")
                .transactionId("234234jnjk2344")
                .build();

        String id = "unhb2342342342";
        Mockito.when(depositService.delete(id))
                .thenReturn(Mono.just(depositMono));

        webTestClient.delete().uri("/deposit/delete/{id}", id)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(depositService,times(1)).delete(id);

    }
}
