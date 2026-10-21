package com.topgol.store.controller;

import com.topgol.store.dto.PaymentConfirmRequest;
import com.topgol.store.model.Order;
import com.topgol.store.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * Controlador de pagos.
 * Este es un simulador de pasarela de pago. Para produccion,
 * reemplaza este metodo por la integracion real con Wompi, PayU o Mercado Pago
 * (pasarelas ampliamente usadas en Colombia), usando sus SDKs/API keys.
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final OrderService orderService;

    public PaymentController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/confirm")
    public Map<String, Object> confirmPayment(@RequestBody PaymentConfirmRequest request) {
        Order order = orderService.confirmPayment(request.getOrderId());
        String reference = request.getTransactionReference() != null
                ? request.getTransactionReference()
                : "TOPGOL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        return Map.of(
                "status", "APROBADO",
                "orderId", order.getId(),
                "reference", reference,
                "total", order.getTotal()
        );
    }
}
