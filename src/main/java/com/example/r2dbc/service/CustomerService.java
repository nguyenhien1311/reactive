package com.example.r2dbc.service;

import com.example.r2dbc.dto.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<CustomerDTO> getAll();

    Mono<CustomerDTO> getById(Integer id);

    Mono<CustomerDTO> addCustomer(CustomerDTO dto);

    Mono<CustomerDTO> editCustomer(Integer id, CustomerDTO dto);

    Mono<CustomerDTO> patchCustomer(Integer id, CustomerDTO dto);

    Mono<Void> deleteCustomer(Integer id);
}
