package longhoang.uet.mobile.closm.mappers;


import longhoang.uet.mobile.closm.dtos.VariantDetailsDTO;
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
}
