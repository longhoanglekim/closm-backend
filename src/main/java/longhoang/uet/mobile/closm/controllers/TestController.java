package longhoang.uet.mobile.closm.controllers;

import longhoang.uet.mobile.closm.models.BaseProduct;
import longhoang.uet.mobile.closm.models.ProductItem;
import longhoang.uet.mobile.closm.repositories.BaseProductRepository;
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
    private BaseProductRepository productRepository;
    @GetMapping("/variants")
    public List<ProductItem> getVariants(@RequestParam(required = false) String category) {
        Optional<BaseProduct> product = productRepository.findByCategory(category);
        if (product.isEmpty()) {
            return new ArrayList<>();
        }
        return product.get().getProductItems();
    }


}
