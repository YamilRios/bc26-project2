package com.proyecto1.transaction.service.impl;

import com.proyecto1.transaction.client.*;
import com.proyecto1.transaction.entity.*;
import com.proyecto1.transaction.repository.TransactionRepository;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private PaymentClient paymentClient;

    @Mock
    private DepositClient depositClient;

    @Mock
    private ProductClient productClient;

    @Mock
    private CustomerClient customerClient;

    @Mock
    private PurchaseClient purchaseClient;

    @Mock
    private SignatoryClient signatoryClient;

    @Mock
    private WithDrawalClient withDrawalClient;

    @InjectMocks
    private TransacionServiceImpl transacionServiceImpl;


    @Test
     void findAllWithDetail(){
        Customer customer = Customer.builder()
                .id("2854445425")
                .name("yasmin")
                .lastName("oyarce")
                .docNumber("2342342342")
                .typeCustomer(1)
                .descTypeCustomer("personal").build();

        Product product = Product.builder()
                .id("83457346534534")
                .indProduct(1)
                .descIndProduct("cuenta bancaria")
                .typeProduct(1)
                .descTypeProduct("cuenta de ahorro")
                .build();

        Deposit deposit = Deposit.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .depositAmount(BigDecimal.valueOf(200))
                .description("demo 1")
                .transactionId("84374234y743123")
                .build();

        Withdrawal withdrawal = Withdrawal.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .withdrawalAmount(BigDecimal.valueOf(2000))
                .description("demo withdrawal")
                .transactionId("84374234y743123")
                .build();

        Signatory signatory = Signatory.builder()
                .id(ObjectId.get().toString())
                .name("yasmin")
                .lastName("zegarra")
                .docNumber("8797897797")
                .transactionId("84374234y743123")
                .build();

        Payment payment = Payment.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .paymentAmount(BigDecimal.valueOf(200))
                .description("demo payment")
                .transactionId("234234jnjk2345")
                .build();

        Purchase purchase = Purchase.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .purchaseAmount(BigDecimal.valueOf(100))
                .description("demo purchase")
                .transactionId("34242423234")
                .build();

        Transaction transactionMono = Transaction.builder()
                .id("84374234y743123")
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
                .customer(customer)
                .product(product)
                .build();

        Mockito.when(transactionRepository.findAll()).thenReturn(Flux.just(transactionMono));
        Mockito.when(customerClient.getCustomer(customer.getId())).thenReturn(Mono.just(customer));
        Mockito.when(productClient.getProduct(product.getId())).thenReturn(Mono.just(product));
        Mockito.when(depositClient.getDeposit()).thenReturn(Flux.just(deposit));
        Mockito.when(withDrawalClient.getWithDrawal()).thenReturn(Flux.just(withdrawal));
        Mockito.when(paymentClient.getPayment()).thenReturn(Flux.just(payment));
        Mockito.when(purchaseClient.getPurchase()).thenReturn(Flux.just(purchase));
        Mockito.when(signatoryClient.getSignatory()).thenReturn(Flux.just(signatory));


        assertDoesNotThrow(() -> transacionServiceImpl.findAllWithDetail()
                .subscribe(response -> {
                    assertEquals(transactionMono.getAccountNumber(), response.getAccountNumber());
                }));
    }

    @Test
     void updateDepositTest() {

        Customer customer = Customer.builder()
                .id("2854445425")
                .name("yasmin")
                .lastName("oyarce")
                .docNumber("2342342342")
                .typeCustomer(1)
                .descTypeCustomer("personal").build();

        Product product = Product.builder()
                .id("83457346534534")
                .indProduct(1)
                .descIndProduct("cuenta bancaria")
                .typeProduct(1)
                .descTypeProduct("cuenta de ahorro")
                .build();
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
                .customer(customer)
                .product(product)
                .deposit(new ArrayList<>())
                .withdrawal(new ArrayList<>())
                .payments(new ArrayList<>())
                .purchases(new ArrayList<>())
                .signatories(new ArrayList<>())
                .build();
        String id = "6767668789fds9";

        Mockito.when(transactionRepository.findById(id)).thenReturn(Mono.just(transactionMono));
        Mockito.when(transactionRepository.save(transactionMono)).thenReturn(Mono.just(transactionMono));

        assertDoesNotThrow(() -> transacionServiceImpl.update(transactionMono,id)
                .subscribe(response -> {
                    assertEquals(transactionMono.getAccountNumber(), response.getAccountNumber());
                }));
    }

    @Test
     void findAll() {

        Customer customer = Customer.builder()
                .id("2854445425")
                .name("yasmin")
                .lastName("oyarce")
                .docNumber("2342342342")
                .typeCustomer(1)
                .descTypeCustomer("personal").build();

        Product product = Product.builder()
                .id("83457346534534")
                .indProduct(1)
                .descIndProduct("cuenta bancaria")
                .typeProduct(1)
                .descTypeProduct("cuenta de ahorro")
                .build();

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
                .customer(customer)
                .product(product)
                .deposit(new ArrayList<>())
                .withdrawal(new ArrayList<>())
                .payments(new ArrayList<>())
                .purchases(new ArrayList<>())
                .signatories(new ArrayList<>())
                .build();

        Mockito.when(transactionRepository.findAll()).thenReturn(Flux.just(transactionMono));

        assertDoesNotThrow(() -> transacionServiceImpl.findAll()
                .subscribe(response -> {
                    assertEquals(transactionMono.getAccountNumber(), response.getAccountNumber());
                }));
    }

    @Test
     void FindById() {

        Customer customer = Customer.builder()
                .id("2854445425")
                .name("yasmin")
                .lastName("oyarce")
                .docNumber("2342342342")
                .typeCustomer(1)
                .descTypeCustomer("personal").build();

        Product product = Product.builder()
                .id("83457346534534")
                .indProduct(1)
                .descIndProduct("cuenta bancaria")
                .typeProduct(1)
                .descTypeProduct("cuenta de ahorro")
                .build();
        Transaction transactionMono = Transaction.builder()
                .id("2346723847262")
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
                .customer(customer)
                .product(product)
                .deposit(new ArrayList<>())
                .withdrawal(new ArrayList<>())
                .payments(new ArrayList<>())
                .purchases(new ArrayList<>())
                .signatories(new ArrayList<>())
                .build();

        Mockito.when(transactionRepository.findById("2346723847262")).thenReturn(Mono.just(transactionMono));

        assertDoesNotThrow(() -> transacionServiceImpl.findById(transactionMono.getId())
                .subscribe(response -> {
                    assertEquals(transactionMono.getAccountNumber(), response.getAccountNumber());
                }));
    }

    @Test
     void Delete() {
        Customer customer = Customer.builder()
                .id("2854445425")
                .name("yasmin")
                .lastName("oyarce")
                .docNumber("2342342342")
                .typeCustomer(1)
                .descTypeCustomer("personal").build();

        Product product = Product.builder()
                .id("83457346534534")
                .indProduct(1)
                .descIndProduct("cuenta bancaria")
                .typeProduct(1)
                .descTypeProduct("cuenta de ahorro")
                .build();
        Transaction transactionMono = Transaction.builder()
                .id("6767668789fds9")
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
                .customer(customer)
                .product(product)
                .build();
        String id = "6767668789fds9";

        Mockito.when(transactionRepository.findById("6767668789fds9")).thenReturn(Mono.just(transactionMono));
        Mockito.when(transactionRepository.delete(transactionMono)).thenReturn(Mono.empty());

        assertDoesNotThrow(() -> transacionServiceImpl.delete(id)
                .subscribe(response -> {
                    assertEquals(new Transaction(), response);
                }));

    }

    @Test
     void findByIdWithCustomer(){
        Customer customer = Customer.builder()
                .id("2854445425")
                .name("yasmin")
                .lastName("oyarce")
                .docNumber("2342342342")
                .typeCustomer(1)
                .descTypeCustomer("personal").build();

        Product product = Product.builder()
                .id("83457346534534")
                .indProduct(1)
                .descIndProduct("cuenta bancaria")
                .typeProduct(1)
                .descTypeProduct("cuenta de ahorro")
                .build();

        Deposit deposit = Deposit.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .depositAmount(BigDecimal.valueOf(200))
                .description("demo 1")
                .transactionId("84374234y743123")
                .build();

        Withdrawal withdrawal = Withdrawal.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .withdrawalAmount(BigDecimal.valueOf(2000))
                .description("demo withdrawal")
                .transactionId("84374234y743123")
                .build();

        Signatory signatory = Signatory.builder()
                .id(ObjectId.get().toString())
                .name("yasmin")
                .lastName("zegarra")
                .docNumber("8797897797")
                .transactionId("84374234y743123")
                .build();

        Payment payment = Payment.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .paymentAmount(BigDecimal.valueOf(200))
                .description("demo payment")
                .transactionId("234234jnjk2345")
                .build();

        Purchase purchase = Purchase.builder()
                .id(ObjectId.get().toString())
                .date(LocalDate.now())
                .purchaseAmount(BigDecimal.valueOf(100))
                .description("demo purchase")
                .transactionId("34242423234")
                .build();

        Transaction transactionMono = Transaction.builder()
                .id("84374234y743123")
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
                .customer(customer)
                .product(product)
                .build();

        Mockito.when(transactionRepository.findById(transactionMono.getId())).thenReturn(Mono.just(transactionMono));
        Mockito.when(customerClient.getCustomer(customer.getId())).thenReturn(Mono.just(customer));
        Mockito.when(productClient.getProduct(product.getId())).thenReturn(Mono.just(product));
        Mockito.when(depositClient.getDeposit()).thenReturn(Flux.just(deposit));
        Mockito.when(withDrawalClient.getWithDrawal()).thenReturn(Flux.just(withdrawal));
        Mockito.when(paymentClient.getPayment()).thenReturn(Flux.just(payment));
        Mockito.when(purchaseClient.getPurchase()).thenReturn(Flux.just(purchase));
        Mockito.when(signatoryClient.getSignatory()).thenReturn(Flux.just(signatory));


        assertDoesNotThrow(() -> transacionServiceImpl.findByIdWithCustomer(transactionMono.getId())
                .subscribe(response -> {
                    assertEquals(transactionMono.getAccountNumber(), response.getAccountNumber());
                }));
    }

    @Test
    void saveTransaction(){

        Product product = Product.builder()
                .id("83457346534534")
                .indProduct(2)
                .descIndProduct("cuenta bancaria")
                .typeProduct(1)
                .descTypeProduct("cuenta de ahorro")
                .build();

        Customer customer = Customer.builder()
                .id("2854445425")
                .name("yasmin")
                .lastName("oyarce")
                .docNumber("2342342342")
                .typeCustomer(2)
                .descTypeCustomer("empresarial").build();

        Transaction transactionMono = Transaction.builder()
                .id("84374234y743123")
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
                .customer(customer)
                .product(product)
                .build();

        Mockito.when(productClient.getProduct(product.getId())).thenReturn(Mono.just(product));
        Mockito.when(customerClient.getCustomer(customer.getId())).thenReturn(Mono.just(customer));
        Mockito.when(transactionRepository.findAll()).thenReturn(Flux.just(transactionMono));


        assertDoesNotThrow(() -> transacionServiceImpl.save(transactionMono)
                .subscribe(response -> {
                    assertEquals(transactionMono.getAccountNumber(), response.getAccountNumber());
                }));
    }
}
