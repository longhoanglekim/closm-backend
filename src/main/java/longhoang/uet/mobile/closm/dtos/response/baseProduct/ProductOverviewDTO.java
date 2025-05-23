package longhoang.uet.mobile.closm.dtos.response.baseProduct;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.dtos.response.itemDTO.ItemOverviewDTO;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOverviewDTO {
    private String category;
    private int quantity;
    private List<ItemOverviewDTO> variants;

}
