package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {
    private final Map<Order, Payment> orderPayments = new HashMap<Order, Payment>();

    public Payment save(Order order, Payment payment) {
        orderPayments.put(order, payment);
        return payment;
    }

    public Payment findByPaymentId(String orderId) {
        for (Order order : orderPayments.keySet()) {
            if (order.getId().equals(orderId)) {
                return orderPayments.get(order);
            }
        }
        return null;
    }

    public List<Payment> findAll() {
        return List.copyOf(orderPayments.values());
    }
}
