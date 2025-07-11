package longhoang.uet.mobile.closm.mappers;


import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.mappers.ProductItemInfo;
import longhoang.uet.mobile.closm.dtos.mappers.ItemInfo;
import longhoang.uet.mobile.closm.dtos.response.itemDTO.ItemDetailsDTO;
import longhoang.uet.mobile.closm.dtos.response.itemDTO.ItemOverviewDTO;
import longhoang.uet.mobile.closm.dtos.response.itemDTO.TaggedVariantOverviewDTO;
import longhoang.uet.mobile.closm.dtos.response.itemDTO.VariantGroupDTO;
import longhoang.uet.mobile.closm.models.ProductItem;
import longhoang.uet.mobile.closm.repositories.BaseProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class ProductItemMapper {
    public static ItemDetailsDTO mapToItemDetailsDTO(ProductItem ProductItem) {
        ItemDetailsDTO variantDetailsDTO = new ItemDetailsDTO();
        variantDetailsDTO.setId(ProductItem.getId());
        variantDetailsDTO.setTag(ProductItem.getTag());
        variantDetailsDTO.setDescription(ProductItem.getDescription());
        variantDetailsDTO.setColor(ProductItem.getColor());
        variantDetailsDTO.setPrice(ProductItem.getPrice().doubleValue());
        variantDetailsDTO.setSize(ProductItem.getSize());
        variantDetailsDTO.setQuantity(ProductItem.getQuantity());
        variantDetailsDTO.setImageUrl(ProductItem.getImageUrl());
        return variantDetailsDTO;
    }

    public static ItemOverviewDTO mapToItemOverviewDTO(ProductItem ProductItem) {
        ItemOverviewDTO variantOverviewDTO = new ItemOverviewDTO();
        variantOverviewDTO.setId(ProductItem.getId());
        variantOverviewDTO.setTag(ProductItem.getTag());
        variantOverviewDTO.setImageUrl(ProductItem.getImageUrl());
        variantOverviewDTO.setQuantity(ProductItem.getQuantity());
        return variantOverviewDTO;
    }

    public static ItemInfo mapToVariantInfoDTO(ProductItem ProductItem) {
        ItemInfo variantInfoDTO = new ItemInfo();
        variantInfoDTO.setId(ProductItem.getId());
        variantInfoDTO.setDescription(ProductItem.getDescription());
        variantInfoDTO.setSize(ProductItem.getSize());
        variantInfoDTO.setPrice(ProductItem.getPrice().doubleValue());
        variantInfoDTO.setQuantity(ProductItem.getQuantity());
        variantInfoDTO.setImageUrl(ProductItem.getImageUrl());
        variantInfoDTO.setColor(ProductItem.getColor());
        return variantInfoDTO;
    }

    public static VariantGroupDTO mapToVariantFullDTO(List<ProductItem> ProductItem) {
        VariantGroupDTO variantFullDTO = new VariantGroupDTO();
        variantFullDTO.setName(ProductItem.get(0).getTag());
        for (ProductItem variant : ProductItem) {
            variantFullDTO.getVariantList().add(mapToVariantInfoDTO(variant));
        }
        return variantFullDTO;
    }

    public static TaggedVariantOverviewDTO mapToVariantDistinctByTagDTO(List<ProductItem> ProductItem) {
        TaggedVariantOverviewDTO dto = new TaggedVariantOverviewDTO();
        dto.setId(ProductItem.get(0).getId());
        dto.setTag(ProductItem.get(0).getTag());
        dto.setImgUrl(ProductItem.get(0).getImageUrl());
        double maxPrice = 0;
        double minPrice = ProductItem.get(0).getPrice().doubleValue();
        int totalVariants = 0;
        for (ProductItem variant : ProductItem) {
            if (variant.getPrice().doubleValue() > maxPrice) {
                maxPrice = variant.getPrice().doubleValue();
            }
            if (variant.getPrice().doubleValue() < minPrice) {
                minPrice = variant.getPrice().doubleValue();
            }
            totalVariants += variant.getQuantity();
        }
        dto.setMaxPrice(maxPrice);
        dto.setMinPrice(minPrice);
        dto.setQuantity(totalVariants);
        return dto;
    }

    public static ProductItemInfo mapToProductItemInfo(ProductItem productItem) {
        log.debug("mapToProductItemInfo");
        ProductItemInfo productItemInfo = new ProductItemInfo();
        productItemInfo.setCategory(productItem.getBaseProduct().getCategory());
        productItemInfo.setId(productItem.getId());
        productItemInfo.setPrice(productItem.getPrice());
        productItemInfo.setImageUrl(productItem.getImageUrl());
        productItemInfo.setTag(productItem.getTag());
        productItemInfo.setSize(productItem.getSize());
        productItemInfo.setColor(productItem.getColor());
        productItemInfo.setDescription(productItem.getDescription());
        productItemInfo.setQuantity(productItem.getQuantity());
        return productItemInfo;
    }

    public static ProductItem mapToProductItem(ProductItemInfo productItemInfo, BaseProductRepository repository) {
        ProductItem productItem = new ProductItem();
        productItem.setBaseProduct(repository.findByCategory(productItemInfo.getCategory()).orElse(null));
        productItem.setDescription(productItemInfo.getDescription());
        productItem.setColor(productItemInfo.getColor());
        productItem.setQuantity(productItemInfo.getQuantity());
        productItem.setImageUrl(productItemInfo.getImageUrl());
        productItem.setSize(productItemInfo.getSize());
        productItem.setPrice(productItemInfo.getPrice());
        productItem.setTag(productItemInfo.getTag());
        return productItem;
    }
}
