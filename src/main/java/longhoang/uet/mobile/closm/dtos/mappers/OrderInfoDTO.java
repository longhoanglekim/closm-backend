package longhoang.uet.mobile.closm.dtos.mappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.dtos.response.orderDTO.OrderItemInfoDTO;
import longhoang.uet.mobile.closm.enums.OrderStatus;
import longhoang.uet.mobile.closm.enums.PaymentMethod;
import longhoang.uet.mobile.closm.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDTO {
    private Long id;
    private String userEmail;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private String orderCode;
    private BigDecimal discountAmount;
    private BigDecimal deliverPayment;
    private BigDecimal finalPrice;

    private PaymentStatus paymentStatus;

    private String paymentMethod;

    private String deliverAddress;
    private LocalDate cancelableDate;
    List<OrderItemInfoDTO> orderItemList = new ArrayList<>();
}
