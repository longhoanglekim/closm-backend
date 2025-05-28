package longhoang.uet.mobile.closm.repositories;

import longhoang.uet.mobile.closm.models.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    PaymentMethod findByMethod(String method);
}
