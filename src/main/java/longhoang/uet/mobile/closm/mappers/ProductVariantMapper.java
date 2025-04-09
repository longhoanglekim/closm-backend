package longhoang.uet.mobile.closm.mappers;


import longhoang.uet.mobile.closm.dtos.VariantDetailsDTO;
import longhoang.uet.mobile.closm.dtos.VariantFullDTO;
import longhoang.uet.mobile.closm.dtos.VariantInfo;
import longhoang.uet.mobile.closm.dtos.VariantOverviewDTO;
import longhoang.uet.mobile.closm.models.ProductVariant;

import java.util.List;

public class ProductVariantMapper {
    public static VariantDetailsDTO mapToVariantDetailsDTO(ProductVariant productVariant) {
        VariantDetailsDTO variantDetailsDTO = new VariantDetailsDTO();
        variantDetailsDTO.setId(productVariant.getId());
        variantDetailsDTO.setName(productVariant.getName());
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
        variantOverviewDTO.setName(productVariant.getName());
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
        variantFullDTO.setName(productVariant.get(0).getName());
        for (ProductVariant variant : productVariant) {
            variantFullDTO.getVariantList().add(mapToVariantInfoDTO(variant));
        }
        return variantFullDTO;
    }
}
