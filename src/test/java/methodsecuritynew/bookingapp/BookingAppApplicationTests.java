package methodsecuritynew.bookingapp;


import com.github.slugify.Slugify;
import methodsecuritynew.bookingapp.entity.*;
import methodsecuritynew.bookingapp.model.statics.*;
import methodsecuritynew.bookingapp.repository.*;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private SupportRepository supportRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AmenityHotelRepository amenityHotelRepository;
    @Autowired
    private AmenityRoomRepository amenityRoomRepository;
    @Autowired
    private BedRepository bedRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    void contextLoads() {
        List<City> cityList = cityRepository.findAll();
        String[] provinces = {
                "Hà Giang", "Cao Bằng", "Bắc Kạn", "Tuyên Quang", "Lào Cai",
                "Điện Biên", "Lai Châu", "Sơn La", "Yên Bái", "Hoà Bình",
                "Thái Nguyên", "Lạng Sơn", "Quảng Ninh", "Bắc Giang", "Phú Thọ",
                "Vĩnh Phúc", "Bắc Ninh", "Hà Nội", "Hải Dương", "Hải Phòng",
                "Hưng Yên", "Thái Bình", "Hà Nam", "Nam Định", "Ninh Bình",
                "Thanh Hóa", "Nghệ An", "Hà Tĩnh", "Quảng Bình", "Quảng Trị",
                "Thừa Thiên Huế", "Đà Nẵng", "Quảng Nam", "Quảng Ngãi", "Bình Định",
                "Phú Yên", "Khánh Hòa", "Ninh Thuận", "Bình Thuận", "Kon Tum",
                "Gia Lai", "Đắk Lắk", "Đắk Nông", "Lâm Đồng", "Bình Phước",
                "Tây Ninh", "Bình Dương", "Đồng Nai", "Bà Rịa - Vũng Tàu", "Hồ Chí Minh",
                "Long An", "Tiền Giang", "Bến Tre", "Trà Vinh", "Vĩnh Long",
                "Đồng Tháp", "An Giang", "Kiên Giang", "Cần Thơ", "Hậu Giang",
                "Sóc Trăng", "Bạc Liêu", "Cà Mau"
        };

        for (int i = 0; i < 63; i++) {
            cityList.get(i).setName(provinces[i]);
            cityRepository.save(cityList.get(i));
        }


//        Faker faker = new Faker();
//        hotelRepository.findAll().forEach(hotel -> {
//            hotel.setRating((float) faker.number().randomDouble(1, 6, 10));
//            hotelRepository.save(hotel);
//        });

    }

    @Test
    void createDataCity() {

        Faker faker = new Faker();
        String[] vietnamProvinces = {
                "Hà Nội", "Hồ Chí Minh", "Hải Phòng", "Đà Nẵng", "Cần Thơ", "Hải Dương", "Hà Giang",
                "Cao Bằng", "Lai Châu", "Lào Cai", "Tuyên Quang", "Lạng Sơn", "Bắc Kạn", "Thái Nguyên",
                "Yên Bái", "Sơn La", "Phú Thọ", "Vĩnh Phúc", "Quảng Ninh", "Bắc Giang", "Bắc Ninh",
                "Hòa Bình", "Hà Tĩnh", "Nghệ An", "Quảng Bình", "Quảng Trị", "Thừa Thiên Huế", "Quảng Nam",
                "Kon Tum", "Gia Lai", "Đắk Lắk", "Đắk Nông", "Lâm Đồng", "Bình Phước", "Bình Dương",
                "Đồng Nai", "Tây Ninh", "Vũng Tàu", "Long An", "Tiền Giang", "Bến Tre",
                "Trà Vinh", "Vĩnh Long", "Đồng Tháp", "An Giang", "Kiên Giang", "Cần Thơ",
                "Sóc Trăng", "Bạc Liêu", "Cà Mau", "Phú Yên", "Khánh Hòa", "Ninh Thuận",
                "Bình Thuận", "Bắc Kạn", "Hà Nam", "Thái Bình", "Nam Định", "Hưng Yên",
                "Thanh Hóa", "Ninh Bình", "Quảng Ngãi", "Quảng Trị"
        };
        for (int i = 0; i < 63; i++) {
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
    void createDataPolicy() {

        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 300; i++) {
            String age = String.valueOf(random.nextInt(18, 80));
            PolicyHotel policyHotel = PolicyHotel.builder()
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
    void createDataHotel() {


        Faker faker = new Faker();
        Random random = new Random();

//        City city = cityRepository.findCityByName("Hà Nội");
        List<City> list = cityRepository.findAll();
        List<AmenityHotel> amenityHotels = amenityHotelRepository.findAll();


        for (int i = 0; i < 200; i++) {
            List<AmenityHotel> amenityHotels1 = new ArrayList<>();
            for (int j = 0; j < random.nextInt(5, 10) + 1; j++) {
                int number = random.nextInt(0, amenityHotels.size());
                AmenityHotel amenityHotel = amenityHotels.get(number);
                if (!amenityHotels1.contains(amenityHotel)) {
                    amenityHotels1.add(amenityHotel);
                }
            }
            Hotel hotel = Hotel.builder()
                    .name(faker.company().name())
                    .email(faker.internet().emailAddress())
                    .description(faker.lorem().paragraph())
                    .address(faker.address().fullAddress())
                    .city(list.get(random.nextInt(1,63)))
                    .policyHotel(policyRepository.findAll().get(40+i))
                    .star(faker.number().numberBetween(1, 5))
                    .hotline(faker.phoneNumber().phoneNumber())
                    .rating((float) faker.number().randomDouble(1, 6, 10))
                    .rentalType(RentalType.values()[random.nextInt(RentalType.values().length)])
                    .amenityHotelList(amenityHotels1)
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .status(true)
                    .build();

            hotelRepository.save(hotel);
        }
    }

    @Test
    void createDataAmenityHotel() {

        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 300; i++) {

            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(faker.company().name());
            amenityHotel.setIcon("<i class=\"fa-solid fa-icons\"></i>");
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotel.setUpdateAt(LocalDate.now());

            amenityHotelRepository.save(amenityHotel);
        }
    }

    @Test
    void createDataAmenityRoom() {

        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 300; i++) {

            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(faker.company().name());

            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoom.setUpdateAt(LocalDate.now());

            amenityRoomRepository.save(amenityRoom);
        }
    }

    @Test
    void createDataRoom() {

        List<Hotel> hotelList = hotelRepository.findAll();


        Faker faker = new Faker();
        Random random = new Random();
        List<AmenityRoom> amenityRoomList = amenityRoomRepository.findAll();
        for (int i = 0; i < 1000; i++) {
            List<AmenityRoom> amenityHotels1 = new ArrayList<>();
            for (int j = 0; j < random.nextInt(5, 10) + 1; j++) {
                int number = random.nextInt(0, amenityRoomList.size());
                AmenityRoom amenityRoom = amenityRoomList.get(number);
                if (!amenityHotels1.contains(amenityRoom)) {
                    amenityHotels1.add(amenityRoom);
                }
            }
            Room room = Room.builder()
                    .name(faker.lorem().word())
                    .description(faker.lorem().sentence())
                    .capacity(faker.number().numberBetween(1, 10)) // Giả sử dung lượng từ 1 đến 10
                    .area(faker.number().numberBetween(20, 50)) // Giả sử diện tích từ 20 đến 200
                    .status(faker.bool().bool())
                    .amenityRoomList(amenityHotels1)
                    .hotel(hotelList.get(random.nextInt(hotelList.size())))
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .build();


            roomRepository.save(room);
        }
    }

    @Test
    void createDataBed() {

        List<Hotel> hotelList = hotelRepository.findAll();

        Faker faker = new Faker();
        Random random = new Random();
        List<Room> roomList = roomRepository.findAll();
        for (int i = 0; i < 1000; i++) {

            Bed bed = Bed.builder()
                    .bedType(BedType.values()[random.nextInt(BedType.values().length)]) // Loại giường ngẫu nhiên từ enum BedType
                    .bedSize(BedSize.values()[random.nextInt(BedSize.values().length)]) // Kích thước giường ngẫu nhiên từ enum BedSize
                    .createAt(LocalDate.now())
                    .updateAt(LocalDate.now())
                    .build();



            bedRepository.save(bed);
        }
    }

    @Test
    void createDataSupport() {
        Faker faker = new Faker();


        Slugify slugify = Slugify.builder().build();


        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String title = faker.lorem().word();

            Support support = Support.builder()
                    .title(title) // Tiêu đề ngẫu nhiên
                    .description(faker.lorem().paragraph()) // Mô tả ngẫu nhiên
                    .content(faker.lorem().paragraph(30)) // Nội dung ngẫu nhiên, 3 đoạn
                    .slug(slugify.slugify(title)) // Slug ngẫu nhiên
                    .status(faker.bool().bool()) // Trạng thái ngẫu nhiên (true/false)
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .publishedAt(LocalDate.now())
                    .supportType(SupportType.values()[random.nextInt(SupportType.values().length)]) // Loại hỗ trợ ngẫu nhiên từ enum SupportType
                    .build();


            supportRepository.save(support);
        }
    }

    @Test
    void createDataUser() {
        Faker faker = new Faker();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            User user = User.builder()
                    .name(faker.lorem().word())
                    .email(faker.internet().emailAddress())
                    .password(passwordEncoder.encode("123"))
                    .userRole(UserRole.values()[random.nextInt(UserRole.values().length)])
                    .birthDay(faker.date().birthdayLocalDate())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .gender(Gender.values()[random.nextInt(Gender.values().length)])
                    .createdAt(LocalDate.now())
                    .updateAt(LocalDate.now())
                    .build();

            userRepository.save(user);
        }

    }


}