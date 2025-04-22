package longhoang.uet.mobile.closm.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}

