package longhoang.uet.mobile.closm.utils;

import longhoang.uet.mobile.closm.dtos.request.OrderRequestDTO;
import longhoang.uet.mobile.closm.models.Discount;
import longhoang.uet.mobile.closm.models.ProductVariant;

import java.math.BigDecimal;
import java.util.List;

public class PriceUtil {
    public static BigDecimal calculateOrderPrice(List<ProductVariant> productVariants, List<Discount> discounts,
                                                 BigDecimal deliverPayment) {
        BigDecimal price = BigDecimal.ZERO;

        // Tính tổng giá sản phẩm
        for (ProductVariant productVariant : productVariants) {
            price = price.add(productVariant.getPrice());
        }

        // Tính tổng phần trăm giảm giá
        BigDecimal totalDiscountPercent = BigDecimal.ZERO;
        for (Discount discount : discounts) {
            totalDiscountPercent = totalDiscountPercent.add(discount.getDiscount());
        }

        // Tính giá sau khi giảm
        BigDecimal discountAmount = price.multiply(totalDiscountPercent);

        return price.subtract(discountAmount).add(deliverPayment);
    }
}
