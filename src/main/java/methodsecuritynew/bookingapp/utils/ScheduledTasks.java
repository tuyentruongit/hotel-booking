package methodsecuritynew.bookingapp.utils;

import lombok.RequiredArgsConstructor;
import methodsecuritynew.bookingapp.entity.RoomPrice;
import methodsecuritynew.bookingapp.repository.RoomPriceRepository;
import methodsecuritynew.bookingapp.repository.RoomRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final RoomPriceRepository roomPriceRepository ;
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteRoomPrice (){
        LocalDate localDate = LocalDate.now().minusDays(-1);
        List<RoomPrice> roomPriceList = roomPriceRepository.findAllByDate(localDate);
        roomPriceRepository.deleteAll(roomPriceList);


    }

}
