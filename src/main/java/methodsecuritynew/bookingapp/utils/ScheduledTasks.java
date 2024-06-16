package methodsecuritynew.bookingapp.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import methodsecuritynew.bookingapp.entity.RoomPrice;
import methodsecuritynew.bookingapp.repository.RoomPriceRepository;
import methodsecuritynew.bookingapp.repository.RoomRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final RoomPriceRepository roomPriceRepository;

    public void deleteRoomPrice() {
        LocalDate localDate = LocalDate.now();
        List<RoomPrice> roomPriceList = roomPriceRepository.findAllByDateAfter(localDate);
        roomPriceRepository.deleteAll(roomPriceList);
        roomPriceRepository.deleteAll(roomPriceList);
    }

}
