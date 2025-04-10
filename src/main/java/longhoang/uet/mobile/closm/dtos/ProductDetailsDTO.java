package longhoang.uet.mobile.closm.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsDTO {
    private String category;
    private int quantity;
    private List<VariantDistinctByTagDTO> variants;
}
