package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void testValidVoucherPayment_Success() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP20230101XYZ");

        Payment payment = new Payment("1", PaymentMethod.VOUCHER, paymentData);

        assertEquals("1", payment.getId());
        assertEquals(PaymentMethod.VOUCHER, payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testInvalidVoucherPayment_Rejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALIDCODE123");

        Payment payment = new Payment("2", PaymentMethod.VOUCHER, paymentData);

        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testValidBankTransferPayment_Success() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("accountNumber", "1234567890");
        paymentData.put("bankName", "Bank UI");

        Payment payment = new Payment("3", PaymentMethod.BANK_TRANSFER, paymentData);

        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

    @Test
    void testInvalidBankTransferPayment_Rejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("accountNumber", "");

        Payment payment = new Payment("4", PaymentMethod.BANK_TRANSFER, paymentData);

        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testPaymentDataCannotBeEmpty() {
        Map<String, String> emptyData = new HashMap<>();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Payment("5", PaymentMethod.BANK_TRANSFER, emptyData)
        );

        assertEquals("Payment data cannot be empty", exception.getMessage());
    }

    @Test
    void testSetValidStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP20230101XYZ");

        Payment payment = new Payment("6", PaymentMethod.VOUCHER, paymentData);
        payment.setStatus(PaymentStatus.REJECTED);

        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    @Test
    void testSetInvalidStatusThrowsException() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP20230101XYZ");

        Payment payment = new Payment("7", PaymentMethod.VOUCHER, paymentData);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                payment.setStatus(null)
        );

        assertEquals("Invalid payment status", exception.getMessage());
    }
}
