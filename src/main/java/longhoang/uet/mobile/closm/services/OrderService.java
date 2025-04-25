package longhoang.uet.mobile.closm.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.dtos.request.OrderConfirmationDTO;

import longhoang.uet.mobile.closm.enums.OrderStatus;
import longhoang.uet.mobile.closm.models.*;
import longhoang.uet.mobile.closm.repositories.DiscountRepository;
import longhoang.uet.mobile.closm.repositories.OrderRepository;
import longhoang.uet.mobile.closm.repositories.ProductItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    ProductItemRepository ProductItemRepository;
    @Autowired
    DiscountRepository discountRepository;
    @Autowired
    UserService userService;


    @Transactional
    public Order confirmOrder(OrderConfirmationDTO orderConfirmationDTO) throws Exception {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(userService.getUser(orderConfirmationDTO.getUserEmail()).orElseThrow(() -> new Exception("User not found"))); // Handle potential null user
        order.setDeliverAddress(orderConfirmationDTO.getAddress());
        order.setDeliverPayment(orderConfirmationDTO.getSummaryOrderPrice().getDeliveryAmount());
        order.setDiscountAmount(orderConfirmationDTO.getSummaryOrderPrice().getDiscountAmount());
        order.setItemsTotalPrice(orderConfirmationDTO.getSummaryOrderPrice().getItemsTotalPrice());
        order.setFinalPrice(orderConfirmationDTO.getSummaryOrderPrice().getFinalPrice());
        order.setCancelableDate(LocalDate.now().minusDays(10));
        Order savedOrder = orderRepository.save(order);

        for (Long id : orderConfirmationDTO.getItemIdsMap().keySet()) {
            OrderItem orderItem = new OrderItem();
            ProductItem productItem = ProductItemRepository.findById(id).orElseThrow(() -> new Exception("Product variant not found with ID: " + id)); // Handle potential null product variant

            orderItem.setProductItem(productItem);
            orderItem.setOrder(savedOrder);
            orderItem.setQuantity(orderConfirmationDTO.getItemIdsMap().get(id));
            productItem.getOrderItems().add(orderItem);
            ProductItemRepository.save(productItem);
            savedOrder.getOrderItems().add(orderItem);
        }
        return orderRepository.save(savedOrder);
    }

    public Order getOrder(long orderId) throws Exception {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }
        throw new Exception("Order not found");
    }

    public Order cancelOrder(Order order) throws Exception {
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
