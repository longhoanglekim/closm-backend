package longhoang.uet.mobile.closm.dtos.response.baseProduct;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.dtos.response.itemDTO.TaggedVariantOverviewDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsDTO {
    private String category;
    private int quantity;
    private List<TaggedVariantOverviewDTO> variants;
}
