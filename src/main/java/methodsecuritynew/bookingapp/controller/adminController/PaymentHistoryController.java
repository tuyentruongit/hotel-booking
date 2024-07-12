package methodsecuritynew.bookingapp.controller.adminController;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.PaymentReceipt;
import methodsecuritynew.bookingapp.model.enums.AmenityHotelType;
import methodsecuritynew.bookingapp.model.enums.AmenityRoomType;
import methodsecuritynew.bookingapp.service.PaymentReceiptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/admin/payment-history")
@RequiredArgsConstructor

public class PaymentHistoryController {
    private final PaymentReceiptService paymentHistoryService;

    @GetMapping()
    public String getAllPaymentHistory(Model model ){
        List<PaymentReceipt> paymentHistoryServices = paymentHistoryService.getAllPaymentHistoryOrderByCreateDesc();
        model.addAttribute("paymentHistoryServices" , paymentHistoryServices);
        return "admin/payment-history/index";
    }
}
