package longhoang.uet.mobile.closm.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryOrderPrice {
    private BigDecimal itemsTotalPrice;
    private BigDecimal discountAmount;
    private BigDecimal deliveryAmount;
    private BigDecimal finalPrice;
}