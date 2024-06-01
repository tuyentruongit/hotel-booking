package methodsecuritynew.bookingapp.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import methodsecuritynew.bookingapp.entity.*;
import methodsecuritynew.bookingapp.repository.*;
import methodsecuritynew.bookingapp.untils.FileUtils;
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
    private final HotelRepository hotelRepository;
    private final ImageHotelRepository imageHotelRepository;
    private final CityRepository cityRepository;

    public ImageService(ImageUserRepository imageUserRepository, UserRepository userRepository, HttpSession session, HotelRepository hotelRepository, ImageHotelRepository imageHotelRepository, CityRepository cityRepository) {
        this.imageUserRepository = imageUserRepository;
        this.userRepository = userRepository;
        this.session = session;
        this.hotelRepository = hotelRepository;
        this.imageHotelRepository = imageHotelRepository;
        this.cityRepository = cityRepository;
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

    @Transactional
    public ImageHotel uploadImageHotel (Integer idHotel , MultipartFile multipartFile){
        Hotel hotel = hotelRepository.findById(idHotel).orElseThrow(()-> new RuntimeException("Không tìm thấy khách sạn nào có id :"+idHotel));
        // tạo một id image
         String imageId = UUID.randomUUID().toString();

        // tìm đường dẫn tới folder lưu ảnh
        Path rootPath = Paths.get(uploadDir).resolve("image_hotel");
        creatFolder(rootPath);
        // tạo đường dẫn file mới với tên là  idImage;
        Path filePath = rootPath.resolve(imageId);
        try {
            // sao chép dữ liệu từ input vào 1 pathfile đã được tạo đường dẫn trước đó
            Files.copy(multipartFile.getInputStream(),filePath);
        } catch (IOException e) {
            throw new RuntimeException("Không thể upload file " + imageId);
        }
        ImageHotel imageHotel = new ImageHotel();
        imageHotel.setId(imageId);
        // set tên của file được upload
        imageHotel.setName(multipartFile.getOriginalFilename());
        imageHotel.setType(multipartFile.getContentType());
        imageHotel.setSize(multipartFile.getSize() / 1048576.0);
        imageHotel.setUrl("/"+uploadDir+"/image_hotel/"+imageId);
        imageHotel.setHotel(hotel);
        return imageHotelRepository.save(imageHotel);
    }
    public void creatFolder(Path path){
        if (!Files.exists(path)){
            try{
                // tạo thư mục
                Files.createDirectory(path);
            }catch (IOException e){
                throw new RuntimeException("Không thể tạo được ");
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
    public ImageUser uploadImageForUser(Integer id ,MultipartFile file) {

        User user = userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("Không tìm thấy người dùng trên "));

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

             try{

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

    public List<ImageHotel> getAllImageByIdHotel(int i) {
      return imageHotelRepository.findAllByHotel_Id(i);
    }

    public void deleteImageHotel(String id) {
        ImageHotel imageHotel = imageHotelRepository.findById(id).orElseThrow(()->new RuntimeException("Không tìm thấy image"));
        Path filePathDelete = Paths.get(uploadDir).resolve("image_hotel").resolve(id);
        try{
            Files.deleteIfExists(filePathDelete);
            imageHotelRepository.delete(imageHotel);
        }catch (IOException ioException){
            throw new RuntimeException(ioException);
        }

    }

    public City uploadImageCity(MultipartFile file, Integer id) {

        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thành phố nào có id " + id));
        String imageId = UUID.randomUUID().toString();

        Path rootPath = Paths.get(uploadDir).resolve("image_city");
        creatFolder(rootPath);
        Path filePath = rootPath.resolve(imageId);

       if (city.getImageCity()!=null){
           FileUtils.deleteFile(city.getImageCity());
       }
        try {
            Files.copy(file.getInputStream(),filePath);
        } catch (IOException e) {
            throw new RuntimeException("Không thể upload file " + imageId);
        }
        city.setImageCity("/"+uploadDir+"/image_city/"+imageId);
        return cityRepository.save(city);
    }
}
