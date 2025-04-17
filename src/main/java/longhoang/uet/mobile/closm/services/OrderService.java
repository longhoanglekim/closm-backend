package longhoang.uet.mobile.closm.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.dtos.request.OrderConfirmationDTO;

import longhoang.uet.mobile.closm.models.*;
import longhoang.uet.mobile.closm.repositories.DiscountRepository;
import longhoang.uet.mobile.closm.repositories.OrderRepository;
import longhoang.uet.mobile.closm.repositories.ProductVariantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

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


    public Order confirmOrder(OrderConfirmationDTO orderConfirmationDTO) throws Exception {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(userService.getUser(orderConfirmationDTO.getUserEmail()).get());
        order.setShippingAddress(orderConfirmationDTO.getAddress());
        order.setDeliverPayment(orderConfirmationDTO.getSummaryOrderPrice().getDeliveryAmount());
        order.setDiscountAmount(orderConfirmationDTO.getSummaryOrderPrice().getDiscountAmount());
        order.setItemsTotalPrice(orderConfirmationDTO.getSummaryOrderPrice().getItemsTotalPrice());
        order.setFinalPrice(orderConfirmationDTO.getSummaryOrderPrice().getFinalPrice());
        for (Long id : orderConfirmationDTO.getItemIdsMap().keySet()) {
            OrderVariant orderVariant = new OrderVariant();
            ProductVariant productVariant = productVariantRepository.findById(id).get();
            orderVariant.setProductVariant(productVariant);
            orderVariant.setOrder(order);
            productVariant.getOrderVariants().add(orderVariant);
            productVariantRepository.save(productVariant);
            order.getOrderVariants().add(orderVariant);
        }
        return orderRepository.save(order);
    }
}
