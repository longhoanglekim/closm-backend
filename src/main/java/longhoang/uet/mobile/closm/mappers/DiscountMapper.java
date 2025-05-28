package longhoang.uet.mobile.closm.mappers;

import longhoang.uet.mobile.closm.dtos.mappers.DiscountInfoDTO;
import longhoang.uet.mobile.closm.models.Discount;

public class DiscountMapper {
    public static DiscountInfoDTO mapDiscountInfoDTO(Discount discount) {
        DiscountInfoDTO discountInfoDTO = new DiscountInfoDTO();
        discountInfoDTO.setId(discount.getId());
        discountInfoDTO.setDiscountType(discount.getDiscountType());
        discountInfoDTO.setName(discount.getName());
        discountInfoDTO.setDiscountPercentage(discount.getDiscountPercentage());
        discountInfoDTO.setDiscountType(discount.getDiscountType());
        discountInfoDTO.setImageUrl(discount.getImageUrl());
        discountInfoDTO.setStartDate(discount.getStartDate());
        discountInfoDTO.setEndDate(discount.getEndDate());
        return discountInfoDTO;
    }
}
