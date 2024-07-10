package methodsecuritynew.bookingapp.rest;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.City;
import methodsecuritynew.bookingapp.model.request.UpsertCityRequest;
import methodsecuritynew.bookingapp.service.CityService;
import methodsecuritynew.bookingapp.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/city")
public class CityApi {
    private final CityService cityService;
    private final ImageService imageService;
    @PostMapping()
    public ResponseEntity<?> createCity(@RequestBody UpsertCityRequest request){

        System.out.println("nè");
       City city= cityService.createCity(request);
        return new ResponseEntity<>(city, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCity(@RequestBody UpsertCityRequest request, @PathVariable Integer id){
        City city= cityService.updateCity(request,id);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Integer id){
        cityService.deleteCity(id);
        return  ResponseEntity.noContent().build();
    }
    @PostMapping("/upload-image/{id}")
    public ResponseEntity<?> changeImageCity(@PathVariable Integer id, @RequestParam("file") MultipartFile file){
       imageService.uploadImageCity(file,id);
        return  ResponseEntity.ok(imageService.uploadImageCity(file,id));
    }
}
