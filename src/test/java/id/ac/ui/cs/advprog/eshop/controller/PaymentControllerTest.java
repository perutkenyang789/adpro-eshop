package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentServiceImpl paymentService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCreatePage() {
        String viewName = paymentController.getCreatePage(model);
        assertEquals("CreatePayment", viewName);
    }

    @Test
    void testPostCreate() {
        Payment payment = new Payment(UUID.randomUUID().toString(), null, null);
        String viewName = paymentController.postCreate(payment, model);
        assertEquals("redirect:/payment/list", viewName);
    }

    @Test
    void testGetListPage() {
        List<Payment> payments = new ArrayList<>();
        when(paymentService.getAllPayment()).thenReturn(payments);

        String viewName = paymentController.getListPage(model);
        assertEquals("PaymentList", viewName);
        verify(model).addAttribute("payments", payments);
    }

    @Test
    void testGetEditPage() {
        UUID paymentId = UUID.randomUUID();
        Payment payment = new Payment(paymentId.toString(), null, null);
        when(paymentService.getPayment(paymentId.toString())).thenReturn(payment);

        String viewName = paymentController.getEditPage(paymentId, model);
        assertEquals("EditPayment", viewName);
        verify(model).addAttribute("payment", payment);
    }

    @Test
    void testPostUpdate() {
        UUID paymentId = UUID.randomUUID();
        Payment payment = new Payment(paymentId.toString(), null, null);
        String viewName = paymentController.postUpdate(paymentId, payment, model);
        assertEquals("redirect:/payment/list", viewName);
    }

    @Test
    void testPostDelete() {
        UUID paymentId = UUID.randomUUID();
        String viewName = paymentController.postDelete(paymentId);
        assertEquals("redirect:/payment/list", viewName);
    }

    @Test
    void testShowPaymentDetail() {
        UUID paymentId = UUID.randomUUID();
        Payment payment = new Payment(paymentId.toString(), null, null);
        when(paymentService.getPayment(paymentId.toString())).thenReturn(payment);

        String viewName = paymentController.showPaymentDetail(paymentId, model);
        assertEquals("PaymentDetail", viewName);
        verify(model).addAttribute("payment", payment);
    }

    @Test
    void testShowAllPayments() {
        List<Payment> payments = new ArrayList<>();
        when(paymentService.getAllPayment()).thenReturn(payments);

        String viewName = paymentController.showAllPayments(model);
        assertEquals("PaymentList", viewName);
        verify(model).addAttribute("payments", payments);
    }

    @Test
    void testShowAdminPaymentDetail() {
        UUID paymentId = UUID.randomUUID();
        Payment payment = new Payment(paymentId.toString(), null, null);
        when(paymentService.getPayment(paymentId.toString())).thenReturn(payment);

        String viewName = paymentController.showAdminPaymentDetail(paymentId, model);
        assertEquals("AdminPaymentDetail", viewName);
        verify(model).addAttribute("payment", payment);
    }

    @Test
    void testSetPaymentStatus() {
        UUID paymentId = UUID.randomUUID();
        String status = "accepted";
        Payment payment = new Payment(paymentId.toString(), null, null);
        when(paymentService.getPayment(paymentId.toString())).thenReturn(payment);

        String viewName = paymentController.setPaymentStatus(paymentId, status);
        assertEquals("redirect:/payment/admin/list", viewName);
        verify(paymentService).setStatus(payment, status);
    }
}