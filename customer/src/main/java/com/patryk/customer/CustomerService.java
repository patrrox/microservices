package com.patryk.customer;


import org.springframework.stereotype.Service;

@Service
public record CustomerService() {

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();

        // todo: check if email valid
        // todo: check if email not taken
        // todo: store customer in db
    }
}
