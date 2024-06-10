package methodsecuritynew.bookingapp.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.*;
import methodsecuritynew.bookingapp.model.dto.RoomDto;
import methodsecuritynew.bookingapp.model.request.UpsertRoomRequest;
import methodsecuritynew.bookingapp.model.enums.BedSize;
import methodsecuritynew.bookingapp.model.enums.BedType;
import methodsecuritynew.bookingapp.model.enums.RoomType;
import methodsecuritynew.bookingapp.repository.AmenityRoomRepository;
import methodsecuritynew.bookingapp.repository.HotelRepository;
import methodsecuritynew.bookingapp.repository.RoomPriceRepository;
import methodsecuritynew.bookingapp.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final AmenityRoomRepository amenityRoomRepository;
    private final ImageService imageService ;
    private final HotelRepository hotelRepository;
    private final UserService userService;
    private final RoomPriceRepository roomPriceRepository;


    public List<Room> getRoomByIdHotel(Integer id) {
        return roomRepository.findRoomByHotel_Id(id);
    }

    public Room getRoomById(Integer idRoom) {
        return  roomRepository.findById(idRoom).orElseThrow(()-> new RuntimeException("Không tìm thấy phòng có id: " + idRoom));
    }

    public List<Room> getRoomHotelManager() {
//        User user = userService.getUserCurrent();
//        Hotel hotel = hotelRepository.findHotelByUser_Id(user.getId());
        Hotel hotel = hotelRepository.findById(2).orElseThrow(() -> new RuntimeException("Không thấy"));
        return roomRepository.findRoomByHotel_Id(hotel.getId());
    }

    public Room updateRoom(UpsertRoomRequest request, Integer id) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng có id :" +id));

        List<AmenityRoom> list = new ArrayList<>();
        request.getAmenityRoom().forEach(idAme ->{
            AmenityRoom amenityRoom = amenityRoomRepository.findById(idAme)
                    .orElseThrow(()-> new RuntimeException("Không tìm thấy amenity nào có id : " + idAme));
            list.add(amenityRoom);
        });

        room.setName(request.getName());
        room.setAmenityRoomList(list);
        room.setDescription(request.getDescription());
        room.setStatus(request.getStatus());
        room.setArea(request.getArea());
        room.setCapacity(request.getCapacity());
        room.setPriceDefault(request.getPriceDefault());
        room.setQuantity(request.getQuantity());
        room.setBedSize(BedSize.valueOf(request.getBedSize()));
        room.setBedType(BedType.valueOf(request.getBedType()));
        room.setRoomType(RoomType.valueOf(request.getRoomType()));

        return roomRepository.save(room);
    }

    public ImageRoom uploadImageRoom(MultipartFile multipartFile, Integer id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng có id : " +id));

        return imageService.saveImageRoom(room,multipartFile);
    }

    public Page<ImageRoom> getAllImageRoom(Integer id, Integer limit , Integer page) {
       Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng có id : " +id));
        return imageService.getImageRoomPage(room.getId() ,limit, page);
    }

    // tạo phòng khi đăng ký cho thuê vầ khi taoh thêm phòng mới hotel manager\
    @Transactional
    public Room createRoom(UpsertRoomRequest request) {
//        User user = userService.getUserCurrent();
//        Hotel hotel = hotelRepository.findHotelByUser_Id(user.getId());
        Hotel hotel = hotelRepository.findById(2).orElseThrow(() -> new RuntimeException("Không thấy"));
        Room room = roomRepository.findRoomByName(request.getName());
        if (room!=null){
            throw new RuntimeException("Tên phòng trên đã tồn tại");
        }
        List<AmenityRoom> list = new ArrayList<>();
        request.getAmenityRoom().forEach(idAme ->{
            AmenityRoom amenityRoom = amenityRoomRepository.findById(idAme)
                    .orElseThrow(()-> new RuntimeException("Không tìm thấy amenity nào có id : " + idAme));
            list.add(amenityRoom);
        });
        Room roomNew = Room.builder()
                .name(request.getName())
                .amenityRoomList(list)
                .description(request.getDescription())
                .status(request.getStatus())
                .area(request.getArea())
                .capacity(request.getCapacity())
                .quantity(request.getQuantity())
                .hotel(hotel)
                .priceDefault(request.getPriceDefault())
                .priceDefault(request.getPriceDefault())
                .bedSize(BedSize.valueOf(request.getBedSize()))
                .bedType(BedType.valueOf(request.getBedType()))
                .roomType(RoomType.valueOf(request.getRoomType()))
                .createdAt(LocalDate.now())
                .build();
        return roomRepository.save(roomNew);
    }

    public void deleteRoom(Integer id) {
        Room room = roomRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy room nào có id :" + id));
        imageService.getAllImageRoomDelete(id);
        roomRepository.delete(room);
    }
    public void deleteImageRoom(String id) {
        imageService.deleteImageRoom(id);
    }


    // tạo dối tượng lưu trữ các thông tin cần thiết
    public RoomDto createRoomDto(Room room, int price, int count) {

        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setName(room.getName());
        roomDto.setDescription(room.getDescription());
        roomDto.setCapacity(room.getCapacity());
        roomDto.setRoomType(room.getRoomType());
        roomDto.setArea(room.getArea());
        roomDto.setBedType(room.getBedType().getValue());
        roomDto.setBedSize(room.getBedSize().getValue());
        // giá hiển thị  bằng tổng giá số ngày người dùng lưu trú chia cho số ngày lưu trú
        roomDto.setPriceAverage(price / count);
        roomDto.setAmenityRoomList(room.getAmenityRoomList());
        return roomDto;
    }

    // logic lấy các thông tin của phòng gias phòng theo ngày mà người dùng đã chọn
    public List<RoomDto> getDataRoom(Integer idHotel, LocalDate start , LocalDate end) {
        // lấy ra các phòng theo id của khách sạn
        List<Room> roomList = roomRepository.findRoomByHotel_Id(idHotel);
        List<RoomDto> roomDtoList = new ArrayList<>();
        for (Room room : roomList ){
            LocalDate currentDay = start;
            int price = 0;
            int count = 0;
            while (currentDay.isBefore(end)) {
                // lấy giá mà hotel manager đã set cho ngay hôm đó , nếu chưa set lấy giá mặc định
                RoomPrice roomPrice = roomPriceRepository.findByDateAndRoom_Id(currentDay,room.getId());
                // nếu hotel manager chưa set giá cho ngày hôm đó thì sẽ lấy giá mặc định của phòng
                if (roomPrice!=null){
                    price += roomPrice.getPrice();
                }
                else {
                    price += room.getPriceDefault();
                }
                currentDay = currentDay.plusDays(1); // next ngày tiếp theo
                count++;
            }
           // tạo một đối tượng chứa các thông tin cần thiết để hiển thị
            RoomDto roomDto =createRoomDto(room, price, count);
            roomDtoList.add(roomDto);
        }
        return roomDtoList;
    }
}