package longhoang.uet.mobile.closm.services;

import longhoang.uet.mobile.closm.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;
}
