package longhoang.uet.mobile.closm.controllers;

import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.mappers.ProductItemInfo;
import longhoang.uet.mobile.closm.mappers.ProductItemMapper;
import longhoang.uet.mobile.closm.models.ProductItem;
import longhoang.uet.mobile.closm.services.ProductService;
import longhoang.uet.mobile.closm.services.ProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Autowired
    private ProductItemService productItemService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getVariantDetails(@PathVariable long id) {
        Optional<ProductItem> ProductItem = productItemService.findProductItemById(id);
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

    @GetMapping("/top-items")
    public ResponseEntity<?> getTopItems() {
        try {
            return ResponseEntity.ok().body(productItemService.getGroupTopItemByTag());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create-item")
    public ResponseEntity<?> createItem(@RequestBody ProductItemInfo productItemInfo) {
        try {
            return ResponseEntity.ok().body(productItemService.createProductItem(productItemInfo));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update-item/{id}")
    public ResponseEntity<?> updateItem(@RequestBody ProductItemInfo productItemInfo, long id) {
        try {
            return ResponseEntity.ok().body(productItemService.updateProductItem(productItemInfo, id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete-item/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(productItemService.deleteProductItem(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
