package com.example.r2dbc.service;

import com.example.r2dbc.domain.Beer;
import com.example.r2dbc.dto.BeerDTO;
import com.example.r2dbc.mapper.BeerMapper;
import com.example.r2dbc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository repository;
    private final BeerMapper mapper;

    @Override
    public Flux<BeerDTO> getAll() {
        return repository.findAll().map(mapper::toDto);
    }

    @Override
    public Mono<BeerDTO> getById(Integer id) {
        return getEntityById(id)
                .map(mapper::toDto);
    }

    @Override
    public Mono<BeerDTO> addBeer(BeerDTO dto) {
        return repository.save(mapper.toDomain(dto))
                .map(mapper::toDto);
    }

    @Override
    public Mono<BeerDTO> editBeer(Integer id, BeerDTO dto) {
        return getEntityById(id).map(found -> {
                    found.setBeerName(dto.getBeerName());
                    found.setBeerStyle(dto.getBeerStyle());
                    found.setPrice(dto.getPrice());
                    found.setQuantityOnHand(dto.getQuantityOnHand());
                    found.setUpc(dto.getUpc());
                    return found;
                }).flatMap(repository::save)
                .map(mapper::toDto);
    }

    @Override
    public Mono<BeerDTO> patchBeer(Integer id, BeerDTO dto) {
        return getEntityById(id).map(found -> {
                    if (StringUtils.hasText(dto.getBeerName())) {
                        found.setBeerName(dto.getBeerName());
                    }
                    if (StringUtils.hasText(dto.getBeerStyle())) {
                        found.setBeerStyle(dto.getBeerStyle());
                    }
                    if (StringUtils.hasText(dto.getUpc())) {
                        found.setUpc(dto.getUpc());
                    }
                    if (dto.getPrice() != null) {
                        found.setPrice(dto.getPrice());
                    }
                    if (dto.getQuantityOnHand() != null) {
                        found.setQuantityOnHand(dto.getQuantityOnHand());
                    }
                    return found;
                }).flatMap(repository::save)
                .map(mapper::toDto);

    }

    @Override
    public Mono<Void> deleteBeer(Integer id) {
        return getEntityById(id).flatMap(beer -> repository.deleteById(id));
    }

    private Mono<Beer> getEntityById(Integer id) {
        return repository.findById(id).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    }
}
