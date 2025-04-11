package longhoang.uet.mobile.closm.repositories;


import longhoang.uet.mobile.closm.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
