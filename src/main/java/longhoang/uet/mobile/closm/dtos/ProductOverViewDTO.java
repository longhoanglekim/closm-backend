package longhoang.uet.mobile.closm.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOverViewDTO {
    private String category;
    private int quantity;
    private List<VariantOverviewDTO> variants;
}
