package longhoang.uet.mobile.closm.services;

import longhoang.uet.mobile.closm.dtos.response.DiscountInfoDTO;
import longhoang.uet.mobile.closm.mappers.DiscountMapper;
import longhoang.uet.mobile.closm.models.Discount;
import longhoang.uet.mobile.closm.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    public List<Discount> findValidateDiscounts() throws Exception {
        return discountRepository.findValidDiscounts(LocalDate.now());
    }

}
