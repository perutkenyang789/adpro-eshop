package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    private OrderServiceImpl orderServiceImpl;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String paymentId = order.getId() != null ? order.getId() : UUID.randomUUID().toString();

        PaymentMethod paymentMethod;
        try {
            paymentMethod = PaymentMethod.valueOf(method.toUpperCase()); // Convert string to enum
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid payment method: " + method);
        }

        Payment payment = new Payment(paymentId, paymentMethod, paymentData);

        setStatus(payment, payment.getStatus().getValue());

        paymentRepository.save(order, payment);

        return payment;
    }


    @Override
    public Payment setStatus(Payment payment, String status) {
        Order order = paymentRepository.findOrderByPaymentId(payment.getId());

        if (status.equals("SUCCESS")) {
            orderServiceImpl.updateStatus(order.getId(), "SUCCESS");
        } else if (status.equals("REJECTED")) {
            orderServiceImpl.updateStatus(order.getId(), "FAILED");
        } else {
            throw new IllegalArgumentException();
        }

        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findByPaymentId(paymentId);
    }

    @Override
    public List<Payment> getAllPayment() {
        return paymentRepository.findAll();
    }
}