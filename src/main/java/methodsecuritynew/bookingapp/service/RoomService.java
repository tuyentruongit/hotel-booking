package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.*;
import methodsecuritynew.bookingapp.model.request.UpsertRoomRequest;
import methodsecuritynew.bookingapp.model.enums.BedSize;
import methodsecuritynew.bookingapp.model.enums.BedType;
import methodsecuritynew.bookingapp.model.enums.RoomType;
import methodsecuritynew.bookingapp.repository.AmenityRoomRepository;
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
    private final AuthService authService;
    private final HotelService hotelService;
    private final ImageService imageService ;


    public List<Room> getRoomByIdHotel(Integer id) {
        return roomRepository.findRoomByHotel_Id(id);
    }

    public Room getRoomById(Integer idRoom) {
        return  roomRepository.findById(idRoom).orElseThrow(()-> new RuntimeException("Không tìm thấy phòng có id: " + idRoom));
    }

    public List<Room> getRoomHotelManager() {
        Hotel hotel = hotelService.getHotelByAccountCurrent();
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
        return imageService.getAllImageRoom(room.getId() ,limit, page);
    }

    public void deleteImageRoom(String id) {
        imageService.deleteImageRoom(id);
    }

    public Room createRoom(UpsertRoomRequest request) {
        Hotel hotel =hotelService.getHotelByAccountCurrent();
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
                .bedSize(BedSize.valueOf(request.getBedSize()))
                .bedType(BedType.valueOf(request.getBedType()))
                .roomType(RoomType.valueOf(request.getRoomType()))
                .createdAt(LocalDate.now())
                .build();
        return roomRepository.save(roomNew);
    }

    public void deleteRoom(Integer id) {
        Room room = roomRepository.findById(id).orElseThrow(()-> new RuntimeException("Không tìm thấy room nào có id :" + id));
        roomRepository.delete(room);
    }
}