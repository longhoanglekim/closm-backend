package longhoang.uet.mobile.closm.utils;

import longhoang.uet.mobile.closm.dtos.request.OrderRequestDTO;
import longhoang.uet.mobile.closm.models.Discount;
import longhoang.uet.mobile.closm.models.ProductVariant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PriceUtil {
    @Autowired
    private LocationUtil locationUtil;





    public static BigDecimal calculateOrderPrice(List<ProductVariant> productVariants, List<Discount> discounts,
                                                 BigDecimal deliverPayment) {
        BigDecimal price = BigDecimal.ZERO;
        for (ProductVariant productVariant : productVariants) {
            price = price.add(productVariant.getPrice());
        }
        BigDecimal totalDiscountPercent = BigDecimal.ZERO;
        for (Discount discount : discounts) {
            totalDiscountPercent = totalDiscountPercent.add(discount.getDiscount());
        }
        BigDecimal discountAmount = price.multiply(totalDiscountPercent);
        return price.subtract(discountAmount).add(deliverPayment);
    }
}
