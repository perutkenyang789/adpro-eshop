package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    Map<Order, Payment> orderPayments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        orderPayments = new HashMap<Order, Payment>();

        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6fa3bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);

        List<Product> products = List.of(product);
        Order order = new Order("13652556-012a-4c07-b546-54eb139d679b", products, 1708560000L, "Safira Sudrajat");
        Payment payment = new Payment("52efd18f-329e-4d7b-8847-fdb0f55b08b7", PaymentMethod.VOUCHER, Map.of("voucherCode", "ESHOP20230101XYZ"));
        orderPayments.put(order, payment);

        paymentRepository.save(order, payment);
    }

    @Test
    void testSaveCreate() {
        Order order = new Order("f79e15bb-4b15-42f4-aebc-c3af385fb078", List.of(new Product()), 1708570000L, "Safira Sudrajat");
        Payment payment = new Payment("106e7f7b-d80c-4c10-b6d7-0fe909a5c4a8", PaymentMethod.VOUCHER, Map.of("voucherCode", "ESHOP20230101XYZ"));
        Payment result = paymentRepository.save(order, payment);

        Payment findResult = paymentRepository.findByPaymentId(order.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testSFindAll() {
        List<Payment> result = paymentRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void testFindByPaymentId() {
        Payment result = paymentRepository.findByPaymentId("13652556-012a-4c07-b546-54eb139d679b");
        assertNotNull(result);
    }

    @Test
    void testFindByPaymentIdNotFound() {
        Payment result = paymentRepository.findByPaymentId("13652556-012a-4c07-b546-54eb139d679c");
        assertNull(result);
    }

    @Test
    void testFindByPaymentIdEmpty() {
        Payment result = paymentRepository.findByPaymentId("13652556-012a-4c07-b546-54eb139d679c");
        assertNull(result);
    }

    @Test
    void testFindByPaymentIdNull() {
        Payment result = paymentRepository.findByPaymentId(null);
        assertNull(result);
    }

    @Test
    void testFindByPaymentIdEmptyString() {
        Payment result = paymentRepository.findByPaymentId("");
        assertNull(result);
    }

    @Test
    void testFindOrderByPaymentId() {
        Order result = paymentRepository.findOrderByPaymentId("52efd18f-329e-4d7b-8847-fdb0f55b08b7");
        assertNotNull(result);
    }

    @Test
    void testFindOrderByPaymentIdNotFound() {
        Order result = paymentRepository.findOrderByPaymentId("52efd18f-329e-4d7b-8847-fdb0f55b08b8");
        assertNull(result);
    }
}
