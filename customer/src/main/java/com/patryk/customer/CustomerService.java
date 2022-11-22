package com.patryk.customer;


import com.patryk.amqp.RabbitMQMessageProducer;
import com.patryk.clients.fraud.FraudCheckResponse;
import com.patryk.clients.fraud.FraudClient;
import com.patryk.clients.notification.NotificationClient;
import com.patryk.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check if email valid
        // todo: check if email not taken
        customerRepository.saveAndFlush(customer);
        //todo: check if fraudster

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome ...",
                        customer.getFirstName())
        );

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }
}
