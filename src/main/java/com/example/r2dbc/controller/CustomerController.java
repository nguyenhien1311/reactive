package com.example.r2dbc.controller;

import com.example.r2dbc.constan.Address;
import com.example.r2dbc.dto.CustomerDTO;
import com.example.r2dbc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(Address.CUSTOMER_ROOT)
public class CustomerController {
    private final CustomerService service;
    @Value("${me.address}")
    private String HOST;

    @GetMapping
    Flux<CustomerDTO> getAll() {
        return service.getAll();
    }

    @GetMapping(Address.PATH_WITH_ID)
    Mono<CustomerDTO> getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    Mono<ResponseEntity<Void>> addCustomer(@Validated @RequestBody CustomerDTO dto) {
        return service.addCustomer(dto).map(savedCustomer -> ResponseEntity.created(UriComponentsBuilder
                        .fromHttpUrl(HOST + Address.CUSTOMER_ROOT + "/" + savedCustomer.getId())
                        .build().toUri())
                .build());
    }

    @PutMapping(Address.PATH_WITH_ID)
    Mono<ResponseEntity<Void>> editCustomer(@PathVariable Integer id,
                                            @Validated @RequestBody CustomerDTO dto) {
        return service.editCustomer(id, dto).map(savedCustomer -> ResponseEntity.noContent().build());
    }

    @PatchMapping(Address.PATH_WITH_ID)
    Mono<ResponseEntity<Void>> patchCustomer(@PathVariable Integer id,
                                             @RequestBody CustomerDTO dto) {
        return service.patchCustomer(id, dto).map(savedCustomer -> ResponseEntity.noContent().build());
    }

    @DeleteMapping(Address.PATH_WITH_ID)
    Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable Integer id) {
        return service.deleteCustomer(id).thenReturn(ResponseEntity.noContent().build());
    }
}
