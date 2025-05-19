package longhoang.uet.mobile.closm.repositories;

import longhoang.uet.mobile.closm.models.BaseProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaseProductRepository extends JpaRepository<BaseProduct, Long> {
    @Query(value = "SELECT DISTINCT category FROM base_products", nativeQuery = true)
    List<String> findAllProductCategories();
    @Query(value = "SELECT DISTINCT id FROM base_products", nativeQuery = true)
    List<Long> findAllProductCategoriesId();

    @Query(value = "SELECT category FROM base_products where id = :id", nativeQuery = true)
    String getCategoryNameById(Long id);
    @Query
    Optional<BaseProduct> findByCategory(String category);
}
