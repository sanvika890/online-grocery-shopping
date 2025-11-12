package com.example.grocery.controller;

import com.example.grocery.entity.GroceryItem;
import com.example.grocery.service.GroceryItemService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
public class GroceryItemController {

    private final GroceryItemService service;

    public GroceryItemController(GroceryItemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GroceryItem> create(@RequestBody GroceryItem i) { return ResponseEntity.ok(service.create(i)); }

    @GetMapping
    public ResponseEntity<List<GroceryItem>> list() { return ResponseEntity.ok(service.list()); }

    @GetMapping("/{id}")
    public ResponseEntity<GroceryItem> get(@PathVariable Long id) { return ResponseEntity.ok(service.get(id)); }

    @PutMapping("/{id}")
    public ResponseEntity<GroceryItem> update(@PathVariable Long id, @RequestBody GroceryItem i) { return ResponseEntity.ok(service.update(id, i)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
