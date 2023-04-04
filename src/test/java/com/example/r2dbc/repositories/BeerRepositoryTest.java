package com.example.r2dbc.repositories;

import com.example.r2dbc.config.DatabaseConfig;
import com.example.r2dbc.domain.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

@DataR2dbcTest
@Import(DatabaseConfig.class)
public class BeerRepositoryTest {
    @Autowired
    BeerRepository repository;

    @Test
    void testGetAll() {
        repository.findAll().subscribe(System.out::println);
    }

    @Test
    void testInsert() {
        repository.save(getTestBeer()).subscribe(beer -> {
            System.out.println(beer.toString());
        });
    }

    public static Beer getTestBeer() {
        return Beer.builder()
                .beerName("Elf tear")
                .beerStyle("Fantasy")
                .upc("Mystic")
                .price(BigDecimal.TEN)
                .quantityOnHand(99).build();
    }
}