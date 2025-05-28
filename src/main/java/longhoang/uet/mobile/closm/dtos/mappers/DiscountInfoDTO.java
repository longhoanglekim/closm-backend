package longhoang.uet.mobile.closm.dtos.mappers;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountInfoDTO {
    private Long id;
    private String name;
    private BigDecimal discountPercentage;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
    private LocalDate startDate;
    private LocalDate endDate;
}
