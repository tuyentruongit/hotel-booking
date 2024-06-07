package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.PolicyHotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<PolicyHotel,Integer> {


}
