package longhoang.uet.mobile.closm.services;

import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.mappers.ProductItemInfo;
import longhoang.uet.mobile.closm.dtos.response.*;
import longhoang.uet.mobile.closm.mappers.ProductItemMapper;
import longhoang.uet.mobile.closm.models.BaseProduct;
import longhoang.uet.mobile.closm.models.ProductItem;
import longhoang.uet.mobile.closm.repositories.ProductRepository;
import longhoang.uet.mobile.closm.repositories.ProductItemRepository;
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
    private ProductItemRepository ProductItemRepository;

    public List<String> getAllCategories() {
        return productRepository.findAllProductCategories();
    }

    public List<ProductItemInfo> getAllProductItemsByCategory(String category) {
        Optional<BaseProduct> product = productRepository.findByCategory(category);

        return product.map(value -> {
            List<ProductItem> variants = ProductItemRepository.findAllByBaseProductId(value.getId());

            variants.forEach(variant -> {
                variant.setDescription(new String(variant.getDescription().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                variant.setColor(new String(variant.getColor().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
            });
            List<ProductItemInfo> ans = new ArrayList<>();
            for (ProductItem productItem : variants) {
                ans.add(ProductItemMapper.mapToProductItemInfo(productItem));
            }
            return ans;
        }).orElse(Collections.emptyList());
    }

    public List<VariantOverviewDTO> getLimitedVariantsByCategory(String category) {
        Optional<BaseProduct> productOpt = productRepository.findByCategory(category);
        if (productOpt.isEmpty()) {
            return Collections.emptyList();  // Trả về danh sách rỗng nếu không tìm thấy sản phẩm
        }
        BaseProduct product = productOpt.get();

        List<ProductItem> variants = product.getProductItems().stream().limit(4).toList();
        if (variants.isEmpty()) {
            return Collections.emptyList();  // Không có biến thể => trả về danh sách rỗng
        }

        List<VariantOverviewDTO> dtos = new ArrayList<>();
        for (ProductItem variant : variants) {
            VariantOverviewDTO dto = ProductItemMapper.mapToVariantOverviewDTO(variant);
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
        Optional<BaseProduct> productOpt = productRepository.findByCategory(category);

        if (productOpt.isEmpty()) {
            return null;
        }
        List<String> variantTags = ProductItemRepository.findAllDistinctTagByProductId(productOpt.get().getId());
        List<TaggedVariantOverviewDTO> variantDistinctByTagDTOS = new ArrayList<>();
        int totalQuantity = 0;
        for (String tag : variantTags) {

            TaggedVariantOverviewDTO dto = ProductItemMapper.mapToVariantDistinctByTagDTO(ProductItemRepository.findAllByTag(tag));
            variantDistinctByTagDTOS.add(dto);
            totalQuantity += dto.getQuantity();
        }
    
        return new ProductDetailsDTO(category, totalQuantity, variantDistinctByTagDTOS);
    }
    

    public Optional<ProductItem> findProductItemById(Long id) {
        return ProductItemRepository.findById(id);
    }
}

