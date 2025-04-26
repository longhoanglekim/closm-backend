package longhoang.uet.mobile.closm.repositories;

import longhoang.uet.mobile.closm.models.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
    
    // Lấy tất cả các biến thể theo product_id
    List<ProductItem> findAllByBaseProductId(Long productId);

    List<ProductItem> findDistinctByTag(String tag);

    @Query(value = "SELECT distinct tag from product_items where base_product_id = :productId", nativeQuery = true)
    List<String> findAllDistinctTagByProductId(long productId);

    List<ProductItem> findAllByTag(String tag);
}
