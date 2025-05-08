package longhoang.uet.mobile.closm.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.dtos.mappers.ProductItemInfo;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemGroupByTag {
    private String tag;
    List<ProductItemInfo> itemList;
}
