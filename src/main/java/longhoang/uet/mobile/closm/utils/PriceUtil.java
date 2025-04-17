package longhoang.uet.mobile.closm.utils;

import longhoang.uet.mobile.closm.dtos.request.OrderRequestDTO;
import longhoang.uet.mobile.closm.dtos.response.OrderPriceSummaryDTO;
import longhoang.uet.mobile.closm.dtos.response.RouteInfo;
import longhoang.uet.mobile.closm.models.Discount;
import longhoang.uet.mobile.closm.models.ProductVariant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Component
public class PriceUtil {
    private static final BigDecimal BASE_FEE = new BigDecimal("10000"); // 10.000 VNĐ
    private static final BigDecimal FEE_PER_KM = new BigDecimal("5000"); // 5.000 VNĐ / km

    public static BigDecimal calculateDeliverFee(RouteInfo routeInfo) {
        BigDecimal distance = BigDecimal.valueOf(routeInfo.getDistanceInKm());

        BigDecimal variableFee = distance.multiply(FEE_PER_KM);
        BigDecimal totalFee = BASE_FEE.add(variableFee);

        return totalFee.setScale(0, RoundingMode.HALF_UP); // Làm tròn về số nguyên
    }


    public static OrderPriceSummaryDTO calculateOrderPrice(Map<ProductVariant, Integer> variantsCount,
                                                           String address) {
        OrderPriceSummaryDTO orderPriceSummaryDTO = new OrderPriceSummaryDTO();
        BigDecimal price = BigDecimal.ZERO;
        for (ProductVariant productVariant : variantsCount.keySet()) {
            BigDecimal quantity = BigDecimal.valueOf(variantsCount.get(productVariant));
            orderPriceSummaryDTO.getProductVariantsCount().put(productVariant.getId(), quantity.intValue() );

            price = price.add(productVariant.getPrice().multiply(quantity));
        }
        orderPriceSummaryDTO.setProductTotal(price);
        BigDecimal totalDiscountPercent = BigDecimal.ZERO;
        orderPriceSummaryDTO.setDiscountAmount(totalDiscountPercent);

        BigDecimal discountAmount = price.multiply(totalDiscountPercent);
        price = price.subtract(discountAmount);

        RouteInfo routeInfo = LocationUtil.calculateDistanceFromShop(address);
        BigDecimal deliveryFee = calculateDeliverFee(routeInfo);
        price = price.add(deliveryFee);
        orderPriceSummaryDTO.setDeliveryFee(deliveryFee);
        orderPriceSummaryDTO.setFinalPrice(price);
        return orderPriceSummaryDTO;
    }


}
