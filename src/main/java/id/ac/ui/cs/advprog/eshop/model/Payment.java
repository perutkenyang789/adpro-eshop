package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

class Payment{
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        setMethod(method);
        setStatus(method, paymentData);
        this.paymentData = paymentData;
    }

    private void setMethod(String method) {
        if (method.equals("Voucher") || method.equals("Bank Transfer")) {
            this.method = method;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void setStatus(String method, Map<String, String> paymentData) {
        boolean isValid = false;
        if (method.equals("Voucher")) {
            isValid = checkVoucherValidity(paymentData.get("voucherCode"));
            if (isValid) {
                this.status = "SUCCESS";
            } else {
                this.status = "REJECTED";
            }
        } else if (method.equals("Bank Transfer")) {
            isValid = checkBankTransferValidity(paymentData);
            if (isValid) {
                this.status = "SUCCESS";
            } else {
                this.status = "REJECTED";
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void checkPaymentData(Map<String, String> paymentData) {
        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.paymentData = paymentData;
    }

    private boolean checkVoucherValidity(String voucherCode) {
        return voucherCode.matches("^ESHOP[a-zA-Z0-9]*\\d{8}[a-zA-Z0-9]*$");
    }

    private boolean checkBankTransferValidity(Map<String, String> paymentData) {
        boolean keyNotEmpty = !paymentData.containsKey("") && !paymentData.containsKey(null);
        boolean valueNotEmpty = !paymentData.containsValue("") && !paymentData.containsValue(null);
        return keyNotEmpty && valueNotEmpty;
    }
}