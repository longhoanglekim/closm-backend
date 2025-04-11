package longhoang.uet.mobile.closm.services;

import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.response.ProductDetailsDTO;
import longhoang.uet.mobile.closm.dtos.response.ProductOverviewDTO;
import longhoang.uet.mobile.closm.dtos.response.VariantDistinctByTagDTO;
import longhoang.uet.mobile.closm.dtos.response.VariantOverviewDTO;
import longhoang.uet.mobile.closm.mappers.ProductVariantMapper;
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

@Slf4j
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
            VariantOverviewDTO dto = ProductVariantMapper.mapToVariantOverviewDTO(variant);
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

        if (productOpt.isEmpty()) {
            return null;
        }
        List<String> variantTags = productVariantRepository.findAllDistinctTagByProductId(productOpt.get().getId());
        List<VariantDistinctByTagDTO> variantDistinctByTagDTOS = new ArrayList<>();
        int totalQuantity = 0;
        for (String tag : variantTags) {

            VariantDistinctByTagDTO dto = ProductVariantMapper.mapToVariantDistinctByTagDTO(productVariantRepository.findAllByTag(tag));
            variantDistinctByTagDTOS.add(dto);
            totalQuantity += dto.getQuantity();
        }
    
        return new ProductDetailsDTO(category, totalQuantity, variantDistinctByTagDTOS);
    }
    

    public ProductVariant findProductVariantById(Long id) {
        return productVariantRepository.findById(id).orElse(null);
    }
}

