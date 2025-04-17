package longhoang.uet.mobile.closm.dtos.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private String description;
    private BigDecimal discount;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;
}
