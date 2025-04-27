package longhoang.uet.mobile.closm.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDetailsDTO {

    private Long id;
    private String tag;
    private int quantity;
    private String imageUrl;
    private String size;
    private String color;
    private String description;
    private double price;
}

