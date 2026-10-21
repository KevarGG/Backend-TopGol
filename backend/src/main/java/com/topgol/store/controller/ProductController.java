package com.topgol.store.controller;

import com.topgol.store.model.Product;
import com.topgol.store.model.Sport;
import com.topgol.store.model.TeamCategory;
import com.topgol.store.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts(
            @RequestParam(required = false) Sport sport,
            @RequestParam(required = false) TeamCategory category,
            @RequestParam(required = false) String sleeveType,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String liga
    ) {
        return productService.findProducts(sport, category, sleeveType, search, liga);
    }

    @GetMapping("/featured")
    public List<Product> getFeatured() {
        return productService.findFeatured();
    }

    @GetMapping("/ligas")
    public List<String> getLigas() {
        return productService.findLigas();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.findById(id);
    }
}
