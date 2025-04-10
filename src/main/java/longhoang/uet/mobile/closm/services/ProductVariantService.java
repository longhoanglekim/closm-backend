package longhoang.uet.mobile.closm.services;

import longhoang.uet.mobile.closm.dtos.VariantFullDTO;
import longhoang.uet.mobile.closm.mappers.ProductVariantMapper;
import longhoang.uet.mobile.closm.models.ProductVariant;
import longhoang.uet.mobile.closm.repositories.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductVariantService {
    @Autowired
    private ProductVariantRepository productVariantRepository;

    public VariantFullDTO findByProductName(String Name) {
        List<ProductVariant> productVariants = productVariantRepository.findDistinctByTag(Name);
        return ProductVariantMapper.mapToVariantFullDTO(productVariants);
    }
}
