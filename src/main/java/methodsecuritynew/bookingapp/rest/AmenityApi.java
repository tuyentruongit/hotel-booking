package methodsecuritynew.bookingapp.rest;


import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.AmenityHotel;
import methodsecuritynew.bookingapp.entity.AmenityRoom;
import methodsecuritynew.bookingapp.model.request.UpsertAmenityRequest;
import methodsecuritynew.bookingapp.service.AmenityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/amenity/")
@RequiredArgsConstructor
public class AmenityApi {
    private final AmenityService amenityService ;


    // admin cập nhật tiện ích khách sạn
    @PutMapping("/update/hotel-amenity/{id}")
    public ResponseEntity<AmenityHotel> updateAmenityHotel (@PathVariable Integer id ,@RequestBody UpsertAmenityRequest request){
        AmenityHotel amenityHotel = amenityService.updateAmenityHotel(id,request);
        return ResponseEntity.ok(amenityHotel);
    }
    // admin cập nhật tiện ích phòng
    @PutMapping("/update/room-amenity/{id}")
    public ResponseEntity<AmenityRoom> updateAmenityRoom (@PathVariable Integer id , @RequestBody UpsertAmenityRequest request){
        AmenityRoom amenityRoom = amenityService.updateAmenityRoom(id,request);
        return ResponseEntity.ok(amenityRoom);
    }

    // admin tạo tiện ích khách sạn
    @PostMapping("/create/hotel-amenity")
    public ResponseEntity<AmenityHotel> createAmenityHotel (@RequestBody UpsertAmenityRequest request){
        AmenityHotel amenityHotel = amenityService.createAmenityHotel(request);
        return new ResponseEntity<>(amenityHotel, HttpStatus.CREATED);
    }

    // admin tạo tiện ích phòng
    @PostMapping("/create/room-amenity")
    public ResponseEntity<AmenityRoom> createAmenityRoom ( @RequestBody UpsertAmenityRequest request){
        AmenityRoom amenityRoom = amenityService.createAmenityRoom(request);
        return new ResponseEntity<>(amenityRoom, HttpStatus.CREATED);
    }

    // admin xóa tiện ích khách sạn hoặc phòng
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAmenity (@PathVariable Integer id){
        amenityService.deleteAmenity(id);
        return ResponseEntity.noContent().build();
    }
}
