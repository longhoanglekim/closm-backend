package longhoang.uet.mobile.closm.dtos.response.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.dtos.mappers.OrderInfoDTO;
import longhoang.uet.mobile.closm.enums.OrderStatus;


import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListByStatus {
    private OrderStatus status;
    private List<OrderInfoDTO> orders = new ArrayList<>();
}
