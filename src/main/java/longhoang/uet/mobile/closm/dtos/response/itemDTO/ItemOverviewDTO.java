package longhoang.uet.mobile.closm.dtos.response.itemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOverviewDTO {
    private Long id;
    private String tag;
    private int quantity;
    private String imageUrl;
}
