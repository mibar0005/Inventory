package com.mibar.Inventory.service;

import com.mibar.Inventory.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer getCustomerById(UUID id);

    List<Customer> getAllCustomers();


    Customer saveNewCustomer(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);

    void removeCustomerById(UUID customerId);

    void patchCustomerById(UUID customerId, Customer customer);
}
