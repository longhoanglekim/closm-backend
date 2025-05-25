package longhoang.uet.mobile.closm.services;

import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.mappers.ProductItemInfo;
import longhoang.uet.mobile.closm.dtos.response.itemDTO.ItemGroupByTag;
import longhoang.uet.mobile.closm.dtos.response.itemDTO.TopTaggedItemByCategory;
import longhoang.uet.mobile.closm.dtos.response.itemDTO.VariantGroupDTO;
import longhoang.uet.mobile.closm.mappers.ProductItemMapper;
import longhoang.uet.mobile.closm.models.ProductItem;
import longhoang.uet.mobile.closm.repositories.BaseProductRepository;
import longhoang.uet.mobile.closm.repositories.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductItemService {
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private BaseProductRepository baseProductRepository;

    public Optional<ProductItem> findProductItemById(Long id) {
        return productItemRepository.findById(id);
    }

    public VariantGroupDTO findByProductName(String Name) {
        List<ProductItem> ProductItems = productItemRepository.findDistinctByTag(Name);
        return ProductItemMapper.mapToVariantFullDTO(ProductItems);
    }

    public List<ItemGroupByTag> getGroupTopItemByTag() throws Exception {
        List<ItemGroupByTag> ans = new ArrayList<>();
        for (long id : baseProductRepository.findAllProductCategoriesId()) {
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

    public long createProductItem(ProductItemInfo productItemInfo) {
        ProductItem productItem = ProductItemMapper.mapToProductItem(productItemInfo, baseProductRepository);
        productItemRepository.save(productItem);
        return productItem.getId();
    }

    public long updateProductItem(ProductItemInfo productItemInfo, long id) throws Exception {
        ProductItem productItem =  productItemRepository.findById(id).orElse(null);
        if (productItem != null) {
            productItem.setBaseProduct(baseProductRepository.findByCategory(productItemInfo.getCategory()).orElse(null));
            productItem.setDescription(productItemInfo.getDescription());
            productItem.setTag(productItemInfo.getTag());
            productItem.setSize(productItemInfo.getSize());
            productItem.setPrice(productItemInfo.getPrice());
            productItem.setColor(productItemInfo.getColor());
            productItem.setImageUrl(productItemInfo.getImageUrl());
            productItem.setQuantity(productItemInfo.getQuantity());
            productItemRepository.save(productItem);
            return productItem.getId();
        }
        throw new Exception("Item not found");
    }

    public long deleteProductItem(long id) {
        productItemRepository.deleteById(id);
        return id;
    }
}
