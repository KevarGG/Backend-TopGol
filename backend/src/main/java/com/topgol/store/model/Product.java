package com.topgol.store.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String team;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeamCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sport sport;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SleeveType sleeveType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(length = 1000)
    private String description;

    // Color principal en formato hex, usado para renderizar la camiseta en el frontend
    private String primaryColor;
    private String secondaryColor;

    // Nombre del archivo de imagen (portada) dentro de frontend/src/assets/products/
    private String imageUrl;

    // Nombres de fotos adicionales (galeria), separados por ';'
    @Column(length = 500)
    private String extraImages;

    // Liga o torneo al que pertenece (ej: "Premier League", "Colombiana", "Seleccion")
    private String liga;

    @ElementCollection
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "size")
    private Set<String> sizes = new HashSet<>();

    private Integer stock;

    private Boolean featured = false;

    public Product() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public TeamCategory getCategory() { return category; }
    public void setCategory(TeamCategory category) { this.category = category; }

    public Sport getSport() { return sport; }
    public void setSport(Sport sport) { this.sport = sport; }

    public SleeveType getSleeveType() { return sleeveType; }
    public void setSleeveType(SleeveType sleeveType) { this.sleeveType = sleeveType; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPrimaryColor() { return primaryColor; }
    public void setPrimaryColor(String primaryColor) { this.primaryColor = primaryColor; }

    public String getSecondaryColor() { return secondaryColor; }
    public void setSecondaryColor(String secondaryColor) { this.secondaryColor = secondaryColor; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getExtraImages() { return extraImages; }
    public void setExtraImages(String extraImages) { this.extraImages = extraImages; }

    public String getLiga() { return liga; }
    public void setLiga(String liga) { this.liga = liga; }

    public Set<String> getSizes() { return sizes; }
    public void setSizes(Set<String> sizes) { this.sizes = sizes; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Boolean getFeatured() { return featured; }
    public void setFeatured(Boolean featured) { this.featured = featured; }
}
