package com.mibar.Inventory.bootstrap;

import com.mibar.Inventory.entities.Beer;
import com.mibar.Inventory.entities.Customer;
import com.mibar.Inventory.model.BeerStyle;
import com.mibar.Inventory.repositories.BeerRepository;
import com.mibar.Inventory.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadCustomerData() {

        if (beerRepository.count() == 0) {

            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("123456")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Riverside Rachets")
                    .beerStyle(BeerStyle.SOUR)
                    .upc("9876543")
                    .price(new BigDecimal("15.99"))
                    .quantityOnHand(58)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("The Can Can")
                    .beerStyle(BeerStyle.DOUBLE_IPA)
                    .upc("1346798264")
                    .price(new BigDecimal("21.99"))
                    .quantityOnHand(13)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);

        }
    }

    private void loadBeerData() {

        if (customerRepository.count() == 0) {

            Customer customer1 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Cranky Karen")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Wendy Worrisome")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .id(UUID.randomUUID())
                    .name("Hazel WazerName")
                    .version(1)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }
}
