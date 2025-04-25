package longhoang.uet.mobile.closm.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.models.ProductItem;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPriceSummaryDTO {
    Map<Long, Integer> ProductItemsCount = new HashMap<>();
    private BigDecimal productTotal;     // Tổng giá sản phẩm chưa giảm
    private BigDecimal discountAmount;   // Tổng số tiền giảm
    private BigDecimal deliveryFee;      // Phí ship
    private BigDecimal finalPrice;       // Tổng tiền phải trả
}
