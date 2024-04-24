package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Support;
import methodsecuritynew.bookingapp.model.statics.SupportType;
import methodsecuritynew.bookingapp.repository.SupportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportService {
    private final SupportRepository supportRepository;


    public List<Support> getSupportByType(String typeSupport) {
        System.out.println(
                supportRepository.findAll().stream()
                        .filter(support -> support.getSupportType().getValue().equalsIgnoreCase(typeSupport))
                        .toList()
        );
        return supportRepository.findAll().stream()
                .filter(support -> support.getSupportType().getValue().equalsIgnoreCase(typeSupport))
                .toList();
    }

    public List<Support> getAllSupport() {
        return supportRepository.findAll();
    }
}
