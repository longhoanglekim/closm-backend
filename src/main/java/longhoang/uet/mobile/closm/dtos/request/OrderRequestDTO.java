package longhoang.uet.mobile.closm.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private Map<Long, Integer> itemIds;
    private List<Long> discountIds;

    private String address;

}
