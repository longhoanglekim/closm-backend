package longhoang.uet.mobile.closm.dtos.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.enums.OrderStatus;
import longhoang.uet.mobile.closm.enums.PaymentMethod;
import longhoang.uet.mobile.closm.enums.PaymentStatus;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderConfirmationDTO {
    private String userEmail;
    private String address;
    private Map<Long, Integer> itemIdsMap;
    private List<Long> discountIds;
    private SummaryOrderPrice summaryOrderPrice;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

}

