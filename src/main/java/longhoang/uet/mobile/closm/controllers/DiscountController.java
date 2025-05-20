package longhoang.uet.mobile.closm.controllers;

import longhoang.uet.mobile.closm.models.Discount;
import longhoang.uet.mobile.closm.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discounts")
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    @GetMapping("/validateList")
    public ResponseEntity<?> getValidateDiscounts() {
        try {
            return ResponseEntity.ok(discountService.findValidateDiscounts());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all-discounts")
    public ResponseEntity<?> getAllDiscounts() {
        try {
            return ResponseEntity.ok(discountService.findAllDiscounts());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findDiscountById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(discountService.findDiscountById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create-discount")
    public ResponseEntity<?> createDiscount(@RequestBody Discount discount) {
        try {
            return ResponseEntity.ok(discountService.createDiscount(discount));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/update-discount/{id}")
    public ResponseEntity<?> createDiscount(@RequestBody Discount discount, @PathVariable long id) {
        try {
            return ResponseEntity.ok(discountService.updateDiscount(id, discount));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete-discount/{id}")
    public ResponseEntity<?> deleteDiscount(@PathVariable long id) {
        try {
            return ResponseEntity.ok(discountService.deleteDiscount(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
