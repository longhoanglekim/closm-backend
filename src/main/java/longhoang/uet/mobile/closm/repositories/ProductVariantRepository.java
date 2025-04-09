package longhoang.uet.mobile.closm.repositories;

import longhoang.uet.mobile.closm.models.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    // Lấy tất cả các biến thể theo product_id
    List<ProductVariant> findAllByProductId(Long productId);

    List<ProductVariant> findDistinctByName(String name);
}
