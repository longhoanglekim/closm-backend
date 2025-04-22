package longhoang.uet.mobile.closm.mappers;

import longhoang.uet.mobile.closm.dtos.response.DiscountInfoDTO;
import longhoang.uet.mobile.closm.models.Discount;

public class DiscountMapper {
    public static DiscountInfoDTO mapDiscountInfoDTO(Discount discount) {
        DiscountInfoDTO discountInfoDTO = new DiscountInfoDTO();
        discountInfoDTO.setId(discount.getId());
        discountInfoDTO.setDiscountType(discount.getDiscountType());
        discountInfoDTO.setDescription(discount.getDescription());
        return discountInfoDTO;
    }
}
