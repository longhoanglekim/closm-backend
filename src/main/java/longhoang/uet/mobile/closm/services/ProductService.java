package longhoang.uet.mobile.closm.services;

import longhoang.uet.mobile.closm.models.Product;
import longhoang.uet.mobile.closm.models.ProductVariant;
import longhoang.uet.mobile.closm.repositories.ProductRepository;
import longhoang.uet.mobile.closm.repositories.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;

    public List<String> getAllCategories() {
        List<String> res = productRepository.findAllProductCategories();
        res.replaceAll(s -> new String(s.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8)); // Fix lỗi encoding
        res.forEach(System.out::println);
        return res;
    }

    public List<ProductVariant> getAllProductVariantsByCategory(String category) {
        Optional<Product> product = productRepository.findByCategory(category);

        return product.map(value -> {
            List<ProductVariant> variants = productVariantRepository.findAllByProductId(value.getId());

            // Fix lỗi encoding cho từng trường trong ProductVariant
            variants.forEach(variant -> {
//                System.out.println(variant);
                variant.setDescription(new String(variant.getDescription().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                variant.setColor(new String(variant.getColor().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
            });

            return variants;
        }).orElse(Collections.emptyList());
    }



}
