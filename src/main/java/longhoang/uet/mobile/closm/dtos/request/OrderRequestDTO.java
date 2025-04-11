package longhoang.uet.mobile.closm.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    private List<Long> itemIds;
    private List<Long> discountIds;

    private String address;

}
