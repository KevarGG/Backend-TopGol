package com.topgol.store.dto;

import jakarta.validation.constraints.NotNull;

public class OrderItemRequest {
    @NotNull
    private Long productId;
    @NotNull
    private String size;
    @NotNull
    private Integer quantity;

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
