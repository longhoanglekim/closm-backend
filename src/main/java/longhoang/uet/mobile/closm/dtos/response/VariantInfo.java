package longhoang.uet.mobile.closm.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantInfo {
    private Long id;
    private int quantity;
    private String imageUrl;
    private String size;
    private String color;
    private String description;
    private double price;
}
