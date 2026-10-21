package com.topgol.store.service;

import com.topgol.store.model.Product;
import com.topgol.store.model.Sport;
import com.topgol.store.model.TeamCategory;
import com.topgol.store.repository.ProductRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findProducts(Sport sport, TeamCategory category, String sleeveType, String search, String liga) {
        Specification<Product> spec = Specification.where(null);

        if (sport != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("sport"), sport));
        }
        if (category != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category"), category));
        }
        if (sleeveType != null && !sleeveType.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("sleeveType"), sleeveType.toUpperCase()));
        }
        if (liga != null && !liga.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("liga"), liga));
        }
        if (search != null && !search.isBlank()) {
            String like = "%" + search.toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("team")), like)
            ));
        }

        return productRepository.findAll(spec);
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + id));
    }

    public List<String> findLigas() {
        return productRepository.findAll().stream()
                .map(Product::getLiga)
                .filter(l -> l != null && !l.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    public List<Product> findFeatured() {
        return productRepository.findAll().stream()
                .filter(p -> Boolean.TRUE.equals(p.getFeatured()))
                .toList();
    }
}
