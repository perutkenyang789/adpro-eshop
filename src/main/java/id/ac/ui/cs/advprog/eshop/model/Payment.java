package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private PaymentMethod method;
    private PaymentStatus status;
    private Map<String,String> paymentData;

    public Payment(String id, PaymentMethod method, Map<String,String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = PaymentStatus.REJECTED; // Default to REJECTED

        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException("Payment data cannot be empty");
        }

        validateAndSetStatus();
    }

    public void setStatus(PaymentStatus status) {
        if (status == null || !PaymentStatus.contains(status.name())) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        this.status = status;
    }

    private void validateAndSetStatus() {
        boolean isValid = switch (method) {
            case VOUCHER -> checkVoucherValidity(paymentData.get("voucherCode"));
            case BANK_TRANSFER -> checkBankTransferValidity(paymentData);
        };

        this.status = isValid ? PaymentStatus.SUCCESS : PaymentStatus.REJECTED;
    }

    private boolean checkVoucherValidity(String voucherCode) {
        return voucherCode != null && voucherCode.matches("^ESHOP[a-zA-Z0-9]*\\d{8}[a-zA-Z0-9]*$");
    }

    private boolean checkBankTransferValidity(Map<String, String> paymentData) {
        return !paymentData.containsKey("") && !paymentData.containsValue("");
    }
}
