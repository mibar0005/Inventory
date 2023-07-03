package com.mibar.Inventory.service;

import com.mibar.Inventory.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Optional<CustomerDTO> getCustomerById(UUID id);

    List<CustomerDTO> getAllCustomers();


    CustomerDTO saveNewCustomer(CustomerDTO customer);

    void updateCustomerById(UUID customerId, CustomerDTO customer);

    void removeCustomerById(UUID customerId);

    void patchCustomerById(UUID customerId, CustomerDTO customer);
}
