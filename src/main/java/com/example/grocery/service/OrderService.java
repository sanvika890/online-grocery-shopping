package com.example.grocery.service;

import com.example.grocery.dto.CreateOrderRequest;
import com.example.grocery.entity.Customer;
import com.example.grocery.entity.GroceryItem;
import com.example.grocery.entity.Order;
import com.example.grocery.entity.OrderItem;
import com.example.grocery.exception.ResourceNotFoundException;
import com.example.grocery.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final GroceryItemService groceryItemService;

    public OrderService(OrderRepository orderRepository, CustomerService customerService,
        GroceryItemService groceryItemService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.groceryItemService = groceryItemService;
    }

    public Order create(CreateOrderRequest req) {
        Customer customer = customerService.get(req.getCustomerId());
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = req.getItems().stream().map(line -> {
            GroceryItem item = groceryItemService.get(line.getItemId());
            if (item.getQuantity() < line.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for item: " + item.getName());
            }
            OrderItem oi = new OrderItem();
            oi.setItem(item);
            oi.setQuantity(line.getQuantity());
            oi.setPriceAtPurchase(item.getPrice());
            // reduce stock
            item.setQuantity(item.getQuantity() - line.getQuantity());
            groceryItemService.create(item); // save updated quantity
            return oi;
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        double total = orderItems.stream().mapToDouble(i -> i.getPriceAtPurchase() * i.getQuantity()).sum();
        order.setTotalPrice(total);
        return orderRepository.save(order);
    }

    public List<Order> list() { return orderRepository.findAll(); }

    public Order get(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
    }

    public Order update(Long id, CreateOrderRequest req) {
        Order existing = get(id);
        // Simple strategy: clear existing items and create new ones (stock adjustments are simplistic)
        existing.getItems().clear();
        Order updated = create(req);
        updated.setId(id);
        return orderRepository.save(updated);
    }

    public void delete(Long id) {
        Order o = get(id);
        orderRepository.delete(o);
    }
}
