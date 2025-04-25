package longhoang.uet.mobile.closm.dtos.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.enums.OrderStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderConfirmResponse {
    private Long orderId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String message;
}
