package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.PaymentReceipt;
import methodsecuritynew.bookingapp.repository.PaymentReceiptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentReceiptService {
    private final PaymentReceiptRepository paymentReceiptRepository;

    public List<PaymentReceipt> getAllPaymentHistoryOrderByCreateDesc() {
        return paymentReceiptRepository.findAllOrderByDescending();

    }
}
