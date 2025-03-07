package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.service.OrderServiceImpl;
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

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderServiceImpl orderService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCreatePage() {
        String viewName = orderController.getCreatePage(model);
        assertEquals("CreateOrder", viewName);
    }

    @Test
    void testPostCreate() {
        Order order = new Order(UUID.randomUUID().toString(), new ArrayList<>(), System.currentTimeMillis(), "John");
        String viewName = orderController.postCreate(order, model);
        assertEquals("redirect:/order/list", viewName);
    }

    @Test
    void testGetListPage() {
        List<Order> orders = new ArrayList<>();
        when(orderService.findAllByAuthor("John")).thenReturn(orders);

        String viewName = orderController.getListPage(model);
        assertEquals("OrderList", viewName);
        verify(model).addAttribute("orders", orders);
    }

    @Test
    void testGetEditPage() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId.toString(), new ArrayList<>(), System.currentTimeMillis(), "John");
        when(orderService.findById(orderId.toString())).thenReturn(order);

        String viewName = orderController.getEditPage(orderId, model);
        assertEquals("EditOrder", viewName);
        verify(model).addAttribute("order", order);
    }

    @Test
    void testPostUpdate() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId.toString(), new ArrayList<>(), System.currentTimeMillis(), "John");
        String viewName = orderController.postUpdate(orderId, order, model);
        assertEquals("redirect:/order/list", viewName);
    }

    @Test
    void testPostDelete() {
        UUID orderId = UUID.randomUUID();
        String viewName = orderController.postDelete(orderId);
        assertEquals("redirect:/order/list", viewName);
    }

    @Test
    void testShowOrderHistory() {
        String name = "John";
        List<Order> orders = new ArrayList<>();
        when(orderService.findAllByAuthor(name)).thenReturn(orders);

        String viewName = orderController.showOrderHistory(name, model);
        assertEquals("OrderList", viewName);
        verify(model).addAttribute("orders", orders);
    }

    @Test
    void testShowPaymentOrderPage() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId.toString(), new ArrayList<>(), System.currentTimeMillis(), "John");
        when(orderService.findById(orderId.toString())).thenReturn(order);

        String viewName = orderController.showPaymentOrderPage(orderId, model);
        assertEquals("PaymentOrder", viewName);
        verify(model).addAttribute("order", order);
    }

    @Test
    void testPayOrder() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order(orderId.toString(), new ArrayList<>(), System.currentTimeMillis(), "John", "SUCCESS");
        when(orderService.updateStatus(orderId.toString(), "SUCCESS")).thenReturn(order);

        String viewName = orderController.payOrder(orderId, model);
        assertEquals("PaymentConfirmation", viewName);
        verify(model).addAttribute("paymentId", orderId.toString());
    }
}