package methodsecuritynew.bookingapp;

import methodsecuritynew.bookingapp.entity.City;
import methodsecuritynew.bookingapp.entity.ImageHotel;
import methodsecuritynew.bookingapp.entity.ImageRoom;
import methodsecuritynew.bookingapp.entity.PolicyHotel;
import methodsecuritynew.bookingapp.model.statics.ImageType;
import methodsecuritynew.bookingapp.repository.CityRepository;
import methodsecuritynew.bookingapp.repository.ImageHotelRepository;

import methodsecuritynew.bookingapp.repository.PolicyRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class BookingAppApplicationTests {
	@Autowired
	private ImageHotelRepository imageRepository;
	@Autowired
	private PolicyRepository policyRepository;
	@Autowired
	private CityRepository cityRepository;

	@Test
	void contextLoads() {
		Faker faker = new Faker();

	}
	@Test
	void createDataCity(){

		Faker faker = new Faker();
		String[] vietnamProvinces = {
				"Hà Nội", "Hồ Chí Minh", "Hải Phòng", "Đà Nẵng", "Cần Thơ", "Hải Dương", "Hà Giang",
				"Cao Bằng", "Lai Châu", "Lào Cai", "Tuyên Quang", "Lạng Sơn", "Bắc Kạn", "Thái Nguyên",
				"Yên Bái", "Sơn La", "Phú Thọ", "Vĩnh Phúc", "Quảng Ninh", "Bắc Giang", "Bắc Ninh",
				"Hòa Bình", "Hà Tĩnh", "Nghệ An", "Quảng Bình", "Quảng Trị", "Thừa Thiên Huế", "Quảng Nam",
				"Kon Tum", "Gia Lai", "Đắk Lắk", "Đắk Nông", "Lâm Đồng", "Bình Phước", "Bình Dương",
				"Đồng Nai", "Tây Ninh", "Bà Rịa - Vũng Tàu", "Long An", "Tiền Giang", "Bến Tre",
				"Trà Vinh", "Vĩnh Long", "Đồng Tháp", "An Giang", "Kiên Giang", "Cần Thơ",
				"Sóc Trăng", "Bạc Liêu", "Cà Mau", "Phú Yên", "Khánh Hòa", "Ninh Thuận",
				"Bình Thuận", "Bắc Kạn", "Hà Nam", "Thái Bình", "Nam Định", "Hưng Yên",
				"Thanh Hóa", "Ninh Bình", "Quảng Ngãi", "Quảng Trị"
		};
		for (int i = 0; i < 63 ; i++) {
			City city = City.builder()
					.name(vietnamProvinces[i])
					.country("Việt Nam")
					.createdAt(LocalDate.now())
					.updatedAt(LocalDate.now())
					.build();
			cityRepository.save(city);
		}
	}
	@Test
	void createDataPolicy(){

		Faker faker = new Faker();
		Random random = new Random();

		for (int i = 0; i < 30 ; i++) {
			String age = String.valueOf(random.nextInt(18,80));
			PolicyHotel	policyHotel= PolicyHotel.builder()
					.checkIn(faker.date().future(30, TimeUnit.DAYS).toString())
					.checkOut(faker.date().future(30, TimeUnit.DAYS).toString())
					.service(faker.lorem().sentence())
					.cancelPolicy(faker.lorem().sentence())
					.animal(faker.lorem().word())
					.ageLimit(age)
					.note(faker.lorem().paragraph())
					.createdAt(LocalDate.now())
					.updatedAt(LocalDate.now())
					.build();
			policyRepository.save(policyHotel);
		}
	}
	@Test
	void createDataHotel(){


		Faker faker = new Faker();
		Random random = new Random();

		for (int i = 0; i < 30 ; i++) {
			String age = String.valueOf(random.nextInt(18,80));
			PolicyHotel	policyHotel= PolicyHotel.builder()
					.checkIn(faker.date().future(30, TimeUnit.DAYS).toString())
					.checkOut(faker.date().future(30, TimeUnit.DAYS).toString())
					.service(faker.lorem().sentence())
					.cancelPolicy(faker.lorem().sentence())
					.animal(faker.lorem().word())
					.ageLimit(age)
					.note(faker.lorem().paragraph())
					.createdAt(LocalDate.now())
					.updatedAt(LocalDate.now())
					.build();
			policyRepository.save(policyHotel);
		}
	}


}
