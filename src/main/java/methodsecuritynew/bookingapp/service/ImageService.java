package methodsecuritynew.bookingapp.service;

import jakarta.servlet.http.HttpSession;
import methodsecuritynew.bookingapp.entity.Image;
import methodsecuritynew.bookingapp.entity.ImageUser;
import methodsecuritynew.bookingapp.entity.User;
import methodsecuritynew.bookingapp.repository.ImageUserRepository;
import methodsecuritynew.bookingapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service
public class ImageService {
    private String uploadDir = "upload_image";

    private final ImageUserRepository imageUserRepository;

    private final UserRepository userRepository;
    private final HttpSession session;

    public ImageService( ImageUserRepository imageUserRepository, UserRepository userRepository, HttpSession session) {
        this.imageUserRepository = imageUserRepository;
        this.userRepository = userRepository;
        this.session = session;
        createDirector(uploadDir);
    }

    public void createDirector (String name){
        // tạo đường dẫn với tên thư mục (nếu thư mục đó chưa ợc tạo thì vẫn tạo đường dẫn )
        Path path = Paths.get(name);

        // kiểm tra xem đối tượng đó đã được tạo chưa nếu chưa thì tạo thư mục
        if (!Files.exists(path)){
           try{
               // tạo thư mục
               Files.createDirectory(path);
           }catch (IOException e){
               throw new RuntimeException("Không thể tạo được file");
           }
        }
    }


    public ImageUser uploadImageForUser(MultipartFile file) {

        User user = userRepository.findByEmail(session.getAttribute("MY_SESSION").toString()).orElseThrow(()->new UsernameNotFoundException("Không tìm thấy người dùng trên "));

        // upload file vào folder
        // tạo một id image
        String imageId = UUID.randomUUID().toString();

        // tìm đường dẫn tới folder lưu ảnh
        Path rootPath = Paths.get(uploadDir);

        // tạo đường dẫn file mới với tên là  idImage;
        Path filePath = rootPath.resolve(imageId);


        try {
            // sao chép dữ liệu từ input vào 1 pathfile đã được tạo đường dẫn trước đó
            Files.copy(file.getInputStream(),filePath);
        } catch (IOException e) {
            throw new RuntimeException("Không thể upload file " + imageId);
        }

        ImageUser imageUser = new ImageUser();
        imageUser.setId(imageId);
        // set tên của file được upload
        imageUser.setName(file.getOriginalFilename());
        imageUser.setType(file.getContentType());
        imageUser.setSize(file.getSize() / 1048576.0);
        imageUser.setUrl("/"+uploadDir+"/"+imageId);
        imageUser.setUser(user);
        user.setAvatar("/"+uploadDir+"/"+imageId);


        // lấy ra avatar trươc đây người dùng dã lưu, sau đó xóa avt đó ra khỏi folder
        ImageUser imageUserOld =  imageUserRepository.findAllByUser_Id(user.getId());
        if (imageUserOld!=null){
            // lấy đường dẫn đến avt được tìm thấy
            Path filePathDelete = Paths.get(uploadDir).resolve(imageUserOld.getId());
//            Path fisllld = Paths.get(imageUserOld.getUrl());
//
//            System.out.println("----------------------------------------------------------------------------"+filePathDelete);
//            System.out.println("----------------------------------------------------------------------------"+fisllld);
            try {
                // xóa file đó đi
                Files.deleteIfExists(filePathDelete);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // xóa đối tượng image user khỏi db
            imageUserRepository.delete(imageUserOld);
        }
        return imageUserRepository.save(imageUser);
    }

    public void deleteImageUser(String id) {
         User user = userRepository.findByEmail(session.getAttribute("MY_SESSION").toString()).orElseThrow(()-> new UsernameNotFoundException("Không tìm thấy "));
         ImageUser imageUserOld = imageUserRepository.findById(id).orElseThrow(()->new RuntimeException("Không tìm thấy image"));

         if (Objects.equals(user.getId(), imageUserOld.getUser().getId())){
             Path filePathDelete = Paths.get(uploadDir).resolve(id);

             try{  Path fisllld = Paths.get(imageUserOld.getUrl());

                 System.out.println("----------------------------------------------------------------------------"+filePathDelete);
                 System.out.println("----------------------------------------------------------------------------"+fisllld);
                 Files.deleteIfExists(filePathDelete);
                 user.setAvatar("/web/assets/image/avatar-default.jpg");
                 imageUserRepository.delete(imageUserOld);
             }catch (IOException ioException){
                 throw new RuntimeException(ioException);
             }
         }
         else {
             throw new RuntimeException("Bạn không có quyền xóa image này ");
         }
    }

    public ImageUser getImageCurrentUser(Integer id) {
        return  imageUserRepository.findAllByUser_Id(id);
    }
}
