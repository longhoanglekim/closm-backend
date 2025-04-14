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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderService {
    @Autowired
    ProductVariantRepository productVariantRepository;
    @Autowired
    DiscountRepository discountRepository;
    public OrderPriceSummaryDTO calculateOrderPrice(OrderRequestDTO request) {

        Map<ProductVariant, Integer> variantsCount = new HashMap<>();
        Map<Long, Integer> idsCount = request.getItemIds();
        for (Long id : idsCount.keySet()) {
            variantsCount.put(productVariantRepository.findById(id).get(), idsCount.get(id));
        }

        List<Discount> discounts = new ArrayList<>();
        for (Long id : request.getDiscountIds()) {
            discounts.add(discountRepository.findById(id).get());
        }

        return PriceUtil.calculateOrderPrice(variantsCount, discounts, request.getAddress());
    }
}
