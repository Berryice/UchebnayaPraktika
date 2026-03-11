package com.practice.uchebnayapraktika.Entitys;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ProductType type;

    @Column(name = "name")
    private String name;

    @Column(name = "article")
    private String article;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private MaterialType material;

    @OneToMany(mappedBy = "product")
    private List<ProductWorkshop> productWorkshops;

    public Integer getTotalTime() {
        if (productWorkshops == null || productWorkshops.isEmpty()) {
            return 0;
        }
        return productWorkshops.stream()
                .mapToInt(pw -> pw.getTime().intValue())
                .sum();
    }

    public Product() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public ProductType getType() { return type; }
    public void setType(ProductType type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getArticle() { return article; }
    public void setArticle(String article) { this.article = article; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public MaterialType getMaterial() { return material; }
    public void setMaterial(MaterialType material) { this.material = material; }

    public List<ProductWorkshop> getProductWorkshops() { return productWorkshops; }
    public void setProductWorkshops(List<ProductWorkshop> productWorkshops) { 
        this.productWorkshops = productWorkshops; 
    }
}
