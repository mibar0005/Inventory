package com.mibar.Inventory.repositories;

import com.mibar.Inventory.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

//Create a new package called repositories
//Create a new interface for BeerRepository that extends the JpaRepository
//and we want to use the Beer type and the UUID property
public interface BeerRepository extends JpaRepository<Beer, UUID> {


}
