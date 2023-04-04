package com.example.r2dbc.service;

import com.example.r2dbc.domain.Customer;
import com.example.r2dbc.dto.CustomerDTO;
import com.example.r2dbc.mapper.CustomerMapper;
import com.example.r2dbc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public Flux<CustomerDTO> getAll() {
        return repository.findAll().map(mapper::toDTO);
    }

    @Override
    public Mono<CustomerDTO> getById(Integer id) {
        return getEntityById(id).map(mapper::toDTO);
    }

    @Override
    public Mono<CustomerDTO> addCustomer(CustomerDTO dto) {
        return repository.save(mapper.toDomain(dto)).map(mapper::toDTO);
    }

    @Override
    public Mono<CustomerDTO> editCustomer(Integer id, CustomerDTO dto) {
        return getEntityById(id)
                .map(found -> {
                    found.setFullName(dto.getFullName());
                    return found;
                })
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<CustomerDTO> patchCustomer(Integer id, CustomerDTO dto) {
        return getEntityById(id)
                .map(found -> {
                    if (StringUtils.hasText(dto.getFullName()))
                        found.setFullName(dto.getFullName());
                    return found;
                }).flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteCustomer(Integer id) {
        return getEntityById(id).flatMap(customer -> repository.deleteById(customer.getId()));
    }

    private Mono<Customer> getEntityById(Integer id) {
        return repository.findById(id).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    }
}
