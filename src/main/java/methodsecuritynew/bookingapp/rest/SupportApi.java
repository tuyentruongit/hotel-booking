package methodsecuritynew.bookingapp.rest;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.Support;
import methodsecuritynew.bookingapp.model.statics.SupportType;
import methodsecuritynew.bookingapp.service.HotelService;
import methodsecuritynew.bookingapp.service.SupportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
public class SupportApi {
    private final SupportService supportService;
//    @GetMapping("/")
//    public ResponseEntity<?> getAll (){
//        List<Support> supportList =  supportService.getAllSupport();
//        return ResponseEntity.ok(supportList);
//    }
    @GetMapping("/{supportType}")
    public ResponseEntity<?> getSupportByType (@PathVariable String supportType){
        List<Support> supportList =  supportService.getSupportByType(supportType);
        return ResponseEntity.ok(supportList);
    }

}
