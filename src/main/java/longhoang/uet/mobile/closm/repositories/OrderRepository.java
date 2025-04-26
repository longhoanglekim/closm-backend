package longhoang.uet.mobile.closm.repositories;

import longhoang.uet.mobile.closm.enums.OrderStatus;
import longhoang.uet.mobile.closm.models.Order;
import longhoang.uet.mobile.closm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findByOrderStatusAndUser(OrderStatus orderStatus, User user);
}
