package methodsecuritynew.bookingapp.rest;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.ImageHotel;
import methodsecuritynew.bookingapp.entity.ImageUser;
import methodsecuritynew.bookingapp.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("api/images")
@RequiredArgsConstructor
public class ImageApi {

    private final ImageService imageService;

    @PostMapping("/upload/user")
    public ResponseEntity<?> uploadImageUser (@RequestParam("file") MultipartFile file){
        ImageUser imageUser = imageService.uploadImageForUser(file);
        return new ResponseEntity<>(imageUser, HttpStatus.CREATED);
    }
    @PostMapping("/upload-hotel/{idHotel}")
    public ResponseEntity<?> uploadImageHotel (@RequestParam("file") MultipartFile file, @PathVariable Integer idHotel){
        ImageHotel imageHotel = imageService.uploadImageHotel(idHotel,file);
        return new ResponseEntity<>(imageHotel, HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<?> deleteImageUser ( @PathVariable String id){
       imageService.deleteImageUser(id);
        return  ResponseEntity.noContent().build();
    }
    @DeleteMapping("/delete/hotel/{id}")
    public ResponseEntity<?> deleteImageHotel ( @PathVariable String id){
       imageService.deleteImageHotel(id);
        return  ResponseEntity.noContent().build();
    }

}
