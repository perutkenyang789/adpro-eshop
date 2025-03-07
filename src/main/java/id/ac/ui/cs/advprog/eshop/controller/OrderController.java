package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController implements CrudController<Order, UUID> {

    private final OrderServiceImpl orderService;

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @Override
    @GetMapping("/create")
    public String getCreatePage(Model model) {
        return "CreateOrder";
    }

    @Override
    @PostMapping("/create")
    public String postCreate(Order order, Model model) {
        orderService.createOrder(order);
        return "redirect:/order/list";
    }

    @Override
    @GetMapping("/list")
    public String getListPage(Model model) {
        model.addAttribute("orders", orderService.findAllByAuthor("John")); // Example author
        return "OrderList";
    }

    @Override
    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") UUID id, Model model) {
        Order order = orderService.findById(id.toString());
        model.addAttribute("order", order);
        return "EditOrder";
    }

    @Override
    @PostMapping("/edit/{id}")
    public String postUpdate(@PathVariable("id") UUID id, Order order, Model model) {
        orderService.updateStatus(id.toString(), order.getStatus());
        return "redirect:/order/list";
    }

    @Override
    @PostMapping("/delete/{id}")
    public String postDelete(@PathVariable("id") UUID id) {
        // Implement delete functionality
        return "redirect:/order/list";
    }

    @GetMapping("/history")
    public String showHistoryForm() {
        return "OrderHistory";
    }

    @PostMapping("/history")
    public String showOrderHistory(@RequestParam("name") String name, Model model) {
        model.addAttribute("orders", orderService.findAllByAuthor(name));
        return "OrderList";
    }

    @GetMapping("/pay/{orderId}")
    public String showPaymentOrderPage(@PathVariable("orderId") UUID orderId, Model model) {
        Order order = orderService.findById(orderId.toString());
        model.addAttribute("order", order);
        return "PaymentOrder";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrder(@PathVariable("orderId") UUID orderId, Model model) {
        orderService.updateStatus(orderId.toString(), "SUCCESS");
        model.addAttribute("paymentId", orderId.toString());
        return "PaymentConfirmation";
    }
}