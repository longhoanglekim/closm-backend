package longhoang.uet.mobile.closm.dtos.response.itemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemGroupByTag {
    private String category;
    List<TopTaggedItemByCategory> itemList;
}
