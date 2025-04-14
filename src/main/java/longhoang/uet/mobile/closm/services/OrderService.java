package longhoang.uet.mobile.closm.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.dtos.request.OrderRequestDTO;
import longhoang.uet.mobile.closm.dtos.response.OrderPriceSummaryDTO;
import longhoang.uet.mobile.closm.models.Discount;
import longhoang.uet.mobile.closm.models.ProductVariant;
import longhoang.uet.mobile.closm.repositories.DiscountRepository;
import longhoang.uet.mobile.closm.repositories.ProductVariantRepository;
import longhoang.uet.mobile.closm.utils.PriceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderService {
    @Autowired
    ProductVariantRepository productVariantRepository;
    @Autowired
    DiscountRepository discountRepository;
    public OrderPriceSummaryDTO calculateOrderPrice(OrderRequestDTO request) {
        List<ProductVariant> productVariant = new ArrayList<>();
        for (Long id : request.getItemIds()) {
            productVariant.add(productVariantRepository.findById(id).get());
        }
        List<Discount> discounts = new ArrayList<>();
        for (Long id : request.getDiscountIds()) {
            discounts.add(discountRepository.findById(id).get());
        }
        return PriceUtil.calculateOrderPrice(productVariant, discounts, request.getAddress());
    }
}
