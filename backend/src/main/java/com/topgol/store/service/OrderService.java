package com.topgol.store.service;

import com.topgol.store.dto.OrderItemRequest;
import com.topgol.store.dto.OrderRequest;
import com.topgol.store.model.*;
import com.topgol.store.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public Order createOrder(OrderRequest request) {
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setPhone(request.getPhone());
        order.setEmail(request.getEmail());
        order.setAddress(request.getAddress());
        order.setDepartment(request.getDepartment());
        order.setCity(request.getCity());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus(OrderStatus.PENDIENTE);

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productService.findById(itemReq.getProductId());

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(product.getId());
            item.setProductName(product.getName() + " - " + product.getTeam());
            item.setSize(itemReq.getSize());
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(product.getPrice());

            order.getItems().add(item);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
        }

        // Descuentos: 5% por pago inmediato (no contraentrega) + 5% si el departamento es Bogota o Cundinamarca
        int discountPercent = 0;
        if (request.getPaymentMethod() != PaymentMethod.CONTRAENTREGA) {
            discountPercent += 5;
        }
        if (isBogotaOCundinamarca(request.getDepartment())) {
            discountPercent += 5;
        }

        BigDecimal discountFactor = BigDecimal.valueOf(100 - discountPercent)
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        BigDecimal finalTotal = total.multiply(discountFactor).setScale(0, RoundingMode.HALF_UP);

        order.setSubtotal(total);
        order.setDiscountPercent(discountPercent);
        order.setTotal(finalTotal);
        return orderRepository.save(order);
    }

    // Detecta si el departamento elegido es Bogota (D.C.) o Cundinamarca,
    // sin importar tildes ni mayusculas.
    private boolean isBogotaOCundinamarca(String departamento) {
        if (departamento == null) return false;
        String normalized = Normalizer.normalize(departamento, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
        return normalized.contains("bogota") || normalized.contains("cundinamarca");
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + id));
    }

    public Order confirmPayment(Long orderId) {
        Order order = findById(orderId);
        order.setStatus(OrderStatus.PAGADO);
        return orderRepository.save(order);
    }
}
