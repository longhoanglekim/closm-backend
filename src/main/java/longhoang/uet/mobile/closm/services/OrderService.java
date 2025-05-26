package longhoang.uet.mobile.closm.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.mappers.OrderInfoDTO;
import longhoang.uet.mobile.closm.dtos.request.OrderConfirmationDTO;

import longhoang.uet.mobile.closm.dtos.response.orderDTO.OrderItemInfoDTO;
import longhoang.uet.mobile.closm.dtos.response.orderDTO.OrderListByStatus;
import longhoang.uet.mobile.closm.enums.OrderStatus;
import longhoang.uet.mobile.closm.enums.PaymentMethod;
import longhoang.uet.mobile.closm.enums.PaymentStatus;
import longhoang.uet.mobile.closm.mappers.OrderMapper;
import longhoang.uet.mobile.closm.models.*;
import longhoang.uet.mobile.closm.repositories.OrderItemRepository;
import longhoang.uet.mobile.closm.repositories.OrderRepository;
import longhoang.uet.mobile.closm.repositories.PaymentMethodRepository;
import longhoang.uet.mobile.closm.repositories.ProductItemRepository;

import longhoang.uet.mobile.closm.utils.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    UserService userService;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

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
            order.setPaymentMethod(paymentMethodRepository.findById((long) 1).get());
        } else order.setPaymentMethod(paymentMethodRepository.findByMethod(orderConfirmationDTO.getPaymentMethod().toString()));
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

    public OrderInfoDTO getOrderInfo(long orderId) throws Exception {
        Order order = getOrder(orderId);
        return OrderMapper.mapToOrderInfoDTO(order);
    }

    public Order cancelOrder(Order order) throws Exception {
        log.debug("Order being cancelled");
        order.setOrderStatus(OrderStatus.CANCELLED);
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            ProductItem productItem = orderItem.getProductItem();
            productItem.setQuantity(productItem.getQuantity() + orderItem.getQuantity());
            productItemRepository.save(productItem);
        }
        log.debug("Order cancelled");
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

    public List<OrderInfoDTO> getAllOrderInfo() {
        List<Order> orderList = orderRepository.findAll();
        return  orderList.stream().map(OrderMapper::mapToOrderInfoDTO).collect(Collectors.toList());
    }

    public long updateOrder(long id, OrderInfoDTO updateOrderInfo) throws Exception {
        Order order = mapToOrder(updateOrderInfo);
        order.setId(id);
        return orderRepository.save(order).getId();
    }


    private Order mapToOrder(OrderInfoDTO dto) throws Exception {
        Order order = new Order();
        order.setOrderCode(dto.getOrderCode());
        order.setOrderDate(dto.getOrderDate());
        order.setOrderStatus(dto.getOrderStatus());
        order.setCancelableDate(dto.getCancelableDate());
        order.setUser(userService.getUserByEmail(dto.getUserEmail()).orElseThrow(() -> new Exception("User not found")));
        order.setPaymentMethod(paymentMethodRepository.findByMethod(dto.getPaymentMethod().toString()));
        order.setPaymentStatus(dto.getPaymentStatus());
        order.setDeliverAddress(dto.getDeliverAddress());
        order.setDeliverPayment(dto.getDeliverPayment());
        order.setFinalPrice(dto.getFinalPrice());
        order.setDiscountAmount(dto.getDiscountAmount());
        order.setOrderItems(dto.getOrderItemList().stream()
                .map(value -> {
                    try {
                        return mapToOrderItem(value, order);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList()));
        return order;
    }

    private OrderItem mapToOrderItem(OrderItemInfoDTO dto, Order order) throws Exception {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProductItem(productItemRepository.findById(dto.getId()).orElseThrow(() ->  new Exception("Item not found")));
        orderItem.setQuantity(orderItem.getQuantity());
        return orderItem;
    }

    public long deleteOrder(long id) {
        orderRepository.deleteById(id);
        return id;
    }
}
