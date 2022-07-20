package com.proyecto1.purchase.service.impl;

import com.proyecto1.purchase.client.TransactionClient;
import com.proyecto1.purchase.entity.Product;
import com.proyecto1.purchase.entity.Purchase;
import com.proyecto1.purchase.entity.Transaction;
import com.proyecto1.purchase.repository.PurchaseRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class PurchaseServiceImplTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private TransactionClient transactionClient;

    @InjectMocks
    private PurchaseServiceImpl purchaseServiceImpl;

    @Test
     void createDepositTest() {
        Purchase purchaseMono = Purchase.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .purchaseAmount(BigDecimal.valueOf(100))
                .description("demo purchase")
                .transactionId("34242423234")
                .build();

        Product product = new Product();
        product.setId("83457346534534");
        product.setIndProduct(1);
        product.setDescIndProduct("cuenta empresarial");
        product.setTypeProduct(6);
        product.setDescTypeProduct("cuenta de ahorro");

        Transaction transaction = new Transaction();
        transaction.setId("34242423234");
        transaction.setCustomerId("7890729347");
        transaction.setProductId("8974593463");
        transaction.setAccountNumber("384724239423");
        transaction.setMovementLimit(1);
        transaction.setCreditLimit(BigDecimal.valueOf(100));
        transaction.setAvailableBalance(BigDecimal.valueOf(30));
        transaction.setMaintenanceCommission(BigDecimal.valueOf(30));
        transaction.setCardNumber("764249236473249234234");
        transaction.setRetirementDateFixedTerm(LocalDate.now());
        transaction.setProduct(product);


        Mockito.when(transactionClient.getTransactionWithDetails(transaction.getId())).thenReturn(Mono.just(transaction));
        Mockito.when(purchaseRepository.save(Mockito.any())).thenReturn(Mono.just(purchaseMono));

        assertDoesNotThrow(() -> purchaseServiceImpl.create(purchaseMono)
                .subscribe(response -> {
                    assertEquals(purchaseMono.getPurchaseAmount(), response.getPurchaseAmount());
                }));
    }

    @Test
     void updateDepositTest() {
        Purchase purchaseMono = Purchase.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .purchaseAmount(BigDecimal.valueOf(100))
                .description("demo purchase")
                .transactionId("342873574h")
                .build();
        String id = "6767668789fds9";

        Mockito.when(purchaseRepository.findById(id)).thenReturn(Mono.just(purchaseMono));
        Mockito.when(purchaseRepository.save(purchaseMono)).thenReturn(Mono.just(purchaseMono));

        assertDoesNotThrow(() -> purchaseServiceImpl.update(purchaseMono,id)
                .subscribe(response -> {
                    assertEquals(purchaseMono.getPurchaseAmount(), response.getPurchaseAmount());
                }));
    }

    @Test
     void findAll() {
        Purchase purchaseMono = Purchase.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .purchaseAmount(BigDecimal.valueOf(100))
                .description("demo purchase")
                .transactionId("342873574h")
                .build();

        Mockito.when(purchaseRepository.findAll()).thenReturn(Flux.just(purchaseMono));

        assertDoesNotThrow(() -> purchaseServiceImpl.findAll()
                .subscribe(response -> {
                    assertEquals(purchaseMono.getPurchaseAmount(), response.getPurchaseAmount());
                }));
    }

    @Test
     void FindById() {

        Purchase purchaseMono = Purchase.builder()
                .id("2346723847262")
                .date(LocalDate.now())
                .purchaseAmount(BigDecimal.valueOf(100))
                .description("demo purchase")
                .transactionId("342873574h")
                .build();

        Mockito.when(purchaseRepository.findById("2346723847262")).thenReturn(Mono.just(purchaseMono));

        assertDoesNotThrow(() -> purchaseServiceImpl.findById(purchaseMono.getId())
                .subscribe(response -> {
                    assertEquals(purchaseMono.getPurchaseAmount(), response.getPurchaseAmount());
                }));
    }

    @Test
     void Delete() {

        Purchase purchaseMono = Purchase.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .purchaseAmount(BigDecimal.valueOf(100))
                .description("demo purchase")
                .transactionId("342873574h")
                .build();
        String id = "6767668789fds9";

        Mockito.when(purchaseRepository.findById("6767668789fds9")).thenReturn(Mono.just(purchaseMono));
        Mockito.when(purchaseRepository.delete(purchaseMono)).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> purchaseServiceImpl.delete(id)
                .subscribe(response -> {
                    assertEquals(new Purchase(), response);
                }));

    }
}
