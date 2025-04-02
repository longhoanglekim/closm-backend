package longhoang.uet.mobile.closm.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VariantDetailsDTO {

    private Long id;
    private String name;
    private int quantity;
    private String imageUrl;
    private String size;
    private String color;
    private String description;
    private double price;
}

