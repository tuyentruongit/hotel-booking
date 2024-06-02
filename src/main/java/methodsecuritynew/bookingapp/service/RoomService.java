package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.*;
import methodsecuritynew.bookingapp.model.request.UpsertRoomRequest;
import methodsecuritynew.bookingapp.model.statics.BedSize;
import methodsecuritynew.bookingapp.model.statics.BedType;
import methodsecuritynew.bookingapp.model.statics.RoomType;
import methodsecuritynew.bookingapp.repository.AmenityRoomRepository;
import methodsecuritynew.bookingapp.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


        
        return null;
    }

}
