package longhoang.uet.mobile.closm.repositories;


import longhoang.uet.mobile.closm.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderVariantRepository extends JpaRepository<OrderItem, Long> {
}
