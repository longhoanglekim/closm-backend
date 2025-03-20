package longhoang.uet.mobile.closm.controllers;


import longhoang.uet.mobile.closm.models.Product;
import longhoang.uet.mobile.closm.models.ProductVariant;
import longhoang.uet.mobile.closm.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return productService.getAllCategories();
    }

    @GetMapping("/variants")
    public List<ProductVariant> getAllVariants(@RequestParam(name = "category") String category) {
        return productService.getAllProductVariantsByCategory(category);
    }
}
