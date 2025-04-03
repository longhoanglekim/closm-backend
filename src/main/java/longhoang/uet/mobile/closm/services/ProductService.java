package longhoang.uet.mobile.closm.services;

import longhoang.uet.mobile.closm.dtos.ProductDetailsDTO;
import longhoang.uet.mobile.closm.dtos.ProductOverviewDTO;
import longhoang.uet.mobile.closm.dtos.VariantDetailsDTO;
import longhoang.uet.mobile.closm.dtos.VariantOverviewDTO;
import longhoang.uet.mobile.closm.models.Product;
import longhoang.uet.mobile.closm.models.ProductVariant;
import longhoang.uet.mobile.closm.repositories.ProductRepository;
import longhoang.uet.mobile.closm.repositories.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return productRepository.findAllProductCategories();
    }

    public List<ProductVariant> getAllProductVariantsByCategory(String category) {
        Optional<Product> product = productRepository.findByCategory(category);

        return product.map(value -> {
            List<ProductVariant> variants = productVariantRepository.findAllByProductId(value.getId());

            variants.forEach(variant -> {
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
        Product product = productOpt.get();

        List<ProductVariant> variants = product.getProductVariants().stream().limit(4).toList();
        if (variants.isEmpty()) {
            return Collections.emptyList();  // Không có biến thể => trả về danh sách rỗng
        }

        List<VariantOverviewDTO> dtos = new ArrayList<>();
        for (ProductVariant variant : variants) {
            VariantOverviewDTO dto = new VariantOverviewDTO();
            dto.setId(variant.getId());
            dto.setImageUrl(variant.getImageUrl());
            dto.setQuantity(variant.getQuantity());
            dtos.add(dto);
        }
        return dtos;
    }

    public ProductOverviewDTO getProductOverview(String category) {
        ProductOverviewDTO dto = new ProductOverviewDTO();
        dto.setCategory(category);
        List<VariantOverviewDTO> variants = getLimitedVariantsByCategory(category);
        int totalQuantity = 0;
        for (VariantOverviewDTO variant : variants) {
            totalQuantity += variant.getQuantity();
        }
        dto.setQuantity(totalQuantity);
        dto.setVariants(variants);
        return dto;
    }

    public ProductDetailsDTO getProductDetails(String category) {
        Optional<Product> productOpt = productRepository.findByCategory(category);
        List<ProductVariant> variants = productOpt.get().getProductVariants();
    
        List<VariantDetailsDTO> variantDTOs = variants.stream()
            .map(variant -> new VariantDetailsDTO(
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
    
        return new ProductDetailsDTO(category, totalQuantity, variantDTOs);
    }
    

    public ProductVariant findProductVariantById(Long id) {
        return productVariantRepository.findById(id).orElse(null);
    }
}

