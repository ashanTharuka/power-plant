package au.ventrek.powerplant.repository;

import au.ventrek.powerplant.domain.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {

    @Query("select b from Battery b where b.postcode " +
            "between :postCodeRangeStart and :postCodeRangeEnd order by b.name ASC")
    List<Battery> getBatteriesByPostCodeRange(int postCodeRangeStart, int postCodeRangeEnd);
}
