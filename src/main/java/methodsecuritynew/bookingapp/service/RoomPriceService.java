package methodsecuritynew.bookingapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.entity.Room;
import methodsecuritynew.bookingapp.entity.RoomPrice;
import methodsecuritynew.bookingapp.model.dto.RoomPriceDto;
import methodsecuritynew.bookingapp.model.enums.RoomType;
import methodsecuritynew.bookingapp.model.request.RoomPriceRequest;
import methodsecuritynew.bookingapp.repository.RoomPriceRepository;
import methodsecuritynew.bookingapp.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class RoomPriceService {
    private final RoomPriceRepository roomPriceRepository;
    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    public void setRoomPrice(RoomPriceRequest request) {
        Hotel hotel = hotelService.getHotelByAccountCurrent();

        Room room = roomRepository.findById(request.getIdRoom())
                .orElseThrow(()-> new RuntimeException("Không tìm thấy phòng nòa có id : "+ request.getIdRoom()));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate startDate = LocalDate.parse(request.getStartDate(),dateTimeFormatter);
        log.info("ngày bắt đầu"+startDate);
        LocalDate endDate = LocalDate.parse(request.getEndDate(),dateTimeFormatter);


        Period period = Period.between(startDate,endDate);
        for (int i = 0 ; i <= period.getDays(); i++) {
            LocalDate localDate = startDate.plusDays(i);

            RoomPrice roomPricePresent = roomPriceRepository.findByDateAndRoom_Id(localDate, request.getIdRoom());

            for (String day : request.getDayApply()){
                if (roomPricePresent!= null &&
                        roomPricePresent.getDate().getDayOfWeek().toString().equalsIgnoreCase(day)){
                    roomPricePresent.setPrice(request.getPrice());
                    roomPriceRepository.save(roomPricePresent);
                    break;
                }
                else if (roomPricePresent == null && day.equalsIgnoreCase(localDate.getDayOfWeek().toString())){
                    RoomPrice roomPrice = RoomPrice.builder()
                            .date(localDate)
                            .price(request.getPrice())
                            .hotel(hotel)
                            .room(room)
                            .createdAt(LocalDate.now())
                            .build();
                    roomPriceRepository.save(roomPrice);
                }
            }
        }

    }


    public List<RoomPriceDto> getRoomPriceDay(String date) {
        Hotel hotel = hotelService.getHotelByAccountCurrent();
        List<Room> roomList = roomRepository.findRoomByHotel_Id(hotel.getId());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date,dateTimeFormatter);
        List<RoomPriceDto> roomPriceDtos = new ArrayList<>();
        List<RoomPrice> roomPriceList = roomPriceRepository.findAllByDate(localDate);
        for (int i = 0; i < roomList.size(); i++) {
            boolean check = false;
            for (RoomPrice roomPrice : roomPriceList) {
                if (Objects.equals(roomPrice.getHotel().getId(), hotel.getId())
                        && Objects.equals(roomList.get(i).getId(), roomPrice.getRoom().getId())){
                    roomPriceDtos.add(new RoomPriceDto(roomPrice.getRoom().getName(), roomPrice.getPrice(),
                            roomPrice.getRoom().getRoomType().getValue(),roomPrice.getDate()));
                    check = true;
                    break;
                }

            }
            if (!check){
                Room room = roomList.get(i);
                RoomPriceDto roomPriceDto = new RoomPriceDto(room.getName(),room.getPriceDefault(),room.getRoomType().getValue(),localDate);
                roomPriceDtos.add(roomPriceDto);
            }
        }
        return roomPriceDtos;
    }
}
