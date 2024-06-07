package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.*;
import methodsecuritynew.bookingapp.model.enums.AmenityHotelType;
import methodsecuritynew.bookingapp.model.enums.AmenityRoomType;
import methodsecuritynew.bookingapp.model.request.UpsertAmenityRequest;
import methodsecuritynew.bookingapp.repository.AmenityHotelRepository;
import methodsecuritynew.bookingapp.repository.AmenityRoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AmenityService {

    private final AmenityHotelRepository amenityHotelRepository;
    private final AmenityRoomRepository amenityRoomRepository;
    private final HotelService hotelService;

    public List<AmenityHotel> getAllAmenityHotelByHotel (){
        Hotel hotel = hotelService.getHotelByAccountCurrent();
       return amenityHotelRepository.findAllByHotel_Id(hotel.getId());
    }


    public List<AmenityRoom> getAllAmenityRoomByHotel() {
        Hotel hotel = hotelService.getHotelByAccountCurrent();
        return amenityRoomRepository.findAmenityRoomByHotel_Id(hotel.getId());
    }

    public AmenityHotel updateAmenityHotel(Integer id, UpsertAmenityRequest request) {
        AmenityHotel amenityHotel = amenityHotelRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy tiện ích nào có id :"+id));
        amenityHotel.setName(request.getName());
        amenityHotel.setAmenityHotelType(AmenityHotelType.valueOf(request.getTypeAmenity()));

        return amenityHotelRepository.save(amenityHotel);
    }

    public AmenityRoom updateAmenityRoom(Integer id, UpsertAmenityRequest request) {
        AmenityRoom amenityRoom = amenityRoomRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy tiện ích nào có id :"+id));
        amenityRoom.setName(request.getName());
        amenityRoom.setAmenityRoomType(AmenityRoomType.valueOf(request.getTypeAmenity()));
        amenityRoom.setUpdateAt(LocalDate.now());

        return amenityRoomRepository.save(amenityRoom);
    }

    public AmenityHotel createAmenityHotel(UpsertAmenityRequest request) {

        Hotel hotel = hotelService.getHotelByAccountCurrent();

        AmenityHotel amenityHotel = amenityHotelRepository.findByName(request.getName());
        if (amenityHotel!=null){
            throw  new RuntimeException("Tên tiện ích trên đã tồn tại ");
        }

        AmenityHotel amenityHotelNew = new AmenityHotel();
        amenityHotelNew.setName(request.getName());
        amenityHotelNew.setHotel(hotel);
        amenityHotelNew.setCreatedAt(LocalDate.now());
        amenityHotelNew.setAmenityHotelType(AmenityHotelType.valueOf(request.getTypeAmenity()));
        return amenityHotelRepository.save(amenityHotelNew);
    }
    public AmenityRoom createAmenityRoom(UpsertAmenityRequest request) {

        Hotel hotel = hotelService.getHotelByAccountCurrent();

        AmenityRoom amenityRoom = amenityRoomRepository.findByName(request.getName());
        if (amenityRoom!=null){
            throw  new RuntimeException("Tên tiện ích trên đã tồn tại ");
        }

        AmenityRoom amenityRoomNew = new AmenityRoom();
        amenityRoomNew.setName(request.getName());
        amenityRoomNew.setHotel(hotel);
        amenityRoomNew.setCreatedAt(LocalDate.now());
        amenityRoomNew.setAmenityRoomType(AmenityRoomType.valueOf(request.getTypeAmenity()));
        return amenityRoomRepository.save(amenityRoomNew);
    }

    public void deleteAmenity(Integer id) {
        AmenityHotel amenityHotel = amenityHotelRepository.findById(id).get();
        if (amenityHotel == null) {
            AmenityRoom amenityRoom = amenityRoomRepository.findById(id).get();
            if (amenityRoom==null){
                throw new RuntimeException("Không tìm thấy tiện ích nào có id"+id);
            }else {
                amenityRoom.setHotel(null);
                amenityRoomRepository.delete(amenityRoom);
            }
        }
        else {
            amenityHotel.setHotel(null);
            amenityHotelRepository.delete(amenityHotel);
        }


    }
}
