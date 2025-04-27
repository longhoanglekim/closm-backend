package longhoang.uet.mobile.closm.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;
    private String imageUrl;
    private String tag;
    private String size;
    private String color;
    private String description;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "base_product_id")
    private BaseProduct baseProduct;

    @OneToMany(mappedBy = "productItem", orphanRemoval = true, cascade = CascadeType.ALL)
    List<OrderItem> orderItems = new ArrayList<>();
}
