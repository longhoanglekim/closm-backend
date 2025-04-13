package longhoang.uet.mobile.closm.controllers;

import longhoang.uet.mobile.closm.dtos.response.Location;
import longhoang.uet.mobile.closm.models.Product;
import longhoang.uet.mobile.closm.models.ProductVariant;
import longhoang.uet.mobile.closm.repositories.ProductRepository;
import longhoang.uet.mobile.closm.utils.EvnLoader;
import longhoang.uet.mobile.closm.utils.LocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/location")
    public ResponseEntity<?> findLocation() {
        try {
            return ResponseEntity.ok().body(LocationUtil.searchLocation("48 ngõ 50 Đình Thôn, Hà Nội"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/calculateDistance")
    public ResponseEntity<?> calculateDistance() {
        try {
            Location src = LocationUtil.searchLocation(EvnLoader.getShopLocation());
            Location dst = LocationUtil.searchLocation("214 Nguyễn Xiển, Hà Nội");
            return ResponseEntity.ok().body(LocationUtil.calculateDistanceAndDuration(src, dst));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
