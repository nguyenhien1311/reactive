package com.example.r2dbc.controller;

import com.example.r2dbc.constan.Address;
import com.example.r2dbc.dto.BeerDTO;
import com.example.r2dbc.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(Address.BEER_ROOT)
@RequiredArgsConstructor
public class BeerController {
    private final BeerService service;
    @Value("${me.address}")
    private String HOST;

    @GetMapping
    Flux<BeerDTO> getAll() {
        return service.getAll();
    }

    @GetMapping(Address.PATH_WITH_ID)
    Mono<BeerDTO> getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    Mono<ResponseEntity<Void>> addBeer(@Validated @RequestBody BeerDTO dto) {
        return service.addBeer(dto).map(savedBeer -> ResponseEntity.created(UriComponentsBuilder
                .fromHttpUrl(HOST +
                        Address.BEER_ROOT + "/" +
                        savedBeer.getId())
                .build().toUri()
        ).build());
    }

    @PutMapping(Address.PATH_WITH_ID)
    Mono<ResponseEntity<Void>> editBeer(@PathVariable Integer id,
                                        @Validated @RequestBody BeerDTO dto) {
        return service.editBeer(id, dto).map(savedBeer -> ResponseEntity.noContent().build()
        );
    }

    @PatchMapping(Address.PATH_WITH_ID)
    Mono<ResponseEntity<Void>> patchBeer(@PathVariable Integer id,
                                         @RequestBody BeerDTO dto) {
        return service.patchBeer(id, dto).map(savedBeer -> ResponseEntity.noContent().build());
    }

    @DeleteMapping(Address.PATH_WITH_ID)
    Mono<ResponseEntity<Void>> deleteBeer(@PathVariable Integer id) {
        return service.deleteBeer(id).thenReturn(ResponseEntity.noContent().build());
    }
}
