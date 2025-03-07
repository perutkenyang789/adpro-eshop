package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/payment")
public class PaymentController implements CrudController<Payment, UUID> {

    private final PaymentServiceImpl paymentService;

    @Autowired
    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    @GetMapping("/create")
    public String getCreatePage(Model model) {
        return "CreatePayment";
    }

    @Override
    @PostMapping("/create")
    public String postCreate(Payment payment, Model model) {
        // Implement create functionality
        return "redirect:/payment/list";
    }

    @Override
    @GetMapping("/list")
    public String getListPage(Model model) {
        model.addAttribute("payments", paymentService.getAllPayment());
        return "PaymentList";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") UUID id, Model model) {
        Payment payment = paymentService.getPayment(id.toString());
        model.addAttribute("payment", payment);
        return "EditPayment";
    }

    @Override
    @PostMapping("/edit/{id}")
    public String postUpdate(@PathVariable("id") UUID id, Payment payment, Model model) {
        paymentService.setStatus(payment, payment.getStatus().getValue());
        return "redirect:/payment/list";
    }

    @Override
    @PostMapping("/delete/{id}")
    public String postDelete(@PathVariable("id") UUID id) {
        // Implement delete functionality
        return "redirect:/payment/list";
    }

    @GetMapping("/detail")
    public String showPaymentDetailForm() {
        return "PaymentDetailForm";
    }

    @GetMapping("/detail/{paymentId}")
    public String showPaymentDetail(@PathVariable("paymentId") UUID paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId.toString());
        model.addAttribute("payment", payment);
        return "PaymentDetail";
    }

    @GetMapping("/admin/list")
    public String showAllPayments(Model model) {
        model.addAttribute("payments", paymentService.getAllPayment());
        return "PaymentList";
    }

    @GetMapping("/admin/detail/{paymentId}")
    public String showAdminPaymentDetail(@PathVariable("paymentId") UUID paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId.toString());
        model.addAttribute("payment", payment);
        return "AdminPaymentDetail";
    }

    @PostMapping("/admin/set-status/{paymentId}")
    public String setPaymentStatus(@PathVariable("paymentId") UUID paymentId, @RequestParam("status") String status) {
        Payment payment = paymentService.getPayment(paymentId.toString());
        paymentService.setStatus(payment, status);
        return "redirect:/payment/admin/list";
    }
}