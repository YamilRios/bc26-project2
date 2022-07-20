package com.proyecto1.deposit.service;

import com.proyecto1.deposit.dto.DepositDTO;
import com.proyecto1.deposit.entity.Deposit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DepositService {

    Flux<Deposit> findAll();

    Mono<Deposit> create(DepositDTO c);

    Mono<Deposit> findById(String id);

    Mono<Deposit> update(DepositDTO c, String id);

    Mono<Deposit> delete(String id);
}
