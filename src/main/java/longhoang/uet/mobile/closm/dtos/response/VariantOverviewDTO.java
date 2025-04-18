package longhoang.uet.mobile.closm.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantOverviewDTO {
    private Long id;
    private String name;
    private int quantity;
    private String imageUrl;
}
