package longhoang.uet.mobile.closm.controllers;


import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.response.ProductDetailsDTO;
import longhoang.uet.mobile.closm.dtos.response.ProductOverviewDTO;
import longhoang.uet.mobile.closm.models.ProductVariant;
import longhoang.uet.mobile.closm.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/products")
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

    @GetMapping("/overview")
    public ResponseEntity<?> getProductOverview() {
        List<ProductOverviewDTO> shopList = new ArrayList<>();
        List<String> categoryList = productService.getAllCategories();
        for (String category : categoryList) {
//            log.info(category);
            ProductOverviewDTO productOverview = productService.getProductOverview(category);
            if (productOverview != null && !productOverview.getVariants().isEmpty()) {
                shopList.add(productOverview);  // Chỉ thêm vào danh sách nếu có biến thể
            }
        }
        if (shopList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(shopList);
    }

    @GetMapping("/details")
    public ResponseEntity<?> getProductDetails() {
        List<ProductDetailsDTO> shopList = new ArrayList<>();

        List<String> categoryList = productService.getAllCategories();
        for (String category : categoryList) {
            ProductDetailsDTO productDetails = productService.getProductDetails(category);
            if (productDetails != null && !productDetails.getVariants().isEmpty()) {
                shopList.add(productDetails);  // Chỉ thêm vào danh sách nếu có biến thể
            }
        }
        if (shopList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(shopList);
    }


}
