package longhoang.uet.mobile.closm.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPriceSummaryDTO {
    private BigDecimal productTotal;     // Tổng giá sản phẩm chưa giảm
    private BigDecimal discountAmount;   // Tổng số tiền giảm
    private BigDecimal deliveryFee;      // Phí ship
    private BigDecimal finalPrice;       // Tổng tiền phải trả
}
