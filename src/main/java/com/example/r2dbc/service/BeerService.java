package com.example.r2dbc.service;

import com.example.r2dbc.dto.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {
    Flux<BeerDTO> getAll();

    Mono<BeerDTO> getById(Integer id);

    Mono<BeerDTO> addBeer(BeerDTO dto);

    Mono<BeerDTO> editBeer(Integer id, BeerDTO dto);

    Mono<BeerDTO> patchBeer(Integer id, BeerDTO dto);

    Mono<Void> deleteBeer(Integer id);
}
