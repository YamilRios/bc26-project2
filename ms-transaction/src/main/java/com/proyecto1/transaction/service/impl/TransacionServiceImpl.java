package com.proyecto1.transaction.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.proyecto1.transaction.client.*;
import com.proyecto1.transaction.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto1.transaction.repository.TransactionRepository;
import com.proyecto1.transaction.service.TransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransacionServiceImpl implements TransactionService {

    private static final Logger log = LogManager.getLogger(TransacionServiceImpl.class);
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CustomerClient customerClient;
    
    @Autowired
    ProductClient product;

    @Autowired
    DepositClient depositClient;

    @Autowired
    WithDrawalClient withDrawalClient;

    @Autowired
    PaymentClient paymentClient;

    @Autowired
    PurchaseClient purchaseClient;

    @Autowired
    SignatoryClient signatoryClient;

    @Override
    public Flux<Transaction> findAll() {
        log.info("Method call FindAll - transaction");
        return transactionRepository.findAll();
    }

    @Override
    public Mono<Transaction> save(Transaction t) {
        log.info("Method call Create - transaction");

        return this.findAllWithDetail()
                .filter( x -> x.getCustomerId().equals(t.getCustomerId())) // Buscamos el customerId de la lista
                .filter( x -> (x.getProduct().getIndProduct() == 2 && x.getCustomer().getTypeCustomer() == 1) && (x.getProduct().getId().equals(t.getProductId())) ) // Buscamos si tiene una cuenta bancaria y es cliente personal
                .hasElements()
                .flatMap( v -> {
                    if (v){
                        return Mono.error(new RuntimeException("The personal client cannot have more than one bank account"));
                    }else{
                        return this.findAllWithDetail()
                                .filter( x -> x.getCustomerId().equals(t.getCustomerId())) // Buscamos el customerId de la lista
                                .filter( x -> (x.getProduct().getIndProduct() == 1 && x.getCustomer().getTypeCustomer() == 1) && (x.getProduct().getId().equals(t.getProductId())) ) // Buscamos si tiene un credito y es cliente personal
                                .hasElements()
                                .flatMap( w -> {
                                   if (w){
                                       return Mono.error(new RuntimeException("The personal client cannot have more than one credit"));
                                   }else{
                                       return product.getProduct(t.getProductId())
                                               .filter( x -> (x.getIndProduct() == 2) )
                                               .filter( x -> (x.getTypeProduct() == 1 || x.getTypeProduct() == 3) )
                                               .hasElement()
                                               .flatMap( zz -> {
                                                   return customerClient.getCustomer(t.getCustomerId())
                                                           .filter( (x -> x.getTypeCustomer() == 2) )
                                                           .hasElement()
                                                           .flatMap( yy -> {
                                                               if ( zz  && yy ){
                                                                   return Mono.error(new RuntimeException("The business client cannot have a savings or fixed-term account"));
                                                               }else{
                                                                   				return transactionRepository.save(t);

                                                               }
                                                           });
                                               });
                                   }
                                });
                    }
                });
    }

    @Override
    public Mono<Transaction> findById(String id) {
        log.info("Method call FindById - transaction");
        return transactionRepository.findById(id);
    }

    @Override
    public Mono<Transaction> update(Transaction t, String id) {
        log.info("Method call Update - transaction");
        return transactionRepository.findById(id)
                .map( x -> {
                    x.setProductId(t.getProductId());
                    x.setAccountNumber(t.getAccountNumber());
                    x.setMovementLimit(t.getMovementLimit());
                    x.setCreditLimit(t.getCreditLimit());
                    x.setAvailableBalance(t.getAvailableBalance());
                    x.setMaintenanceCommission(t.getMaintenanceCommission());
                    x.setCardNumber(t.getCardNumber());
                    x.setRetirementDateFixedTerm(t.getRetirementDateFixedTerm());
                    return x;
                }).flatMap(transactionRepository::save);
    }

    @Override
    public Mono<Transaction> delete(String id) {
        log.info("Method call Delete - transaction");
        return transactionRepository.findById(id).flatMap( x -> transactionRepository.delete(x).then(Mono.just(new Transaction())));
    }

    @Override
    public Mono<Transaction> findByIdWithCustomer(String id) {
        log.info("Method call FindByIdWithCustomer - transaction");
        return transactionRepository.findById(id)
                .flatMap( trans -> customerClient.getCustomer(trans.getCustomerId())
                        .flatMap( customer -> {
                            return product.getProduct(trans.getProductId())
                                    .flatMap( product -> {
                                        return depositClient.getDeposit()
                                                .filter(x -> x.getTransactionId().equals(trans.getId()))
                                                .collectList()
                                                .flatMap((deposit -> {
                                                    return withDrawalClient.getWithDrawal()
                                                           .filter(i -> i.getTransactionId().equals(trans.getId()))
                                                           .collectList()
                                                           .flatMap(( withdrawals -> {
                                                               return paymentClient.getPayment()
                                                                       .filter(z -> z.getTransactionId().equals(trans.getId()))
                                                                       .collectList()
                                                                       .flatMap((payments -> {
                                                               return purchaseClient.getPurchase()
                                                                       .filter(y -> y.getTransactionId().equals(trans.getId()))
                                                                       .collectList()
                                                                       .flatMap(purchases -> {

                                                                           return signatoryClient.getSignatory()
                                                                                   .filter(o -> o.getTransactionId().equals(trans.getId()))
                                                                                   .collectList()
                                                                                   .flatMap(signatories -> {
                                                                                       ValorAllValidator(trans, customer, product, deposit, withdrawals, payments, purchases, signatories);
                                                                                       return Mono.just(trans);
                                                                                   });
                                                                       });

                                                                       } ));
                                                   } ));
                                    }));
                        });
            }));
    }

	@Override
	public Flux<Transaction> findAllWithDetail() {
        return transactionRepository.findAll()
                .flatMap( trans -> customerClient.getCustomer(trans.getCustomerId())
                        .flatMapMany( customer -> {
                            return product.getProduct(trans.getProductId())
                                    .flatMapMany( product -> depositClient.getDeposit()
                                            .filter(x -> x.getTransactionId().equals(trans.getId()))
                                            .collectList()
                                            .flatMapMany((deposit -> {
                                                return withDrawalClient.getWithDrawal()
                                                        .filter(i -> i.getTransactionId().equals(trans.getId()))
                                                        .collectList()
                                                        .flatMapMany(( withdrawals -> {
                                                            return paymentClient.getPayment()
                                                                    .filter(z -> z.getTransactionId().equals(trans.getId()))
                                                                    .collectList()
                                                                    .flatMapMany((payments -> {
                                                                        return purchaseClient.getPurchase()
                                                                                .filter(y -> y.getTransactionId().equals(trans.getId()))
                                                                                .collectList()
                                                                                .flatMapMany(purchases -> {
                                                                                    return signatoryClient.getSignatory()
                                                                                            .filter(o -> o.getTransactionId().equals(trans.getId()))
                                                                                            .collectList()
                                                                                            .flatMapMany(signatories -> {
                                                                                                ValorAllValidator(trans, customer, product, deposit, withdrawals, payments, purchases, signatories);
                                                                                                return Flux.just(trans);
                                                                                            });
                                                                                });

                                                                    } ));
                                                        } ));
                                            })));
                        }));

	}

    private void ValorAllValidator(Transaction trans, Customer customer, Product product, List<Deposit> deposit, List<Withdrawal> withdrawals, List<Payment> payments, List<Purchase> purchases, List<Signatory> signatories) {
        trans.setCustomer(customer);
        trans.setProduct(product);
        trans.setDeposit(deposit.stream().collect(Collectors.toList()));
        trans.setWithdrawal(withdrawals.stream().collect(Collectors.toList()));
        trans.setPayments(payments.stream().collect(Collectors.toList()));
        trans.setPurchases(purchases.stream().collect(Collectors.toList()));
        trans.setSignatories(signatories.stream().collect(Collectors.toList()));
    }
}