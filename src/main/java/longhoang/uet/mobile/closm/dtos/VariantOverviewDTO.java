package longhoang.uet.mobile.closm.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VariantOverviewDTO {

    private Long id;
    private String name;
    private int quantity;
    private String imageUrl;
}
