package com.topgol.store.dto;

import com.topgol.store.model.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderRequest {
    @NotBlank
    private String customerName;
    @NotBlank
    private String phone;
    @Email
    private String email;
    @NotBlank
    private String address;
    @NotBlank
    private String department;
    @NotBlank
    private String city;
    @NotNull
    private PaymentMethod paymentMethod;
    @NotEmpty
    @Valid
    private List<OrderItemRequest> items;

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}
