package methodsecuritynew.bookingapp;

import methodsecuritynew.bookingapp.entity.Hotel;
import methodsecuritynew.bookingapp.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookingAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookingAppApplication.class, args);
	}
}
