package com.mibar.Inventory.entities;

import com.mibar.Inventory.model.BeerStyle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

//Created a new package for the Entities.
//This will be a 1 to 1 copy of the BeerDTO Object
//We can also bring in our Lombok annotations
//Using @Data with JPA entities is not recommended. It can cause performance and memory consumption issues
//@Data
//Bring in the getters and setters manually
@Setter
@Getter
@Builder
//To annotate an object as an Entity we need to use the @Entity bean
//The Entities are the persistence object stores as a record in the database
//In our case we need to add the @Id and @Version annotations to our fields
@Entity
//Bring in your constructors (All and noArgs)
@AllArgsConstructor
@NoArgsConstructor
public class Beer {

    @Id
    //We want to set this up for Hibernate to manage it.
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    //We can use the Column annotation in order to set up our Column properties
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;
    @Version
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
