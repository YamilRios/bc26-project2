package com.proyecto1.payment.controller;

import com.proyecto1.payment.entity.Payment;
import com.proyecto1.payment.service.PaymentService;
import com.proyecto1.payment.service.impl.PaymentServiceImpl;
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
@WebFluxTest(controllers = PaymentController.class)
@Import(PaymentServiceImpl.class)
public class PaymentControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PaymentService paymentService;

    @Test
     void createDepositTest() {
        Payment paymentMono = Payment.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .paymentAmount(BigDecimal.valueOf(200))
                .description("demo payment")
                .transactionId("234234jnjk2345")
                .build();

        Mockito.when(paymentService.create(paymentMono)).thenReturn(Mono.just(paymentMono));

        webTestClient.post().uri("/payment/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(paymentMono))
                .exchange()
                .expectStatus()
                .isOk().expectBody();

        Mockito.verify(paymentService,times(1)).create(paymentMono);
    }

    @Test
     void updatePaymentTest() {
        Payment paymentMono = Payment.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .paymentAmount(BigDecimal.valueOf(200))
                .description("demo payment")
                .transactionId("234234jnjk2345")
                .build();
        String id = "6767668789fds9";
        Mockito.when(paymentService.update(paymentMono, id))
                .thenReturn(Mono.just(paymentMono));

        webTestClient.put().uri(uriBuilder -> uriBuilder
                        .path("/payment/update/{id}")
                        .build(id))
                .body(Mono.just(paymentMono), Payment.class)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(paymentService,times(1)).update(paymentMono,id);
    }

    @Test
     void findAll() {

        Payment paymentMono = Payment.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .paymentAmount(BigDecimal.valueOf(200))
                .description("demo payment")
                .transactionId("234234jnjk2345")
                .build();

        Mockito.when(paymentService.findAll()).thenReturn(Flux.just(paymentMono));
        webTestClient.get()
                .uri("/payment/findAll")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Payment.class);

        Mockito.verify(paymentService,times(1)).findAll();
    }

    @Test
     void FindById() {

        Payment payment = new Payment();
        payment.setId("12buhvg24uhjknv2");
        payment.setDate(LocalDate.now());
        payment.setPaymentAmount(BigDecimal.valueOf(200));
        payment.setDescription("demo2");
        payment.setTransactionId("2323423424");

        Mockito.when(paymentService.findById("12buhvg24uhjknv2")).thenReturn(Mono.just(payment));

        webTestClient.get()
                .uri("/payment/find/{id}","12buhvg24uhjknv2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.date").isNotEmpty()
                .jsonPath("$.id").isEqualTo("12buhvg24uhjknv2")
                .jsonPath("$.date").isEqualTo("2022-07-20")
                .jsonPath("$.paymentAmount").isEqualTo(BigDecimal.valueOf(200))
                .jsonPath("$.description").isEqualTo("demo2")
                .jsonPath("$.transactionId").isEqualTo("2323423424");


        Mockito.verify(paymentService,times(1)).findById("12buhvg24uhjknv2");
    }

    @Test
     void Delete() {

        Payment paymentMono = Payment.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .paymentAmount(BigDecimal.valueOf(200))
                .description("demo payment")
                .transactionId("234234jnjk2345")
                .build();
        String id = "6767668789fds9";

        Mockito.when(paymentService.delete(id))
                .thenReturn(Mono.just(paymentMono));

        webTestClient.delete().uri("/payment/delete/{id}", id)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(paymentService,times(1)).delete(id);

    }
}
