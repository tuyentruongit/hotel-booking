package methodsecuritynew.bookingapp.rest;


import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.model.request.AdminUpdateUserRequest;
import methodsecuritynew.bookingapp.model.request.CreateUserRequest;
import methodsecuritynew.bookingapp.service.ImageService;
import methodsecuritynew.bookingapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    private final ImageService imageService;

    @PostMapping()
    public ResponseEntity<?> adminCreateUser(@RequestBody CreateUserRequest request){
        User user= userService.createUser(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> adminUpdateUser(@PathVariable Integer id ,@RequestBody AdminUpdateUserRequest request){
        User user= userService.adminUpdateUser(id,request);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/reset-password/{id}")
    public ResponseEntity<?> resetPasswordUser(@PathVariable Integer id ){
        userService.adminResetPasswordUser(id);
        return ResponseEntity.ok(userService.adminResetPasswordUser(id));
    }
    @PostMapping("/update-avatar/{id}")
    public ResponseEntity<?> updateAvatar(@RequestParam("file") MultipartFile file, @PathVariable Integer id) {
        return ResponseEntity.ok(imageService.uploadImageForUser(id, file));
    }


}