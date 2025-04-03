package longhoang.uet.mobile.closm.controllers;

import longhoang.uet.mobile.closm.models.Product;
import longhoang.uet.mobile.closm.models.ProductVariant;
import longhoang.uet.mobile.closm.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/variants")
    public List<ProductVariant> getVariants(@RequestParam(required = false) String category) {
        Optional<Product> product = productRepository.findByCategory(category);
        if (product.isEmpty()) {
            return new ArrayList<>();
        }
        return product.get().getProductVariants();

    }
}
