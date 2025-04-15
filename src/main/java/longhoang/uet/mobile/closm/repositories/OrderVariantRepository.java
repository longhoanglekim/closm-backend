package longhoang.uet.mobile.closm.repositories;


import longhoang.uet.mobile.closm.models.OrderVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderVariantRepository extends JpaRepository<OrderVariant, Long> {
}
