package com.example.grocery.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private GroceryItem item;

    private Integer quantity;

    private Double priceAtPurchase; // item price snapshot
}
