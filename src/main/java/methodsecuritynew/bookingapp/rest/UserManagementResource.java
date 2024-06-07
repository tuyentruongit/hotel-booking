//package methodsecuritynew.bookingapp.rest;
//
//import lombok.RequiredArgsConstructor;
//import methodsecuritynew.bookingapp.entity.ImageUser;
//import methodsecuritynew.bookingapp.model.request.ChangeInformationUserRequest;
//import methodsecuritynew.bookingapp.service.UserService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("api/users")
//@RequiredArgsConstructor
//public class UserManagementResource {
//    private  final UserService userService ;
//
//    @PostMapping("/upload-avatar")
//    public ResponseEntity<?> uploadImageUser (@RequestParam("file") MultipartFile file){
//        return new ResponseEntity<>(userService.uploadImageForUser(file), HttpStatus.CREATED);
//    }
//    @PutMapping("/update-user/{id}")
//    public ResponseEntity<?> updateUser(@RequestBody ChangeInformationUserRequest changeInformationUserRequest, @PathVariable Integer id){
//        userService.updateUser(id,changeInformationUserRequest);
//        return ResponseEntity.ok().build();
//
//    }
//
//
//}
