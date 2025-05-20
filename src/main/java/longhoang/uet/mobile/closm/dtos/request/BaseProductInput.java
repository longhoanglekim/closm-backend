package longhoang.uet.mobile.closm.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseProductInput {
    private String category;
    private String imageUrl;
}
