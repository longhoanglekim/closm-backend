package longhoang.uet.mobile.closm.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderConfirmationDTO {
    private String userEmail;
    private String address;
    private OrderPriceSummaryDTO orderPriceSummary;
}
