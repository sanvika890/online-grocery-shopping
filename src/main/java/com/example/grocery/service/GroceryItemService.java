package com.example.grocery.service;

import com.example.grocery.entity.GroceryItem;
import com.example.grocery.exception.ResourceNotFoundException;
import com.example.grocery.repository.GroceryItemRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GroceryItemService {

    private final GroceryItemRepository repo;

    public GroceryItemService(GroceryItemRepository repo) {
        this.repo = repo;
    }

    public GroceryItem create(GroceryItem item) {
        return repo.save(item);
    }

    public List<GroceryItem> list() { return repo.findAll(); }

    public GroceryItem get(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item not found with id " + id));
    }

    public GroceryItem update(Long id, GroceryItem updated) {
        GroceryItem item = get(id);
        item.setName(updated.getName());
        item.setCategory(updated.getCategory());
        item.setPrice(updated.getPrice());
        item.setQuantity(updated.getQuantity());
        return repo.save(item);
    }

    public void delete(Long id) {
        GroceryItem item = get(id);
        repo.delete(item);
    }
}
