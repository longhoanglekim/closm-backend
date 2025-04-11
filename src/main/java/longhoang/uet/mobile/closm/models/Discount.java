package longhoang.uet.mobile.closm.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "discounts")
public class Discount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String description;
    private BigDecimal discount;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private LocalDate startDate;
    private LocalDate endDate;

}
