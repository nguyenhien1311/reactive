package com.example.r2dbc.controller;

import com.example.r2dbc.constan.Address;
import com.example.r2dbc.domain.Beer;
import com.example.r2dbc.dto.BeerDTO;
import com.example.r2dbc.repositories.BeerRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

    @Autowired
    WebTestClient client;

    private final String URI = "/" + Address.BEER_ROOT;
    private final String URI_WITH_ID = URI + Address.PATH_WITH_ID;

    @Test
    @Order(1)
    void getAll() {
        client.get().uri(URI)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(4);
    }

    @Test
    @Order(2)
    void getById() {
        client.get().uri(URI_WITH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(BeerDTO.class);
    }

    @Test
    void getByIdNotFound() {
        client.get().uri(URI_WITH_ID, 10)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(3)
    void addBeer() {
        client.post().uri(URI)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/beers/5");
    }

    @Test
    void addBeerBadData() {
        Beer testBeer = BeerRepositoryTest.getTestBeer();
        testBeer.setBeerName("");
        client.post().uri(URI)
                .body(Mono.just(testBeer), BeerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(4)
    void editBeer() {
        client.put().uri(URI_WITH_ID, 1)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void editBeerBadRequest() {
        Beer testBeer = BeerRepositoryTest.getTestBeer();
        testBeer.setBeerName("");
        client.put().uri(URI_WITH_ID, 1)
                .body(Mono.just(testBeer), BeerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void patchBeerNotFound() {
        client.patch().uri(URI_WITH_ID, 10)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(5)
    void deleteBeer() {
        client.delete().uri(URI_WITH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deleteBeerNotFound() {
        client.delete().uri(URI_WITH_ID, 10)
                .exchange()
                .expectStatus().isBadRequest();
    }
}