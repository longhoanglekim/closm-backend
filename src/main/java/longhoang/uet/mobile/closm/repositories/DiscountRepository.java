package longhoang.uet.mobile.closm.repositories;


import longhoang.uet.mobile.closm.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query(
            value = "SELECT * FROM discounts WHERE start_date < :currentDate AND end_date > :currentDate",
            nativeQuery = true
    )
    List<Discount> findValidDiscounts(@Param("currentDate") LocalDate currentDate);
}
