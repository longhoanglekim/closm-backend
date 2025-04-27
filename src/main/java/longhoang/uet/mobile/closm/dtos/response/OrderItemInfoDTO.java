package longhoang.uet.mobile.closm.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemInfoDTO {
    private Long id;
    private BigDecimal price;
    private String imageUrl;
    private String tag;
    private String size;
    private String color;
    private String description;
    private int orderedQuantity;
}
