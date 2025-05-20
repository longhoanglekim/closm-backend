package longhoang.uet.mobile.closm.controllers;


import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.request.BaseProductInput;
import longhoang.uet.mobile.closm.dtos.response.ProductDetailsDTO;
import longhoang.uet.mobile.closm.dtos.mappers.ProductItemInfo;
import longhoang.uet.mobile.closm.dtos.response.ProductOverviewDTO;
import longhoang.uet.mobile.closm.repositories.BaseProductRepository;
import longhoang.uet.mobile.closm.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private BaseProductRepository baseProductRepository;

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return productService.getAllCategories();
    }

    @GetMapping("/items")
    public List<ProductItemInfo> getAllItemsByCategory(@RequestParam(name = "category") String category) {
        return productService.getAllProductItemsByCategory(category);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create-base-product")
    public ResponseEntity<?> createBaseProduct(@RequestBody BaseProductInput baseProductInput) {
        try  {
            return ResponseEntity.ok(productService.createBaseProduct(baseProductInput));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update-base-product/{id}")
    public ResponseEntity<?> updateBaseProduct(@RequestBody BaseProductInput baseProductInput, @PathVariable long id) {
        try  {
            return ResponseEntity.ok(productService.updateBaseProduct(id, baseProductInput));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete-base-product/{id}")
    public ResponseEntity<?> createBaseProduct(@PathVariable long id) {
        try  {
            return ResponseEntity.ok(productService.deleteBaseProduct(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
