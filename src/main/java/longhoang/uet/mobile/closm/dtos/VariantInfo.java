package longhoang.uet.mobile.closm.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantInfo {
    private int quantity;
    private String imageUrl;
    private String size;
    private String color;
    private String description;
    private double price;
}
