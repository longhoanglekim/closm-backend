package longhoang.uet.mobile.closm.repositories;

import longhoang.uet.mobile.closm.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
