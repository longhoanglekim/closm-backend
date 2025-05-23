package longhoang.uet.mobile.closm.services;

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

    public List<Discount> findAllDiscounts() throws Exception {
        return discountRepository.findAll();
    }

    public Discount findDiscountById(long id) throws Exception {
        return discountRepository.findById(id).orElse(null);
    }

    public long createDiscount(Discount discountInfo) throws Exception {
        Discount discount = discountRepository.save(discountInfo);
        return discount.getId();
    }


    public long updateDiscount(long id,Discount discountInfo) throws Exception {
        Discount discount = discountRepository.findById(id).orElse(null);
        if (discount != null) {
            discount.setDiscountType(discountInfo.getDiscountType());
            discount.setDiscountPercentage(discountInfo.getDiscountPercentage());
            discount.setDescription(discountInfo.getDescription());
            discount.setStartDate(discountInfo.getStartDate());
            discount.setEndDate(discountInfo.getEndDate());
            return discount.getId();
        }
        throw new Exception("Discount not found");
    }

    public long deleteDiscount(long id) throws Exception {
        Discount discount = discountRepository.findById(id).orElse(null);
        if (discount != null) {
            discountRepository.delete(discount);
            return discount.getId();
        }
        throw new Exception("Discount not found");
    }
}
