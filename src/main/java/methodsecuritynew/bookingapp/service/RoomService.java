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
import methodsecuritynew.bookingapp.repository.*;
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
    private final BookingRepository bookingRepository;


    public List<Room> getRoomByIdHotel(Integer id) {
        return roomRepository.findRoomByHotel_Id(id);
    }

    public Room getRoomById(Integer idRoom) {
        return  roomRepository.findById(idRoom).orElseThrow(()-> new RuntimeException("Không tìm thấy phòng có id: " + idRoom));
    }

    public List<Room> getRoomHotelManager() {
        User user = userService.getUserCurrent();
        Hotel hotel = hotelRepository.findHotelByUser_Id(user.getId());
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

        room.setName(request.getNameRoom());
        room.setAmenityRoomList(list);
        room.setDescription(request.getDescriptionRoom());
        room.setStatus(request.getStatusRoom());
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
        User user = userService.getUserCurrent();
        Hotel hotel = hotelRepository.findHotelByUser_Id(user.getId());
        Room room = roomRepository.findRoomByName(request.getNameRoom());
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
                .name(request.getNameRoom())
                .amenityRoomList(list)
                .description(request.getDescriptionRoom())
                .status(request.getStatusRoom())
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
                .status(false)
                .build();
        return roomRepository.save(roomNew);
    }
    public void deleteAllRoom(Integer idHotel) {
        List<Room> roomList = getRoomByIdHotel(idHotel);
        for (Room room : roomList){
            deleteRoom(room.getId());
        }
    }

    public void deleteRoom(Integer id) {
        Room room = roomRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy room nào có id :" + id));
        imageService.getAllImageRoomDelete(id);
        roomRepository.delete(room);
    }
    public void deleteImageRoom(String id) {
        imageService.deleteImageRoom(id);
    }


    // logic lấy ra các phòng trống của từng khách sạn theo ngày và theo booking
    List<Room> availableRooms(Integer id, LocalDate checkInDay, LocalDate checkOutDay, Integer numberRoom, Integer numberGuest) {
        // lấy danh sách các phòng theo id hotel
        List<Room> roomList = roomRepository.findRoomByHotel_IdAndStatusTrue(id);

        List<Room> result = new ArrayList<>();
        // duyệt từng phòng lấy ra các thông tin cần thiết để kiểm tra
        for (Room room : roomList){
            if (room.getCapacity()>= numberGuest /numberRoom
                    && roomCheck(room , checkInDay , checkOutDay , numberRoom)){
                result.add(room);
            }
        }
        return result;
    }
    // logic kiểm  số lượng phòng đã đặt theo từng ngày và từng phòng
    public boolean roomCheck(Room room, LocalDate checkInDay, LocalDate checkOutDay , Integer numberRoom) {
        // kiểm tra cho từng ngày trong khoảng checkin checkout
        LocalDate currentDay = checkInDay;
        while (!currentDay.isAfter(checkOutDay)) {
            List<Booking> bookingList = bookingRepository.findAllByRoom_IdAndCheckInBetween(room.getId(),currentDay,currentDay.plusDays(3));
            int totalBookedRooms = 0;

            // tính tổng số lượng phòng đã được đặt cho ngày hiện tại
            for (Booking booking : bookingList) {
                totalBookedRooms += booking.getNumberRoom();
            }
            // kiểm tra sô lượng phòng còn lại cho ngày hiện tại
            if (room.getQuantity() - totalBookedRooms < numberRoom) {
                return false;
            }

            currentDay = currentDay.plusDays(1); // next ngày tiếp theo
        }
        return true;
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
        roomDto.setThumbnailRoom(room.getThumbnailRoom());
        roomDto.setBedType(room.getBedType().getValue());
        roomDto.setBedSize(room.getBedSize().getValue());
        // giá hiển thị  bằng tổng giá số ngày người dùng lưu trú chia cho số ngày lưu trú
        roomDto.setPriceAverage(price / count);
        roomDto.setAmenityRoomList(room.getAmenityRoomList());
        return roomDto;
    }

    // logic lấy các thông tin của phòng gias phòng theo ngày mà người dùng đã chọn
    public List<RoomDto> getDataRoom(Integer idHotel, LocalDate start , LocalDate end , Integer numberGuest, Integer numberRoom) {
        // lấy ra các phòng theo id của khách sạn
        List<Room> roomList = availableRooms(idHotel,start,end,numberGuest,numberRoom);
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