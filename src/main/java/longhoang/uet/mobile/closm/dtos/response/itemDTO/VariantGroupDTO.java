package longhoang.uet.mobile.closm.dtos.response.itemDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.dtos.mappers.ItemInfo;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantGroupDTO {
    private String name;
    private List<ItemInfo> variantList = new ArrayList<>();
}
