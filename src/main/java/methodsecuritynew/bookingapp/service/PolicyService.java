package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.PolicyHotel;
import methodsecuritynew.bookingapp.model.request.UpsertPolicyRequest;
import methodsecuritynew.bookingapp.repository.PolicyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PolicyService {
    private final PolicyRepository policyRepository;
    private final HotelService hotelService;

    public void updatePolicyHotel( UpsertPolicyRequest request) {
        Hotel hotel = hotelService.getHotelByAccountCurrent();
        PolicyHotel policyHotel = hotel.getPolicyHotel();
        policyHotel.setCheckIn(request.getCheckIn());
        policyHotel.setCheckOut(request.getCheckOut());
        policyHotel.setService(request.getService());
        policyHotel.setNote(request.getOther());
        policyHotel.setCancelPolicy(request.getCancel());
        policyHotel.setAnimal(request.getAnimal());
        policyHotel.setAgeLimit(request.getAge());
        policyHotel.setUpdatedAt(LocalDate.now());
        policyRepository.save(policyHotel);
    }


}
