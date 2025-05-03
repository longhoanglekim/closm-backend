package longhoang.uet.mobile.closm.mappers;

import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.mappers.OrderInfoDTO;

import longhoang.uet.mobile.closm.dtos.response.OrderItemInfoDTO;
import longhoang.uet.mobile.closm.models.Order;
import longhoang.uet.mobile.closm.models.OrderItem;
import longhoang.uet.mobile.closm.models.ProductItem;

import java.util.ArrayList;

import java.util.List;

@Slf4j
public class OrderMapper {
    public static OrderInfoDTO mapToOrderInfoDTO(Order order) {
        OrderInfoDTO dto = new OrderInfoDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setCancelableDate(order.getCancelableDate());
        dto.setUserEmail(order.getUser().getEmail());
        dto.setDeliverPayment(order.getDeliverPayment());
        dto.setDeliverAddress(order.getDeliverAddress());
        dto.setDiscountAmount(order.getDiscountAmount());
        dto.setFinalPrice(order.getFinalPrice());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setOrderItemList(getProductItemList(order.getOrderItems()));
        dto.setOrderCode(order.getOrderCode());
        return dto;
    }

    private static List<OrderItemInfoDTO> getProductItemList(List<OrderItem> orderItemList) {
        List<OrderItemInfoDTO> orderItemInfoList = new ArrayList<>();
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem item = orderItemList.get(i);
            orderItemInfoList.add(OrderMapper.mapToOrderItemInfo(item.getProductItem(), item.getQuantity()));
        }
        return orderItemInfoList;
    }

    public static OrderItemInfoDTO mapToOrderItemInfo(ProductItem productItem, int quantity) {
        OrderItemInfoDTO dto = new OrderItemInfoDTO();
        dto.setId(productItem.getId());
        dto.setId(productItem.getId());
        dto.setImageUrl(productItem.getImageUrl());
        dto.setTag(productItem.getTag());
        dto.setPrice(productItem.getPrice());
        dto.setColor(productItem.getColor());
        dto.setSize(productItem.getSize());
        dto.setDescription(productItem.getDescription());
        dto.setOrderedQuantity(quantity);
        return dto;
    }
}
