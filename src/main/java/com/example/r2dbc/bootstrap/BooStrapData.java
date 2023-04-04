package com.example.r2dbc.bootstrap;

import com.example.r2dbc.domain.Beer;
import com.example.r2dbc.domain.Customer;
import com.example.r2dbc.repositories.BeerRepository;
import com.example.r2dbc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BooStrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        getBeers();
        getCustomers();
    }

    private void getBeers() {
        beerRepository.count().subscribe(aLong -> {
            if (aLong < 4) {
                Beer volcano = Beer.builder()
                        .beerName("Volcano Tear")
                        .quantityOnHand(1)
                        .upc("Mystic")
                        .beerStyle("Fantasy")
                        .price(BigDecimal.TEN)
                        .build();
                Beer moon = Beer.builder()
                        .beerName("Moon dust")
                        .beerStyle("Fantasy")
                        .upc("Mystic")
                        .price(BigDecimal.TEN)
                        .quantityOnHand(20).build();
                Beer apple = Beer.builder()
                        .beerName("Golden Apple")
                        .quantityOnHand(100)
                        .upc("Rare")
                        .beerStyle("Fantasy")
                        .price(BigDecimal.TEN)
                        .build();
                Beer scale = Beer.builder()
                        .beerName("Drake Scale")
                        .beerStyle("Fantasy")
                        .upc("Mystic")
                        .price(BigDecimal.TEN)
                        .quantityOnHand(99).build();
                beerRepository.saveAll(List.of(volcano, moon, apple, scale)).subscribe();
            }
        });
    }

    private void getCustomers() {
        customerRepository.count().subscribe(aLong -> {
            if (aLong < 4) {
                Customer batman = Customer.builder().fullName("Batman").build();
                Customer ironman = Customer.builder().fullName("Ironman").build();
                Customer sup = Customer.builder().fullName("Superman").build();
                Customer spider = Customer.builder().fullName("Spider man").build();
                customerRepository.saveAll(List.of(batman, ironman, sup, spider)).subscribe();
            }
        });
    }
}
