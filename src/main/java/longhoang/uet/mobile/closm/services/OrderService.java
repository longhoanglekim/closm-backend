package longhoang.uet.mobile.closm.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.dtos.request.OrderRequestDTO;
import longhoang.uet.mobile.closm.dtos.response.OrderConfirmationDTO;
import longhoang.uet.mobile.closm.dtos.response.OrderPriceSummaryDTO;
import longhoang.uet.mobile.closm.models.*;
import longhoang.uet.mobile.closm.repositories.DiscountRepository;
import longhoang.uet.mobile.closm.repositories.OrderRepository;
import longhoang.uet.mobile.closm.repositories.ProductVariantRepository;
import longhoang.uet.mobile.closm.utils.PriceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    ProductVariantRepository productVariantRepository;
    @Autowired
    DiscountRepository discountRepository;
    @Autowired
    UserService userService;
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

    public Order confirmOrder(OrderConfirmationDTO orderConfirmationDTO) throws Exception {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(userService.getUser(orderConfirmationDTO.getUserEmail()).get());
        order.setShippingAddress(orderConfirmationDTO.getAddress());
        for (Long id : orderConfirmationDTO.getOrderPriceSummary().getProductVariantsCount().keySet()) {
            OrderVariant orderVariant = new OrderVariant();
            ProductVariant productVariant = productVariantRepository.findById(id).get();
            orderVariant.setProductVariant(productVariant);
            orderVariant.setOrder(order);
            productVariant.getOrderVariants().add(orderVariant);
            productVariantRepository.save(productVariant);
            order.getOrderVariants().add(orderVariant);
        }
        order.setTotalPrice(orderConfirmationDTO.getOrderPriceSummary().getFinalPrice());
        return orderRepository.save(order);
    }
}
