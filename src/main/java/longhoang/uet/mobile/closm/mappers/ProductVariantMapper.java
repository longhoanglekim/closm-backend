package longhoang.uet.mobile.closm.mappers;


import longhoang.uet.mobile.closm.dtos.*;
import longhoang.uet.mobile.closm.models.ProductVariant;

import java.util.List;

public class ProductVariantMapper {
    public static VariantDetailsDTO mapToVariantDetailsDTO(ProductVariant productVariant) {
        VariantDetailsDTO variantDetailsDTO = new VariantDetailsDTO();
        variantDetailsDTO.setId(productVariant.getId());
        variantDetailsDTO.setTag(productVariant.getTag());
        variantDetailsDTO.setDescription(productVariant.getDescription());
        variantDetailsDTO.setColor(productVariant.getColor());
        variantDetailsDTO.setPrice(productVariant.getPrice().doubleValue());
        variantDetailsDTO.setSize(productVariant.getSize());
        variantDetailsDTO.setQuantity(productVariant.getQuantity());
        variantDetailsDTO.setImageUrl(productVariant.getImageUrl());
        return variantDetailsDTO;
    }

    public static VariantOverviewDTO mapToVariantOverviewDTO(ProductVariant productVariant) {
        VariantOverviewDTO variantOverviewDTO = new VariantOverviewDTO();
        variantOverviewDTO.setId(productVariant.getId());
        variantOverviewDTO.setName(productVariant.getTag());
        variantOverviewDTO.setImageUrl(productVariant.getImageUrl());
        variantOverviewDTO.setQuantity(productVariant.getQuantity());
        return variantOverviewDTO;
    }

    public static VariantInfo mapToVariantInfoDTO(ProductVariant productVariant) {
        VariantInfo variantInfoDTO = new VariantInfo();
        variantInfoDTO.setDescription(productVariant.getDescription());
        variantInfoDTO.setSize(productVariant.getSize());
        variantInfoDTO.setPrice(productVariant.getPrice().doubleValue());
        variantInfoDTO.setQuantity(productVariant.getQuantity());
        variantInfoDTO.setImageUrl(productVariant.getImageUrl());
        variantInfoDTO.setColor(productVariant.getColor());
        return variantInfoDTO;
    }

    public static VariantFullDTO mapToVariantFullDTO(List<ProductVariant> productVariant) {
        VariantFullDTO variantFullDTO = new VariantFullDTO();
        variantFullDTO.setName(productVariant.get(0).getTag());
        for (ProductVariant variant : productVariant) {
            variantFullDTO.getVariantList().add(mapToVariantInfoDTO(variant));
        }
        return variantFullDTO;
    }

    public static VariantDistinctByTagDTO mapToVariantDistinctByTagDTO(List<ProductVariant> productVariant) {
        VariantDistinctByTagDTO dto = new VariantDistinctByTagDTO();
        dto.setId(productVariant.get(0).getId());
        dto.setTag(productVariant.get(0).getTag());
        dto.setImgUrl(productVariant.get(0).getImageUrl());
        double maxPrice = 0;
        double minPrice = productVariant.get(0).getPrice().doubleValue();
        int totalVariants = 0;
        for (ProductVariant variant : productVariant) {
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
}
