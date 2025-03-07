package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderServiceImpl orderServiceImpl;

    private Order testOrder;
    private Map<String, String> voucherPaymentData;
    private Map<String, String> bankTransferPaymentData;
    private List<Product> products;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inject orderServiceImpl using ReflectionTestUtils
        ReflectionTestUtils.setField(paymentService, "orderServiceImpl", orderServiceImpl);

        // Create test products
        products = new ArrayList<>();
        products.add(new Product());

        // Create test order
        testOrder = new Order("81d7ddd6-78fc-4755-ad75-2c05f7a0f722", products, System.currentTimeMillis(), "testUser");

        // Create payment data for voucher
        voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP12345678");

        // Create payment data for bank transfer
        bankTransferPaymentData = new HashMap<>();
        bankTransferPaymentData.put("accountNumber", "1234567890");
        bankTransferPaymentData.put("bankName", "2a1a8792-93e6-4b95-93e1-677ce2de59d8");
        bankTransferPaymentData.put("accountName", "cd5c8313-9a69-4499-952b-452cd766740d");
    }

    @Test
    public void testAddPayment_ValidVoucherMethod() {
        when(paymentRepository.save(any(Order.class), any(Payment.class))).thenReturn(null);
        when(paymentRepository.findOrderByPaymentId(anyString())).thenReturn(testOrder);

        Payment result = paymentService.addPayment(testOrder, "VOUCHER", voucherPaymentData);

        assertNotNull(result);
        assertEquals("81d7ddd6-78fc-4755-ad75-2c05f7a0f722", result.getId());
        assertEquals(PaymentMethod.VOUCHER, result.getMethod());
        assertEquals(voucherPaymentData, result.getPaymentData());
        verify(paymentRepository, times(1)).save(testOrder, result);
    }

    @Test
    public void testAddPayment_ValidBankTransferMethod() {
        when(paymentRepository.save(any(Order.class), any(Payment.class))).thenReturn(null);
        when(paymentRepository.findOrderByPaymentId(anyString())).thenReturn(testOrder);

        Payment result = paymentService.addPayment(testOrder, "BANK_TRANSFER", bankTransferPaymentData);

        assertNotNull(result);
        assertEquals("81d7ddd6-78fc-4755-ad75-2c05f7a0f722", result.getId());
        assertEquals(PaymentMethod.BANK_TRANSFER, result.getMethod());
        assertEquals(bankTransferPaymentData, result.getPaymentData());
        verify(paymentRepository, times(1)).save(testOrder, result);
    }

    @Test
    public void testAddPayment_InvalidVoucherFormat() {
        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "INVALID");
        when(paymentRepository.save(any(Order.class), any(Payment.class))).thenReturn(null);

        when(paymentRepository.findOrderByPaymentId(anyString())).thenReturn(testOrder);

        Payment result = paymentService.addPayment(testOrder, "VOUCHER", invalidVoucherData);

        assertNotNull(result);
        assertEquals(PaymentStatus.REJECTED, result.getStatus()); // Invalid voucher format should be REJECTED
        verify(paymentRepository, times(1)).save(testOrder, result);
    }

    @Test
    public void testAddPayment_EmptyPaymentData() {
        Map<String, String> emptyData = new HashMap<>();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.addPayment(testOrder, "BANK_TRANSFER", emptyData);
        });

        assertTrue(exception.getMessage().contains("Payment data cannot be empty"));
        verify(paymentRepository, never()).save(any(), any());
    }

    @Test
    public void testAddPayment_NullOrderId() {
        Order orderWithNullId = new Order(null, products, System.currentTimeMillis(), "testUser");
        when(paymentRepository.save(any(Order.class), any(Payment.class))).thenReturn(null);

        when(paymentRepository.findOrderByPaymentId(anyString())).thenReturn(orderWithNullId);

        Payment result = paymentService.addPayment(orderWithNullId, "BANK_TRANSFER", bankTransferPaymentData);

        assertNotNull(result);
        assertNotNull(result.getId()); // Should generate UUID
        assertEquals(PaymentMethod.BANK_TRANSFER, result.getMethod());
        verify(paymentRepository, times(1)).save(orderWithNullId, result);
    }

    @Test
    public void testAddPayment_InvalidMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.addPayment(testOrder, "INVALID_METHOD", bankTransferPaymentData);
        });

        assertTrue(exception.getMessage().contains("Invalid payment method"));
        verify(paymentRepository, never()).save(any(), any());
    }

    @Test
    public void testSetStatus_Success() {
        Payment payment = new Payment("5f2f5778-a815-428b-a5f4-abfadfb0856d", PaymentMethod.VOUCHER, voucherPaymentData);
        Order order = new Order("81d7ddd6-78fc-4755-ad75-2c05f7a0f722", products, System.currentTimeMillis(), "testUser");

        when(paymentRepository.findOrderByPaymentId("5f2f5778-a815-428b-a5f4-abfadfb0856d")).thenReturn(order);

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertNotNull(result);
        verify(orderServiceImpl, times(1)).updateStatus("81d7ddd6-78fc-4755-ad75-2c05f7a0f722", "SUCCESS");
    }

    @Test
    public void testSetStatus_Rejected() {
        Payment payment = new Payment("5f2f5778-a815-428b-a5f4-abfadfb0856d", PaymentMethod.VOUCHER, voucherPaymentData);
        Order order = new Order("81d7ddd6-78fc-4755-ad75-2c05f7a0f722", products, System.currentTimeMillis(), "testUser");

        when(paymentRepository.findOrderByPaymentId("5f2f5778-a815-428b-a5f4-abfadfb0856d")).thenReturn(order);

        Payment result = paymentService.setStatus(payment, "REJECTED");

        assertNotNull(result);
        verify(orderServiceImpl, times(1)).updateStatus("81d7ddd6-78fc-4755-ad75-2c05f7a0f722", "FAILED");
    }

    @Test
    public void testSetStatus_InvalidStatus() {
        Payment payment = new Payment("5f2f5778-a815-428b-a5f4-abfadfb0856d", PaymentMethod.VOUCHER, voucherPaymentData);
        Order order = new Order("81d7ddd6-78fc-4755-ad75-2c05f7a0f722", products, System.currentTimeMillis(), "testUser");

        when(paymentRepository.findOrderByPaymentId("5f2f5778-a815-428b-a5f4-abfadfb0856d")).thenReturn(order);

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "PENDING");
        });

        verify(orderServiceImpl, never()).updateStatus(anyString(), anyString());
    }

    @Test
    public void testSetStatus_NullOrder() {
        Payment payment = new Payment("5b850806-9c12-4a55-8c63-07ad1357758f", PaymentMethod.VOUCHER, voucherPaymentData);

        when(paymentRepository.findOrderByPaymentId("5b850806-9c12-4a55-8c63-07ad1357758f")).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            paymentService.setStatus(payment, "SUCCESS");
        });

        verify(orderServiceImpl, never()).updateStatus(anyString(), anyString());
    }

    @Test
    public void testGetPayment() {
        Payment expectedPayment = new Payment("5f2f5778-a815-428b-a5f4-abfadfb0856d", PaymentMethod.VOUCHER, voucherPaymentData);
        when(paymentRepository.findByPaymentId("5f2f5778-a815-428b-a5f4-abfadfb0856d")).thenReturn(expectedPayment);

        Payment result = paymentService.getPayment("5f2f5778-a815-428b-a5f4-abfadfb0856d");

        assertNotNull(result);
        assertEquals(expectedPayment, result);
        verify(paymentRepository, times(1)).findByPaymentId("5f2f5778-a815-428b-a5f4-abfadfb0856d");
    }

    @Test
    public void testGetAllPayment() {
        List<Payment> expectedPayments = new ArrayList<>();
        expectedPayments.add(new Payment("a634c694-1d13-4b90-a7db-a27d1c04f03a", PaymentMethod.VOUCHER, voucherPaymentData));
        expectedPayments.add(new Payment("c1d6333e-1a9b-41f5-8d2f-f93ae12bb627", PaymentMethod.BANK_TRANSFER, bankTransferPaymentData));

        when(paymentRepository.findAll()).thenReturn(expectedPayments);

        List<Payment> result = paymentService.getAllPayment();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedPayments, result);
        verify(paymentRepository, times(1)).findAll();
    }
}