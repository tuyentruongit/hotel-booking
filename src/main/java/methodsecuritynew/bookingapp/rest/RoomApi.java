package methodsecuritynew.bookingapp.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.model.request.UpsertRoomRequest;
import methodsecuritynew.bookingapp.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotel-manager/room")
public class RoomApi {

    private final RoomService roomService;
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom (@RequestBody UpsertRoomRequest request, @PathVariable Integer id){
        return ResponseEntity.ok(roomService.updateRoom(request,id));
    }

    @PutMapping("/upload-image/{id}")
    public ResponseEntity<?> uploadImageRoom (@RequestParam("file") MultipartFile multipartFile , @PathVariable Integer id){
        return ResponseEntity.ok(roomService.uploadImageRoom(multipartFile,id));
    }
}
