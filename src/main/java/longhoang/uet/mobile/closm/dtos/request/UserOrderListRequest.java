package longhoang.uet.mobile.closm.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import longhoang.uet.mobile.closm.enums.OrderStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderListRequest {
    OrderStatus orderStatus;
    String userEmail;
}
