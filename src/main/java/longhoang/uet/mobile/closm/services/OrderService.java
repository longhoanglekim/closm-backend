package longhoang.uet.mobile.closm.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.mappers.OrderInfoDTO;
import longhoang.uet.mobile.closm.dtos.request.OrderConfirmationDTO;

import longhoang.uet.mobile.closm.dtos.response.OrderListByStatus;
import longhoang.uet.mobile.closm.dtos.response.ProductOverviewDTO;
import longhoang.uet.mobile.closm.enums.OrderStatus;
import longhoang.uet.mobile.closm.enums.PaymentMethod;
import longhoang.uet.mobile.closm.enums.PaymentStatus;
import longhoang.uet.mobile.closm.mappers.OrderMapper;
import longhoang.uet.mobile.closm.models.*;
import longhoang.uet.mobile.closm.repositories.DiscountRepository;
import longhoang.uet.mobile.closm.repositories.OrderRepository;
import longhoang.uet.mobile.closm.repositories.ProductItemRepository;

import longhoang.uet.mobile.closm.utils.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    ProductItemRepository ProductItemRepository;
    @Autowired
    DiscountRepository discountRepository;
    @Autowired
    UserService userService;
    @Autowired
    private ProductItemRepository productItemRepository;


    @Transactional
    public Order confirmOrder(OrderConfirmationDTO orderConfirmationDTO) throws Exception {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(userService.getUserByEmail(orderConfirmationDTO.getUserEmail()).orElseThrow(() -> new Exception("User not found"))); // Handle potential null user
        order.setDeliverAddress(orderConfirmationDTO.getAddress());
        order.setDeliverPayment(orderConfirmationDTO.getSummaryOrderPrice().getDeliveryAmount());
        order.setDiscountAmount(orderConfirmationDTO.getSummaryOrderPrice().getDiscountAmount());
        order.setFinalPrice(orderConfirmationDTO.getSummaryOrderPrice().getFinalPrice());
        order.setOrderCode(CodeGenerator.generateOrderCode());
        order.setCancelableDate(LocalDate.now().minusDays(10));
        if (orderConfirmationDTO.getPaymentMethod() == null) {
            order.setPaymentMethod(PaymentMethod.CASH);
        } else order.setPaymentStatus(orderConfirmationDTO.getPaymentStatus());
        if (orderConfirmationDTO.getPaymentStatus() == null) {
            order.setPaymentStatus(PaymentStatus.UNPAID);
        } else order.setPaymentStatus(orderConfirmationDTO.getPaymentStatus());
        Order savedOrder = orderRepository.save(order);

        for (Long id : orderConfirmationDTO.getItemIdsMap().keySet()) {
            OrderItem orderItem = new OrderItem();
            int quantityOrdered = orderConfirmationDTO.getItemIdsMap().get(id);
            ProductItem productItem = ProductItemRepository.findById(id).orElseThrow(() -> new Exception("Product variant not found with ID: " + id)); // Handle potential null product variant
            productItem.setQuantity(productItem.getQuantity() - quantityOrdered);
            orderItem.setProductItem(productItem);
            orderItem.setOrder(savedOrder);
            orderItem.setQuantity(quantityOrdered);
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
        order.setOrderStatus(OrderStatus.CANCELLED);
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            ProductItem productItem = orderItem.getProductItem();
            productItem.setQuantity(productItem.getQuantity() + orderItem.getQuantity());
            productItemRepository.save(productItem);
        }
        return orderRepository.save(order);
    }

    /**
     *
     * @param orderStatus
     * @param userEmail
     * @return
     * @throws Exception
     */
    public OrderListByStatus getUserOrderListByOrderStatus(OrderStatus orderStatus, String userEmail) throws Exception {
        User user = userService.getUserByEmail(userEmail).orElseThrow(() -> new Exception("User not found"));
        List<Order> orderList = orderRepository.findByOrderStatusAndUser(orderStatus, user);
        List<OrderInfoDTO> orderInfoDTOList = new ArrayList<>();
        orderList.forEach(order -> {
            orderInfoDTOList.add(OrderMapper.mapToOrderInfoDTO(order));
        });
        OrderListByStatus orderListByStatus = new OrderListByStatus();
        orderListByStatus.setStatus(orderStatus);
        orderListByStatus.setOrders(orderInfoDTOList);
        return orderListByStatus;
    }
}
