package com.proyecto1.transaction.controller;

import com.proyecto1.transaction.entity.Customer;
import com.proyecto1.transaction.entity.Product;
import com.proyecto1.transaction.entity.Transaction;
import com.proyecto1.transaction.service.TransactionService;
import com.proyecto1.transaction.service.impl.TransacionServiceImpl;
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
import java.util.ArrayList;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TransactionController.class)
@Import(TransacionServiceImpl.class)
public class TransactionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private TransactionService transactionService;

    @Test
     void findAll() {

        Transaction transactionMono = Transaction.builder()
                .id(ObjectId.get().toString())
                .customerId("23424242345fdd")
                .productId("242342j3nji234")
                .depositId("234234u89534hufinf")
                .accountNumber("38748398273492734")
                .movementLimit(1)
                .creditLimit(BigDecimal.valueOf(300))
                .availableBalance(BigDecimal.valueOf(200))
                .maintenanceCommission(BigDecimal.valueOf(2.00))
                .cardNumber("2732648729238423742")
                .retirementDateFixedTerm(LocalDate.now())
                .customer(new Customer())
                .product(new Product())
                .build();

        Mockito.when(transactionService.findAll()).thenReturn(Flux.just(transactionMono));

        webTestClient.get()
                .uri("/transaction/findAll")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Transaction.class);

        Mockito.verify(transactionService, times(1)).findAll();
    }

    @Test
     void FindById() {

        Transaction transaction = new Transaction();
        transaction.setId("12buhvg24uhjknv2");
        transaction.setCustomerId("23423424bjhj");
        transaction.setProductId("23434234knjnk");
        transaction.setDepositId("98778ewwer");
        transaction.setAccountNumber("234u89293u49283424234234");
        transaction.setMovementLimit(1);
        transaction.setCreditLimit(BigDecimal.valueOf(100));
        transaction.setAvailableBalance(BigDecimal.valueOf(20));
        transaction.setMaintenanceCommission(BigDecimal.valueOf(5.00));
        transaction.setCardNumber("2347823482742934");
        transaction.setRetirementDateFixedTerm(LocalDate.now());
        transaction.setCustomer(new Customer());
        transaction.setProduct(new Product());

        Mockito.when(transactionService.findById("12buhvg24uhjknv2")).thenReturn(Mono.just(transaction));

        webTestClient.get()
                .uri("/transaction/find/{id}", "12buhvg24uhjknv2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.id").isEqualTo("12buhvg24uhjknv2")
                .jsonPath("$.customerId").isEqualTo("23423424bjhj")
                .jsonPath("$.productId").isEqualTo("23434234knjnk")
                .jsonPath("$.depositId").isEqualTo("98778ewwer")
                .jsonPath("$.accountNumber").isEqualTo("234u89293u49283424234234")
                .jsonPath("$.movementLimit").isEqualTo(1)
                .jsonPath("$.creditLimit").isEqualTo(BigDecimal.valueOf(100))
                .jsonPath("$.availableBalance").isEqualTo(BigDecimal.valueOf(20))
                .jsonPath("$.maintenanceCommission").isEqualTo(BigDecimal.valueOf(5.00))
                .jsonPath("$.cardNumber").isEqualTo("2347823482742934")
                .jsonPath("$.retirementDateFixedTerm").isEqualTo("2022-07-20")
                .jsonPath("$.customer").isEqualTo(new Customer())
                .jsonPath("$.product").isEqualTo(new Product());

        Mockito.verify(transactionService, times(1)).findById("12buhvg24uhjknv2");
    }

    @Test
     void Delete() {

        Transaction transactionMono = Transaction.builder()
                .id(ObjectId.get().toString())
                .customerId("23424242345fdd")
                .productId("242342j3nji234")
                .depositId("234234u89534hufinf")
                .accountNumber("38748398273492734")
                .movementLimit(1)
                .creditLimit(BigDecimal.valueOf(300))
                .availableBalance(BigDecimal.valueOf(200))
                .maintenanceCommission(BigDecimal.valueOf(2.00))
                .cardNumber("2732648729238423742")
                .retirementDateFixedTerm(LocalDate.now())
                .customer(new Customer())
                .product(new Product())
                .deposit(new ArrayList<>())
                .withdrawal(new ArrayList<>())
                .payments(new ArrayList<>())
                .purchases(new ArrayList<>())
                .signatories(new ArrayList<>())
                .build();
        String id = "6767668789fds9";

        Mockito.when(transactionService.findById(id)).thenReturn(Mono.just(transactionMono));
        Mockito.when(transactionService.delete(id)).thenReturn(Mono.just(transactionMono));

        webTestClient.delete().uri("/transaction/delete/{id}", id)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(transactionService,times(1)).delete(id);

    }

    @Test
     void updateTransactionTest() {
        Transaction transactionMono = Transaction.builder()
                .id(ObjectId.get().toString())
                .customerId("23424242345fdd")
                .productId("242342j3nji234")
                .depositId("234234u89534hufinf")
                .accountNumber("38748398273492734")
                .movementLimit(1)
                .creditLimit(BigDecimal.valueOf(300))
                .availableBalance(BigDecimal.valueOf(200))
                .maintenanceCommission(BigDecimal.valueOf(2.00))
                .cardNumber("2732648729238423742")
                .retirementDateFixedTerm(LocalDate.now())
                .build();
        String id = "6767668789fds9";

        Mockito.when(transactionService.findById(id)).thenReturn(Mono.just(transactionMono));
        Mockito.when(transactionService.update(transactionMono, id)).thenReturn(Mono.just(transactionMono));

        webTestClient.put().uri(uriBuilder -> uriBuilder
                        .path("/transaction/update/{id}")
                        .build(id))
                .body(Mono.just(transactionMono), Transaction.class)
                .exchange()
                .expectStatus().isOk();
        Mockito.verify(transactionService,times(1)).update(transactionMono,id);
    }

    @Test
     void findAllWithDetail() {

        Transaction transactionMono = Transaction.builder()
                .id(ObjectId.get().toString())
                .customerId("23424242345fdd")
                .productId("242342j3nji234")
                .depositId("234234u89534hufinf")
                .accountNumber("38748398273492734")
                .movementLimit(1)
                .creditLimit(BigDecimal.valueOf(300))
                .availableBalance(BigDecimal.valueOf(200))
                .maintenanceCommission(BigDecimal.valueOf(2.00))
                .cardNumber("2732648729238423742")
                .retirementDateFixedTerm(LocalDate.now())
                .customer(new Customer())
                .product(new Product())
                .build();

        Mockito.when(transactionService.findAllWithDetail()).thenReturn(Flux.just(transactionMono));

        webTestClient.get()
                .uri("/transaction/findAllWithDetail")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Transaction.class);

        Mockito.verify(transactionService, times(1)).findAllWithDetail();
    }

    @Test
     void findByIdWithCustomer() {

        Transaction transaction = new Transaction();
        transaction.setId("12buhvg24uhjknv2");
        transaction.setCustomerId("23423424bjhj");
        transaction.setProductId("23434234knjnk");
        transaction.setDepositId("98778ewwer");
        transaction.setAccountNumber("234u89293u49283424234234");
        transaction.setMovementLimit(1);
        transaction.setCreditLimit(BigDecimal.valueOf(100));
        transaction.setAvailableBalance(BigDecimal.valueOf(20));
        transaction.setMaintenanceCommission(BigDecimal.valueOf(5.00));
        transaction.setCardNumber("2347823482742934");
        transaction.setRetirementDateFixedTerm(LocalDate.now());
        transaction.setCustomer(new Customer());
        transaction.setProduct(new Product());


        Mockito.when(transactionService.findByIdWithCustomer("12buhvg24uhjknv2")).thenReturn(Mono.just(transaction));

        webTestClient.get()
                .uri("/transaction/findByIdWithCustomer/{id}", "12buhvg24uhjknv2")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.id").isEqualTo("12buhvg24uhjknv2")
                .jsonPath("$.customerId").isEqualTo("23423424bjhj")
                .jsonPath("$.productId").isEqualTo("23434234knjnk")
                .jsonPath("$.depositId").isEqualTo("98778ewwer")
                .jsonPath("$.accountNumber").isEqualTo("234u89293u49283424234234")
                .jsonPath("$.movementLimit").isEqualTo(1)
                .jsonPath("$.creditLimit").isEqualTo(BigDecimal.valueOf(100))
                .jsonPath("$.availableBalance").isEqualTo(BigDecimal.valueOf(20))
                .jsonPath("$.maintenanceCommission").isEqualTo(BigDecimal.valueOf(5.00))
                .jsonPath("$.cardNumber").isEqualTo("2347823482742934")
                .jsonPath("$.retirementDateFixedTerm").isEqualTo("2022-07-20")
                .jsonPath("$.customer").isEqualTo(new Customer())
                .jsonPath("$.product").isEqualTo(new Product());

        Mockito.verify(transactionService, times(1)).findByIdWithCustomer("12buhvg24uhjknv2");
    }

    @Test
     void createWithDrawalTest() {
        Transaction transactionMono = Transaction.builder()
                .id(ObjectId.get().toString())
                .customerId("23424242345fdd")
                .productId("242342j3nji234")
                .depositId("234234u89534hufinf")
                .accountNumber("38748398273492734")
                .movementLimit(1)
                .creditLimit(BigDecimal.valueOf(300))
                .availableBalance(BigDecimal.valueOf(200))
                .maintenanceCommission(BigDecimal.valueOf(2.00))
                .cardNumber("2732648729238423742")
                .retirementDateFixedTerm(LocalDate.now())
                .build();
        Mockito.when(transactionService.findAllWithDetail()).thenReturn(Flux.just(transactionMono));
        Mockito.when(transactionService.save(transactionMono)).thenReturn(Mono.just(transactionMono));
        webTestClient.post().uri("/transaction/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(transactionMono))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(transactionService,times(1)).save(transactionMono);
    }
}
