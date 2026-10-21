package com.topgol.store.dto;

public class PaymentConfirmRequest {
    private Long orderId;
    private String transactionReference;

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }
}
