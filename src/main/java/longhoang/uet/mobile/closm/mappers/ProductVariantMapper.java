package longhoang.uet.mobile.closm.mappers;


import longhoang.uet.mobile.closm.dtos.VariantDetailsDTO;
import longhoang.uet.mobile.closm.dtos.VariantOverviewDTO;
import longhoang.uet.mobile.closm.models.ProductVariant;

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
}
