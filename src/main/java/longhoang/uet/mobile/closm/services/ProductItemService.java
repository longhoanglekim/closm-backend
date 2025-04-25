package longhoang.uet.mobile.closm.services;

import longhoang.uet.mobile.closm.dtos.response.VariantGroupDTO;
import longhoang.uet.mobile.closm.mappers.ProductItemMapper;
import longhoang.uet.mobile.closm.models.ProductItem;
import longhoang.uet.mobile.closm.repositories.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductItemService {
    @Autowired
    private ProductItemRepository ProductItemRepository;

    public VariantGroupDTO findByProductName(String Name) {
        List<ProductItem> ProductItems = ProductItemRepository.findDistinctByTag(Name);
        return ProductItemMapper.mapToVariantFullDTO(ProductItems);
    }
}
