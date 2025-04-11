package longhoang.uet.mobile.closm.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantDistinctByTagDTO {
    private Long id;
    private String tag;
    private String imgUrl;
    private int quantity;
    private double minPrice;
    private double maxPrice;
}
