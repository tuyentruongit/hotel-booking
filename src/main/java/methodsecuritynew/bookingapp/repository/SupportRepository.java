package methodsecuritynew.bookingapp.repository;

import methodsecuritynew.bookingapp.entity.Support;
import methodsecuritynew.bookingapp.model.enums.SupportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportRepository extends JpaRepository<Support,Integer> {

    List<Support> findBySupportType(SupportType supportTypeValue);
    List<Support> findAllByStatusTrue();
}

