package methodsecuritynew.bookingapp;


import com.github.slugify.Slugify;
import methodsecuritynew.bookingapp.entity.*;
import methodsecuritynew.bookingapp.model.enums.*;
import methodsecuritynew.bookingapp.repository.*;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BookingRepository bookingRepository;



//    @Test
//    void contextLoads() {
//        List<City> cityList = cityRepository.findAll();
//        String[] provinces = {
//                "Hà Giang", "Cao Bằng", "Bắc Kạn", "Tuyên Quang", "Lào Cai",
//                "Điện Biên", "Lai Châu", "Sơn La", "Yên Bái", "Hoà Bình",
//                "Thái Nguyên", "Lạng Sơn", "Quảng Ninh", "Bắc Giang", "Phú Thọ",
//                "Vĩnh Phúc", "Bắc Ninh", "Hà Nội", "Hải Dương", "Hải Phòng",
//                "Hưng Yên", "Thái Bình", "Hà Nam", "Nam Định", "Ninh Bình",
//                "Thanh Hóa", "Nghệ An", "Hà Tĩnh", "Quảng Bình", "Quảng Trị",
//                "Thừa Thiên Huế", "Đà Nẵng", "Quảng Nam", "Quảng Ngãi", "Bình Định",
//                "Phú Yên", "Khánh Hòa", "Ninh Thuận", "Bình Thuận", "Kon Tum",
//                "Gia Lai", "Đắk Lắk", "Đắk Nông", "Lâm Đồng", "Bình Phước",
//                "Tây Ninh", "Bình Dương", "Đồng Nai", "Bà Rịa - Vũng Tàu", "Hồ Chí Minh",
//                "Long An", "Tiền Giang", "Bến Tre", "Trà Vinh", "Vĩnh Long",
//                "Đồng Tháp", "An Giang", "Kiên Giang", "Cần Thơ", "Hậu Giang",
//                "Sóc Trăng", "Bạc Liêu", "Cà Mau"
//        };
////
////        for (int i = 0; i < 63; i++) {
////            cityList.get(i).setName(provinces[i]);
////            cityRepository.save(cityList.get(i));
////        }
////
////
//////        Faker faker = new Faker();
//////        hotelRepository.findAll().forEach(hotel -> {
//////            hotel.setRating((float) faker.number().randomDouble(1, 6, 10));
//////            hotelRepository.save(hotel);
//////        });
////
//  }


    // đã tạo
//    @Test
//    void createDataCity() {
//
//        Faker faker = new Faker();
//        String[] vietnamProvinces = {
//                "Hà Nội", "Hồ Chí Minh", "Hải Phòng", "Đà Nẵng", "Cần Thơ", "Hải Dương", "Hà Giang",
//                "Cao Bằng", "Lai Châu", "Lào Cai", "Tuyên Quang", "Lạng Sơn", "Bắc Kạn", "Thái Nguyên",
//                "Yên Bái", "Sơn La", "Phú Thọ", "Vĩnh Phúc", "Quảng Ninh", "Bắc Giang", "Bắc Ninh",
//                "Hòa Bình", "Hà Tĩnh", "Nghệ An", "Quảng Bình", "Quảng Trị", "Thừa Thiên Huế", "Quảng Nam",
//                "Kon Tum", "Gia Lai", "Đắk Lắk", "Đắk Nông", "Lâm Đồng", "Bình Phước", "Bình Dương",
//                "Đồng Nai", "Tây Ninh", "Vũng Tàu", "Long An", "Tiền Giang", "Bến Tre",
//                "Trà Vinh", "Vĩnh Long", "Đồng Tháp", "An Giang", "Kiên Giang", "Cần Thơ",
//                "Sóc Trăng", "Bạc Liêu", "Cà Mau", "Phú Yên", "Khánh Hòa", "Ninh Thuận",
//                "Bình Thuận", "Bắc Kạn", "Hà Nam", "Thái Bình", "Nam Định", "Hưng Yên",
//                "Thanh Hóa", "Ninh Bình", "Quảng Ngãi", "Quảng Trị"
//        };
//        for (int i = 0; i < 63; i++) {
//            City city = City.builder()
//                    .name(vietnamProvinces[i])
//                    .country("Việt Nam")
//                    .createdAt(LocalDate.now())
//                    .updatedAt(LocalDate.now())
//                    .build();
//            cityRepository.save(city);
//        }
//    }


    // đã tạo
//    @Test
//    void createDataPolicy() {
//
//        Faker faker = new Faker();
//        Random random = new Random();
//
//        for (int i = 0; i < 200; i++) {
//            String age = String.valueOf(random.nextInt(18, 80));
//            PolicyHotel policyHotel = PolicyHotel.builder()
//                    .checkIn(faker.date().future(30, TimeUnit.DAYS).toString())
//                    .checkOut(faker.date().future(30, TimeUnit.DAYS).toString())
//                    .service(faker.lorem().sentence())
//                    .cancelPolicy(faker.lorem().sentence())
//                    .animal(faker.lorem().word())
//                    .ageLimit(age)
//                    .note(faker.lorem().paragraph())
//                    .createdAt(LocalDate.now())
//                    .updatedAt(LocalDate.now())
//                    .build();
//            policyRepository.save(policyHotel);
//        }
//    }


    // đã tạo
//    @Test
//    void createDataHotel() {
//        Faker faker = new Faker();
//        Random random = new Random();
//
//        City city = cityRepository.findCityByName("Đà Nẵng");
//        List<City> list = cityRepository.findAll();
//        List<PolicyHotel> policyHotels = policyRepository.findAll();
//        List<User> userList = userRepository.findAllByUserRole(UserRole.ROLE_HOTEL);
//
//
//        for (int i = 0; i < 100; i++) {
//            List<AmenityHotel> amenityHotels1 = new ArrayList<>();
//            Hotel hotel = Hotel.builder()
//                    .name(faker.company().name())
//                    .email(faker.internet().emailAddress())
//                    .description(faker.lorem().paragraph())
//                    .address(faker.address().fullAddress())
//                    .city(list.get(random.nextInt(0,list.size())))
//                    .poster("/web/assets/image/dep.jpg")
////                    .city(city)
////                    .policyHotel(policyHotels.get(i))
////                    .user(userList.get(random.nextInt(userList.size())))
//                    .star(faker.number().numberBetween(1, 5))
//                    .hotline(faker.phoneNumber().phoneNumber())
//                    .rating((float) faker.number().randomDouble(1, 6, 10))
//                    .rentalType(RentalType.values()[random.nextInt(RentalType.values().length)])
//                    .createdAt(LocalDate.now())
//                    .updatedAt(LocalDate.now())
//                    .status(true)
//                    .build();
//
//            hotelRepository.save(hotel);
//        }
//    }
            // đã tạo
//    @Test
//    void createDataAmenityHotel() {
//
////        Faker faker = new Faker();
//        Random random = new Random();
////        List<Hotel> hotelList = hotelRepository.findAll();
////        int count = 0;
////
////        for (int i = 0; i < 300; i++) {
////            if (count < 300-1){
////                count ++;
////            }else {
////                count=0;
////            }
////
////            AmenityHotel amenityHotel = new AmenityHotel();
////            amenityHotel.setName(faker.company().name());
////            amenityHotel.setCreatedAt(LocalDate.now());
////            amenityHotel.setUpdateAt(LocalDate.now());
//////            amenityHotel.setHotel(hotelList.get(count));
////            amenityHotelRepository.save(amenityHotel);
////        }
//////
////        List<AmenityHotel> amenityHotelList  = amenityHotelRepository.findAll();
////        for (AmenityHotel amenityHotel : amenityHotelList){
////            amenityHotel.setAmenityHotelType(AmenityHotelType.values()[random.nextInt(AmenityHotelType.values().length)]);
////            amenityHotelRepository.save(amenityHotel);
////        }
////        List<AmenityRoom> amenityRoomList  = amenityRoomRepository.findAll();
////        for (AmenityRoom amenityRoom : amenityRoomList){
////            amenityRoom.setAmenityRoomType(AmenityRoomType.values()[random.nextInt(AmenityRoomType.values().length)]);
////            amenityRoomRepository.save(amenityRoom);
////        }
//    }

//    @Test
//    void createDataAmenityRoom() {
//
////        Faker faker = new Faker();
////        Random random = new Random();
////
////        for (int i = 0; i < 400; i++) {
////
////            AmenityRoom amenityRoom = new AmenityRoom();
////            amenityRoom.setName(faker.company().name());
////            amenityRoom.setCreatedAt(LocalDate.now());
////            amenityRoom.setUpdateAt(LocalDate.now());
////
////            amenityRoomRepository.save(amenityRoom);
////        }
////        System.out.println(amenityRoomRepository.findAll());
//
//        List<AmenityRoom> amenityRoomList = amenityRoomRepository.findAll();
//        List<Hotel> hotelList = hotelRepository.findAll();
//        Random random = new Random();
//       for (AmenityRoom amenityRoom : amenityRoomList){
//           amenityRoom.setHotel(hotelList.get(random.nextInt(hotelList.size())));
//           amenityRoomRepository.save(amenityRoom);
//       }
//    }

    // đã tạo
//    @Test
//    void createDataRoom() {
//        List<Hotel> hotelList = hotelRepository.findAll();
//
//        Faker faker = new Faker();
//        Random random = new Random();
//        List<AmenityRoom> amenityRoomList = amenityRoomRepository.findAll();
//        System.out.println(amenityRoomList);
//        for (int i = 0; i < 1000; i++) {
//            Hotel hotel = hotelList.get(random.nextInt(hotelList.size()));
//
////            List<AmenityRoom> amenityHotels1 = new ArrayList<>();
////            for (int j = 0; j < random.nextInt(5, 10) + 1; j++) {
////                int number = random.nextInt(0, amenityRoomList.size());
////                AmenityRoom amenityRoom = amenityRoomList.get(number);
////                if (!amenityHotels1.contains(amenityRoom)) {
////                    amenityHotels1.add(amenityRoom);
////                }
////            }
//            Room room = Room.builder()
//                    .name(faker.lorem().word())
//                    .description(faker.lorem().sentence())
//                    .capacity(faker.number().numberBetween(1, 10)) // Giả sử dung lượng từ 1 đến 10
//                    .area(faker.number().numberBetween(20, 50)) // Giả sử diện tích từ 20 đến 200
//                    .status(faker.bool().bool())
//                    .quantity(random.nextInt(1,7))
////                    .amenityRoomList(amenityHotels1)
//                    .roomType(RoomType.values()[random.nextInt(RoomType.values().length)])
//                    .bedSize(BedSize.values()[random.nextInt(BedSize.values().length)])
//                    .bedType(BedType.values()[random.nextInt(BedType.values().length)])
//                    .hotel(hotel)
//                    .createdAt(LocalDate.now())
//                    .updatedAt(LocalDate.now())
//                    .build();
//
//            roomRepository.save(room);
//        }
//
//    }

//
        // đã taaoj
//    @Test
//    void createDataSupport() {
//        Faker faker = new Faker();
//
//
//        Slugify slugify = Slugify.builder().build();
//
//
//        Random random = new Random();
//        for (int i = 0; i < 30; i++) {
//            String title = faker.lorem().word();
//
//            Support support = Support.builder()
//                    .title(title) // Tiêu đề ngẫu nhiên
//                    .description(faker.lorem().paragraph()) // Mô tả ngẫu nhiên
//                    .content(faker.lorem().paragraph(30)) // Nội dung ngẫu nhiên, 3 đoạn
//                    .slug(slugify.slugify(title)) // Slug ngẫu nhiên
//                    .status(faker.bool().bool()) // Trạng thái ngẫu nhiên (true/false)
//                    .createdAt(LocalDate.now())
//                    .updatedAt(LocalDate.now())
//                    .publishedAt(LocalDate.now())
//                    .supportType(SupportType.values()[random.nextInt(SupportType.values().length)]) // Loại hỗ trợ ngẫu nhiên từ enum SupportType
//                    .build();
//
//
//            supportRepository.save(support);
//        }
//    }


    // đã taạo
//    @Test
//    void createDataUser() {
//        Faker faker = new Faker();
//        Random random = new Random();
//        for (int i = 0; i < 200; i++) {
//            User user = User.builder()
//                    .name(faker.lorem().word())
//                    .email(faker.internet().emailAddress())
//                    .password(passwordEncoder.encode("123"))
//                    .userRole(UserRole.values()[random.nextInt(UserRole.values().length)])
//                    .birthDay(faker.date().birthdayLocalDate())
//                    .phoneNumber(faker.phoneNumber().phoneNumber())
//                    .gender(Gender.values()[random.nextInt(Gender.values().length)])
//                    .enable(true)
//                    .address(faker.address().fullAddress())
//                    .avatar("/web/assets/image/avata-default.jpg")
//                    .createdAt(LocalDate.now())
//                    .updateAt(LocalDate.now())
//                    .build();
//
//            userRepository.save(user);
//        }
//
//    }
//

//
    @Test
    void createDataBooking(){

        List<Booking> lis = bookingRepository.findAll();
        Random random = new Random();

        for (Booking bo : lis){
            bo.setStatusBooking(StatusBooking.values()[random.nextInt(StatusBooking.values().length)]);
            bookingRepository.save(bo);
        }


//
//        Faker faker = new Faker();
//        List<User> userList = userRepository.findAllByUserRole(UserRole.ROLE_USER);
//        Hotel hotel = hotelRepository.findById(2).get();
//        List<Room> roomList = roomRepository.findRoomByHotel_Id(hotel.getId());
//
//        for (int i = 0; i < 50; i++) {
//            Booking booking  = Booking.builder()
//                    .user(userList.get(random.nextInt(userList.size())))
//                    .hotel(hotel)
//                    .room(roomList.get(2))
//                    .nameCustomer(faker.name().name())
//                    .emailCustomer(faker.internet().emailAddress())
//                    .phoneCustomer(faker.phoneNumber().phoneNumber())
//                    .guests(random.nextInt(1,4))
//                    .numberRoom(random.nextInt(1,3))
//                    .price(random.nextInt(100000, 1000000))
//                    .checkIn(LocalDate.now().plusDays(2))
//                    .checkOut(LocalDate.now().plusDays(4))
//                    .paymentMethod(PaymentMethod.PAY_AT_ACCOMMODATION)
//                    .statusBooking(StatusBooking.values()[random.nextInt(StatusBooking.values().length)])
//                    .createAt(LocalDate.now())
//                    .build();
//
//            bookingRepository.save(booking);
//        }

    }

    @Test
    void abchdh (){
//        List<AmenityRoom> amenityRoomList = amenityRoomRepository.findAmenityRoomByHotel_Id(2);
//        List<Room> roomList = roomRepository.findRoomByHotel_Id(2);
        Random random = new Random();
//
//        for (Room room : roomList){
//            List<AmenityRoom> amenityRoomList1 = new ArrayList<>();
//            for (int i = 0; i < 7; i++) {
//                amenityRoomList1.add(amenityRoomList.get(random.nextInt(amenityRoomList.size())));
//            }
//            room.setAmenityRoomList(amenityRoomList1);
//            roomRepository.save(room);
//        }

//        List<Hotel> hotelList = hotelRepository.findAll();
//        List<User> userList  = userRepository.findAllByUserRole(UserRole.ROLE_HOTEL);
//        List<PolicyHotel> policyHotels = policyRepository.findAll();
//
//        for (int i = 0; i < userList.size(); i++) {
//            hotelList.get(i).setUser(userList.get(i));
//            hotelList.get(i).setPolicyHotel(policyHotels.get(i));
//            hotelRepository.save(hotelList.get(i));
//        }

        List<AmenityRoom> list = amenityRoomRepository.findAll();
        List<AmenityHotel> amenityHotelList = amenityHotelRepository.findAll();
        for (AmenityRoom amenityRoom : list){
            amenityRoom.setAmenityRoomType(AmenityRoomType.values()[random.nextInt(AmenityRoomType.values().length)]);
            amenityRoomRepository.save(amenityRoom);
        }
        for (AmenityHotel amenityHotel : amenityHotelList){
            amenityHotel.setAmenityHotelType(AmenityHotelType.values()[random.nextInt(AmenityHotelType.values().length)]);

        }

    }

}