package longhoang.uet.mobile.closm.repositories;

import longhoang.uet.mobile.closm.models.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    // Lấy tất cả các biến thể theo product_id
    List<ProductVariant> findAllByProductId(Long productId);

//    // Native query để lấy 4 biến thể đầu tiên theo product_id
//    @Query(value = "SELECT * FROM product_variants WHERE product_id = (:productId) LIMIT 4", nativeQuery = true)
//    List<ProductVariant> findTop4VariantsByProductId(@Param("productId") Long productId);
    // t thêm đay nhé    
//    @Query("SELECT v FROM ProductVariant v JOIN Product p ON v.productId = p.id WHERE p.category = :category")
//    List<ProductVariant> findByCategory(@Param("category") String category);
    
//    int countProductVariantByProductId(Long productId);
}
