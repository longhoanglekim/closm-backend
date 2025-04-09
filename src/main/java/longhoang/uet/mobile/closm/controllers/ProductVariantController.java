package longhoang.uet.mobile.closm.controllers;

import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.ProductDetailsDTO;
import longhoang.uet.mobile.closm.mappers.ProductVariantMapper;
import longhoang.uet.mobile.closm.models.ProductVariant;
import longhoang.uet.mobile.closm.services.ProductService;
import longhoang.uet.mobile.closm.services.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/variants")
public class ProductVariantController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductVariantService productVariantService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getVariantDetails(@PathVariable long id) {
        ProductVariant productVariant = productService.findProductVariantById(id);
        if (productVariant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ProductVariantMapper.mapToVariantDetailsDTO(productVariant));
    }

    @GetMapping
    public ResponseEntity<?> getVariantsByVariantName(@RequestParam String variantName) {
        if (variantName == null || variantName.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productVariantService.findByProductName(variantName));
    }
}
