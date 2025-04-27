package longhoang.uet.mobile.closm.controllers;

import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.mappers.ProductItemMapper;
import longhoang.uet.mobile.closm.models.ProductItem;
import longhoang.uet.mobile.closm.services.ProductService;
import longhoang.uet.mobile.closm.services.ProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@Slf4j
@RequestMapping("/items")
public class ProductItemController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductItemService ProductItemService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getVariantDetails(@PathVariable long id) {
        Optional<ProductItem> ProductItem = productService.findProductItemById(id);
        if (ProductItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ProductItemMapper.mapToItemDetailsDTO(ProductItem.get()));
    }

    @GetMapping
    public ResponseEntity<?> getVariantsByVariantName(@RequestParam String variantName) {
        if (variantName == null || variantName.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        log.info("Get Variant by Variant Name: {}", variantName);
        return ResponseEntity.ok().body(ProductItemService.findByProductName(variantName));
    }
}
