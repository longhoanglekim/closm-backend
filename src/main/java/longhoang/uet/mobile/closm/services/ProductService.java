package longhoang.uet.mobile.closm.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import longhoang.uet.mobile.closm.dtos.ProductOverViewDTO;
import longhoang.uet.mobile.closm.dtos.VariantOverviewDTO;
import longhoang.uet.mobile.closm.models.Product;
import longhoang.uet.mobile.closm.models.ProductVariant;
import longhoang.uet.mobile.closm.repositories.ProductRepository;
import longhoang.uet.mobile.closm.repositories.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;

    public List<String> getAllCategories() {
        List<String> res = productRepository.findAllProductCategories();
        res.replaceAll(s -> new String(s.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8)); // Fix lỗi encoding
        res.forEach(System.out::println);
        return res;
    }

    public List<ProductVariant> getAllProductVariantsByCategory(String category) {
        Optional<Product> product = productRepository.findByCategory(category);

        return product.map(value -> {
            List<ProductVariant> variants = productVariantRepository.findAllByProductId(value.getId());

            // Fix lỗi encoding cho từng trường trong ProductVariant
            variants.forEach(variant -> {
//                System.out.println(variant);
                variant.setDescription(new String(variant.getDescription().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                variant.setColor(new String(variant.getColor().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
            });

            return variants;
        }).orElse(Collections.emptyList());
    }

    public List<VariantOverviewDTO> getLimitedVariantsByCategory(String category) {
        Optional<Product> productOpt = productRepository.findByCategory(category);
        if (productOpt.isEmpty()) {
            return Collections.emptyList();  // Trả về danh sách rỗng nếu không tìm thấy sản phẩm
        }

        Long productId = productOpt.get().getId();
        List<ProductVariant> variants = productVariantRepository.findTop4VariantsByProductId(productId);
        if (variants.isEmpty()) {
            return Collections.emptyList();  // Không có biến thể => trả về danh sách rỗng
        }

        List<VariantOverviewDTO> dtos = new ArrayList<>();
        for (ProductVariant variant : variants) {
            productVariantRepository.countProductVariantByProductId(productId);
            VariantOverviewDTO dto = new VariantOverviewDTO();
            dto.setId(variant.getId());
            dto.setName(variant.getName());
            dto.setImageUrl(variant.getImageUrl());
            dto.setQuantity(variant.getQuantity());
            dtos.add(dto);
        }
        return dtos;
    }
    // t thêm ở đây nhé
    public ProductOverViewDTO getProductOverviewDTO(String category) {
        List<ProductVariant> variants = productVariantRepository.findByCategory(category);
    
        List<VariantOverviewDTO> variantDTOs = variants.stream()
            .map(variant -> new VariantOverviewDTO(
                variant.getId(),
                variant.getName(),
                variant.getQuantity(),
                variant.getImageUrl(),
                variant.getSize(),
                variant.getColor(),
                variant.getDescription(),
                variant.getPrice() != null ? variant.getPrice().doubleValue() : 0
            )).toList();
    
        int totalQuantity = variants.stream().mapToInt(ProductVariant::getQuantity).sum();
    
        return new ProductOverViewDTO(category, totalQuantity, variantDTOs);
    }
    

    public ProductVariant findProductVariantById(Long id) {
        return productVariantRepository.findById(id).orElse(null);
    }
}

