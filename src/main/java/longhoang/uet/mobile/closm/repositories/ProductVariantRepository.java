package longhoang.uet.mobile.closm.repositories;

import longhoang.uet.mobile.closm.models.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    Optional<ProductVariant> findById(Long id);
    
    // Lấy tất cả các biến thể theo product_id
    List<ProductVariant> findAllByProductId(Long productId);

    List<ProductVariant> findDistinctByTag(String tag);

    @Query(value = "SELECT distinct tag from product_variants where product_id = :productId", nativeQuery = true)
    List<String> findAllDistinctTagByProductId(long productId);

    List<ProductVariant> findAllByTag(String tag);
}
