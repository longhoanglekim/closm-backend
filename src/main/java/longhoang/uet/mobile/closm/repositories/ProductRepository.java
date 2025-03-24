package longhoang.uet.mobile.closm.repositories;

import longhoang.uet.mobile.closm.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT DISTINCT category FROM products", nativeQuery = true)
    List<String> findAllProductCategories();

    Optional<Product> findByCategory(String category);
}
