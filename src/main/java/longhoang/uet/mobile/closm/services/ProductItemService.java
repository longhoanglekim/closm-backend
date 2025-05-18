package longhoang.uet.mobile.closm.services;

import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.response.ItemGroupByTag;
import longhoang.uet.mobile.closm.dtos.response.TopTaggedItemByCategory;
import longhoang.uet.mobile.closm.dtos.response.VariantGroupDTO;
import longhoang.uet.mobile.closm.mappers.ProductItemMapper;
import longhoang.uet.mobile.closm.models.BaseProduct;
import longhoang.uet.mobile.closm.models.ProductItem;
import longhoang.uet.mobile.closm.repositories.BaseProductRepository;
import longhoang.uet.mobile.closm.repositories.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductItemService {
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private BaseProductRepository baseProductRepository;

    public VariantGroupDTO findByProductName(String Name) {
        List<ProductItem> ProductItems = productItemRepository.findDistinctByTag(Name);
        return ProductItemMapper.mapToVariantFullDTO(ProductItems);
    }

    public List<ItemGroupByTag> getGroupTopItemByTag() throws Exception {
        List<ItemGroupByTag> ans = new ArrayList<>();
        for (long id : baseProductRepository.findAllProductCategoriesId()) {
            log.debug(String.valueOf(id));
            List<TopTaggedItemByCategory> foundItems = productItemRepository.getTopProductByTagGroupedByBaseProduct(id);
            ItemGroupByTag group = new ItemGroupByTag();
            if (!foundItems.isEmpty()) {
                group.setCategory(baseProductRepository.getCategoryNameById(id));
                group.setItemList(foundItems);
                ans.add(group);
            }

        }
        return ans;
    }
}
