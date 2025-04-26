package longhoang.uet.mobile.closm.mappers;

import longhoang.uet.mobile.closm.dtos.mappers.OrderInfoDTO;
import longhoang.uet.mobile.closm.dtos.mappers.ProductItemInfo;
import longhoang.uet.mobile.closm.models.Order;
import longhoang.uet.mobile.closm.models.OrderItem;
import longhoang.uet.mobile.closm.models.ProductItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        dto.setItemMap(getProductItemList(order.getOrderItems()));
        return dto;
    }

    private static Map<ProductItemInfo, Integer> getProductItemList(List<OrderItem> orderItemList) {
        Map<ProductItemInfo, Integer> itemsMap = new HashMap<>();
        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem item = orderItemList.get(i);
            itemsMap.put(ProductItemMapper.mapToProductItemInfo(item.getProductItem()), item.getQuantity());
        }
        return itemsMap;
    }
}
