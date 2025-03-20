package longhoang.uet.mobile.closm.repositories;

import longhoang.uet.mobile.closm.models.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findAllByProductId(Long productId);
}
