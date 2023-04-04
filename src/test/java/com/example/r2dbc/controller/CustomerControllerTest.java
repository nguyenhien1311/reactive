package com.example.r2dbc.controller;

import com.example.r2dbc.constan.Address;
import com.example.r2dbc.domain.Customer;
import com.example.r2dbc.dto.CustomerDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest {
    @Autowired
    WebTestClient client;
    private final static String URI = "/" + Address.CUSTOMER_ROOT;
    private final static String URI_WITH_ID = URI + Address.PATH_WITH_ID;
    private final static Customer blackPanther = Customer.builder().fullName("Black Panther").build();

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
                .expectBody(CustomerDTO.class);
    }

    @Test
    void getByIdNotFound() {
        client.get().uri(URI_WITH_ID, 11)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(3)
    void addCustomer() {
        client.post().uri(URI)
                .body(Mono.just(blackPanther), CustomerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/customers/5");
    }

    @Test
    void addCustomerBadData() {
        blackPanther.setFullName("");
        client.post().uri(URI)
                .body(Mono.just(blackPanther), CustomerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(4)
    void editCustomer() {
        client.put().uri(URI_WITH_ID, 5)
                .body(Mono.just(blackPanther), CustomerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void editCustomerNotFound() {
        blackPanther.setFullName("");
        client.put().uri(URI_WITH_ID, 5)
                .body(Mono.just(blackPanther), CustomerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void patchCustomerNotFound() {
        client.patch().uri(URI_WITH_ID, 10)
                .body(Mono.just(blackPanther), CustomerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(5)
    void deleteCustomer() {
        client.delete().uri(URI_WITH_ID, 5)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deleteCustomerNotFound() {
        client.delete().uri(URI_WITH_ID, 10)
                .exchange()
                .expectStatus().isBadRequest();
    }
}