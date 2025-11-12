package com.example.grocery.dto;

import java.util.List;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long customerId;
    private List<OrderLine> items;

    @Data
    public static class OrderLine {
        private Long itemId;
        private Integer quantity;
    }
}
