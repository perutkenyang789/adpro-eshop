package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> validVoucherData;
    private Map<String, String> invalidVoucherData;
    private Map<String, String> validBankTransferData;
    private Map<String, String> invalidBankTransferData;

    @BeforeEach
    void setUp() {
        validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "ESHOP123456782024");

        invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "INVALID12345678");

        validBankTransferData = new HashMap<>();
        validBankTransferData.put("accountNumber", "123456789");
        validBankTransferData.put("bankName", "Bank UI");

        invalidBankTransferData = new HashMap<>();
        invalidBankTransferData.put("", ""); // Invalid empty key and value
    }

    @Test
    void testValidVoucherPayment() {
        Payment payment = new Payment("1", "Voucher", validVoucherData);
        assertEquals("Voucher", payment.method);
        assertEquals("SUCCESS", payment.status);
    }

    @Test
    void testInvalidVoucherPayment() {
        Payment payment = new Payment("2", "Voucher", invalidVoucherData);
        assertEquals("Voucher", payment.method);
        assertEquals("REJECTED", payment.status);
    }

    @Test
    void testValidBankTransferPayment() {
        Payment payment = new Payment("3", "Bank Transfer", validBankTransferData);
        assertEquals("Bank Transfer", payment.method);
        assertEquals("SUCCESS", payment.status);
    }

    @Test
    void testInvalidBankTransferPayment() {
        Payment payment = new Payment("4", "Bank Transfer", invalidBankTransferData);
        assertEquals("Bank Transfer", payment.method);
        assertEquals("REJECTED", payment.status);
    }

    @Test
    void testInvalidPaymentMethod() {
        assertThrows(IllegalArgumentException.class, () -> new Payment("5", "Credit Card", validBankTransferData));
    }

    @Test
    void testNullPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> new Payment("6", "Bank Transfer", null));
    }

    @Test
    void testEmptyPaymentData() {
        Map<String, String> emptyData = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> new Payment("7", "Bank Transfer", emptyData));
    }
}