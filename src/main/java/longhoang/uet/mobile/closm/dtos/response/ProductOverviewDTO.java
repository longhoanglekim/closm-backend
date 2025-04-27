package longhoang.uet.mobile.closm.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOverviewDTO {
    private String category;
    private int quantity;
    private List<ItemOverviewDTO> variants;

}
