package longhoang.uet.mobile.closm.controllers;

import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.ProductDetailsDTO;
import longhoang.uet.mobile.closm.models.ProductVariant;
import longhoang.uet.mobile.closm.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/variants")
public class ProductVariantController {
    @Autowired
    private ProductService productService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getVariantDetails(@PathVariable long id) {
        ProductVariant productVariant = productService.findProductVariantById(id);
        if (productVariant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productVariant);
    }
}
