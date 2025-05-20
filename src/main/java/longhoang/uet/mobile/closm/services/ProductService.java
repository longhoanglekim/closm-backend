package longhoang.uet.mobile.closm.services;

import lombok.extern.slf4j.Slf4j;
import longhoang.uet.mobile.closm.dtos.mappers.ProductItemInfo;
import longhoang.uet.mobile.closm.dtos.request.BaseProductInput;
import longhoang.uet.mobile.closm.dtos.response.*;
import longhoang.uet.mobile.closm.mappers.ProductItemMapper;
import longhoang.uet.mobile.closm.models.BaseProduct;
import longhoang.uet.mobile.closm.models.ProductItem;
import longhoang.uet.mobile.closm.repositories.BaseProductRepository;
import longhoang.uet.mobile.closm.repositories.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    private BaseProductRepository productRepository;
    @Autowired
    private ProductItemRepository ProductItemRepository;
    @Autowired
    private BaseProductRepository baseProductRepository;

    @Cacheable(value = "categories", key = "1")
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


    public List<ItemOverviewDTO> getLimitedVariantsByCategory(String category) {
        Optional<BaseProduct> productOpt = productRepository.findByCategory(category);
        if (productOpt.isEmpty()) {
            return Collections.emptyList();  // Trả về danh sách rỗng nếu không tìm thấy sản phẩm
        }
        BaseProduct product = productOpt.get();

        List<ProductItem> variants = product.getProductItems().stream().limit(4).toList();
        if (variants.isEmpty()) {
            return Collections.emptyList();  // Không có biến thể => trả về danh sách rỗng
        }

        List<ItemOverviewDTO> dtos = new ArrayList<>();
        for (ProductItem variant : variants) {
            ItemOverviewDTO dto = ProductItemMapper.mapToItemOverviewDTO(variant);
            dtos.add(dto);
        }
        return dtos;
    }
    @Cacheable(value = "productOverview", key = "#category")
    public ProductOverviewDTO getProductOverview(String category) {
        log.debug("getProductOverview from db");
        ProductOverviewDTO dto = new ProductOverviewDTO();
        dto.setCategory(category);
        List<ItemOverviewDTO> variants = getLimitedVariantsByCategory(category);
        int totalQuantity = 0;
        for (ItemOverviewDTO variant : variants) {
            totalQuantity += variant.getQuantity();
        }
        dto.setQuantity(totalQuantity);
        dto.setVariants(variants);
        return dto;
    }
    @Cacheable(value = "productDetails", key = "#category")
    public ProductDetailsDTO getProductDetails(String category) {
        Optional<BaseProduct> productOpt = productRepository.findByCategory(category);

        if (productOpt.isEmpty()) {
            return null;
        }
        List<String> variantTags = ProductItemRepository.findAllDistinctTagByProductId(productOpt.get().getId());
        List<TaggedVariantOverviewDTO> variantDistinctByTagDTOS = new ArrayList<>();
        int totalQuantity = 0;
        for (String tag : variantTags) {
            log.debug(tag);
            TaggedVariantOverviewDTO dto = ProductItemMapper.mapToVariantDistinctByTagDTO(ProductItemRepository.findAllByTag(tag));
            variantDistinctByTagDTOS.add(dto);
            totalQuantity += dto.getQuantity();
        }
    
        return new ProductDetailsDTO(category, totalQuantity, variantDistinctByTagDTOS);
    }
    
    public long createBaseProduct(BaseProductInput baseProductInput) {
        BaseProduct product = new BaseProduct();
        product.setCategory(baseProductInput.getCategory());
        product.setImageUrl(baseProductInput.getImageUrl());
        productRepository.save(product);
        return product.getId();
    }

    public long updateBaseProduct(long id, BaseProductInput baseProductInput) throws Exception{
        BaseProduct product = baseProductRepository.findById(id).get();
        product.setCategory(baseProductInput.getCategory());
        product.setImageUrl(baseProductInput.getImageUrl());
        productRepository.save(product);
        return product.getId();
    }

    public long deleteBaseProduct(long id) {
        BaseProduct product = baseProductRepository.findById(id).get();
        productRepository.delete(product);
        return product.getId();
    }
}

