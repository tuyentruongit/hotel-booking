package methodsecuritynew.bookingapp.rest;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.RoomPrice;
import methodsecuritynew.bookingapp.model.dto.RoomPriceDto;
import methodsecuritynew.bookingapp.model.request.RoomPriceRequest;
import methodsecuritynew.bookingapp.service.RoomPriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/room-price")
@RestController
@RequiredArgsConstructor
public class RoomPriceApi {

    private final RoomPriceService roomPriceService;
    @PostMapping("/update")
    public ResponseEntity<?> setRoomPrice(@RequestBody RoomPriceRequest request){
        roomPriceService.setRoomPrice(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/day")
    public ResponseEntity<?> getRoomPriceDay(@RequestParam String date){
        List<RoomPriceDto> list = roomPriceService.getRoomPriceDay(date);
        return ResponseEntity.ok(list);
    }

}
