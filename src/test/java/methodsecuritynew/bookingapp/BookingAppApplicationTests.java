package methodsecuritynew.bookingapp;


import methodsecuritynew.bookingapp.entity.*;
import methodsecuritynew.bookingapp.model.enums.*;
import methodsecuritynew.bookingapp.repository.*;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Test
    void anem(){
//        Random random = new Random();
//        List<Room> roomList = roomRepository.findAll();
//        List<AmenityRoom> amenityRoomList = amenityRoomRepository.findAll();
//        for (Room room :roomList ){
//           List<AmenityRoom> small = new ArrayList<>();
//           for (int i = 0; i < random.nextInt(7,10); i++) {
//               small.add(amenityRoomList.get(random.nextInt(0,amenityRoomList.size())));
//
//           }
//            room.setAmenityRoomList(small);
//           roomRepository.save(room);
//       }


        List<Hotel> hotelList = hotelRepository.findAll();
        List<PolicyHotel> policyHotels = policyRepository.findAll();
        int i = 0;
        for (Hotel hotel : hotelList){
            hotel.setPolicyHotel(policyHotels.get(i));
            i++;
            hotelRepository.save(hotel);
        }

//        List<AmenityHotel> amenityHotelList = amenityHotelRepository.findAll();
//
//       for (Hotel hotel : hotelList){
//           List<AmenityHotel> small = new ArrayList<>();
//           for (int i = 0; i < random.nextInt(5,8); i++) {
//               small.add(amenityHotelList.get(random.nextInt(0,amenityHotelList.size())));
//
//           }
//           hotel.setAmenityHotelList(small);
//           hotelRepository.save(hotel);
//       }
    }


    @Test
    void createData() {
        String[] hotelNames = {
                "Rex Hotel",
                "Caravelle Saigon",
                "Majestic Saigon",
                "Sheraton Saigon Hotel & Towers",
                "Continental Saigon",
                "InterContinental Saigon",
                "Park Hyatt Saigon",
                "New World Saigon",
                "Liberty Central Saigon Riverside",
                "Liberty Central Saigon Citypoint",
                "Silverland Yen Hotel",
                "The Myst Dong Khoi",
                "Hotel Nikko Saigon",
                "Pullman Saigon Centre",
                "Sofitel Saigon Plaza",
                "Fusion Suites Saigon",
                "Norfolk Mansion",
                "Renaissance Riverside Hotel Saigon",
                "Novotel Saigon Centre",
                "Hotel Majestic",
                "Saigon Prince Hotel",
                "Lotte Legend Hotel Saigon",
                "The Reverie Saigon",
                "Windsor Plaza Hotel",
                "Sofitel Legend Metropole Hanoi",
                "Hanoi Daewoo Hotel",
                "Hilton Hanoi Opera",
                "Pan Pacific Hanoi",
                "Melia Hanoi",
                "Movenpick Hotel Hanoi",
                "InterContinental Hanoi Westlake",
                "JW Marriott Hotel Hanoi",
                "Lotte Hotel Hanoi",
                "Apricot Hotel",
                "The Oriental Jade Hotel",
                "Peridot Grand Hotel & Spa",
                "La Siesta Premium Hang Be",
                "Hanoi La Siesta Hotel & Spa",
                "Hanoi Emerald Waters Hotel & Spa",
                "Hanoi Pearl Hotel",
                "Golden Sun Villa Hotel",
                "Hanoi E Central Hotel",
                "Soleil Boutique Hotel Hanoi",
                "Dal Vostro Hotel & Spa",
                "Hanoi Marvellous Hotel & Spa",
                "Silk Path Boutique Hanoi",
                "The Light Hotel",
                "The Noble Swan Hotel & Spa",
                "Anise Hotel",
                "Imperial Hotel Hue",
                "Indochine Palace",
                "Saigon Morin Hotel",
                "Hotel Saigon Morin",
                "Moonlight Hotel Hue",
                "Eldora Hotel",
                "Cherish Hotel",
                "Huong Giang Hotel Resort & Spa",
                "Park View Hotel",
                "Muong Thanh Holiday Hue Hotel",
                "Duy Tan Hotel",
                "Vinpearl Hotel Hue",
                "Asia Hotel Hue",
                "Gold Hotel",
                "Thanh Lich Royal Boutique Hotel",
                "Green Hotel Hue",
                "Alba Spa Hotel",
                "Rosaleen Boutique Hotel",
                "Valentine Hotel",
                "Villa Hue Hotel",
                "White Lotus Hotel",
                "Alba Hotel",
                "Mondial Hotel Hue",
                "Vina Hotel Hue",
                "Orchid Hotel",
                "Phu Thinh Boutique Resort & Spa",
                "Almanity Hoi An Wellness Resort",
                "Little Hoi An Central Boutique Hotel & Spa",
                "Hoi An Silk Marina Resort & Spa",
                "Lantana Riverside Hoi An Boutique Hotel & Spa",
                "Belle Maison Hadana Hoi An Resort & Spa",
                "Allegro Hoi An . A Little Luxury Hotel & Spa",
                "Lasenta Boutique Hotel Hoian",
                "Sunrise Premium Resort Hoi An",
                "Little Riverside Hoi An . A Luxury Hotel & Spa",
                "La Residencia . A Little Boutique Hotel & Spa",
                "TTC Hotel Premium Hoi An",
                "Hoi An Silk Village Resort & Spa",
                "Essence Hoi An Hotel & Spa",
                "La Siesta Hoi An Resort & Spa",
                "Royal Riverside Hoi An Hotel",
                "Lantana Boutique Hotel Hoi An",
                "Vinh Hung Riverside Resort & Spa",
                "Lantana Riverside Hoi An Boutique Hotel & Spa",
                "Mulberry Collection Silk Marina",
                "Emm Hotel Hoi An",
                "Aurora Riverside Hotel & Villas",
                "Muca Hoi An Boutique Resort & Spa",
                "Phu Thinh Boutique Resort & Spa",
                "Almanity Hoi An Wellness Resort",
                "Little Hoi An Central Boutique Hotel & Spa",
                "Hoi An Silk Marina Resort & Spa",
                "Lantana Riverside Hoi An Boutique Hotel & Spa",
                "Belle Maison Hadana Hoi An Resort & Spa",
                "Allegro Hoi An . A Little Luxury Hotel & Spa",
                "Lasenta Boutique Hotel Hoian",
                "Sunrise Premium Resort Hoi An",
                "Little Riverside Hoi An . A Luxury Hotel & Spa",
                "La Residencia . A Little Boutique Hotel & Spa",
                "TTC Hotel Premium Hoi An",
                "Hoi An Silk Village Resort & Spa",
                "Essence Hoi An Hotel & Spa",
                "La Siesta Hoi An Resort & Spa",
                "Royal Riverside Hoi An Hotel",
                "Lantana Boutique Hotel Hoi An",
                "Vinh Hung Riverside Resort & Spa",
                "Lantana Riverside Hoi An Boutique Hotel & Spa",
                "Mulberry Collection Silk Marina",
                "Emm Hotel Hoi An",
                "Aurora Riverside Hotel & Villas",
                "Muca Hoi An Boutique Resort & Spa",
                "Naman Retreat Da Nang",
                "Hyatt Regency Danang Resort & Spa",
                "InterContinental Danang Sun Peninsula Resort",
                "Furama Resort Danang",
                "Vinpearl Luxury Da Nang",
                "Pullman Danang Beach Resort",
                "Grand Mercure Danang",
                "Hilton Da Nang",
                "Novotel Danang Premier Han River",
                "Four Points by Sheraton Danang",
                "Altara Suites",
                "Monarque Hotel Danang",
                "TMS Hotel Da Nang Beach",
                "Hadana Boutique Hotel Danang",
                "Titan Hotel Da Nang",
                "Haka Hotel & Apartment",
                "Danang Golden Bay",
                "Sanouva Danang Hotel",
                "Vanda Hotel",
                "Brilliant Hotel",
                "Orange Hotel",
                "Gopatel Hotel & Spa",
                "Seahorse Hotel & Office by HAVI",
                "Pariat Hotel Danang",
                "Samdi Hotel",
                "Satya Danang Hotel",
                "Stay Hotel",
                "Lion Sea Hotel",
                "Sea Phoenix Hotel",
                "Belle Maison Parosand Danang",
                "Fivitel Boutique Da Nang",
                "Balcona Hotel Da Nang",
                "Avatar Danang Hotel",
                "Risemount Premier Resort Danang",
                "Belle Maison Parosand Danang",
                "Blossom City Hotel",
                "Luna Diamond Hotel",
                "Azumaya Hotel Da Nang",
                "Senriver Hotel",
                "Palazzo Hotel & Apartment",
                "Hoang Dai 2 Hotel Da Nang",
                "Da Nang - Mikazuki Japanese Resorts & Spa",
                "Hotel Royal Hoi An - MGallery",
                "Muong Thanh Luxury Nha Trang Hotel",
                "InterContinental Nha Trang",
                "Sheraton Nha Trang Hotel & Spa",
                "Sunrise Nha Trang Beach Hotel & Spa",
                "Mia Resort Nha Trang",
                "Amiana Resort and Villas Nha Trang",
                "Novotel Nha Trang",
                "Evason Ana Mandara Nha Trang",
                "Vinpearl Resort Nha Trang",
                "Duyen Ha Resort Cam Ranh",
                "Cam Ranh Riviera Beach Resort & Spa",
                "The Anam",
                "Movenpick Resort Cam Ranh",
                "Radisson Blu Resort Cam Ranh",
                "Fusion Resort Cam Ranh",
                "Golden Peak Resort & Spa",
                "Swandor Cam Ranh Resort",
                "Alma Resort Cam Ranh",
                "An Lam Retreats Ninh Van Bay",
                "Six Senses Ninh Van Bay",
                "Wild Beach Resort & Spa",
                "Whale Island Resort",
                "Diamond Bay Resort & Spa",
                "MerPerle Hon Tam Resort",
                "Vinpearl Luxury Nha Trang",
                "Sheraton Nha Trang Hotel & Spa",
                "Sunrise Nha Trang Beach Hotel & Spa",
                "Mia Resort Nha Trang",
                "Amiana Resort and Villas Nha Trang",
                "Novotel Nha Trang",
                "Evason Ana Mandara Nha Trang",
                "Vinpearl Resort Nha Trang",
                "Duyen Ha Resort Cam Ranh",
                "Cam Ranh Riviera Beach Resort & Spa",
                "The Anam",
                "Movenpick Resort Cam Ranh",
                "Radisson Blu Resort Cam Ranh",
                "Fusion Resort Cam Ranh",
                "Golden Peak Resort & Spa",
                "Swandor Cam Ranh Resort",
                "Alma Resort Cam Ranh",
                "An Lam Retreats Ninh Van Bay",
                "Six Senses Ninh Van Bay",
                "Wild Beach Resort & Spa",
                "Whale Island Resort",
                "Diamond Bay Resort & Spa",
                "MerPerle Hon Tam Resort"
        };
        String[] phoneNumbers = {
                "0351234567", "0351234568", "0351234569", "0351234570", "0351234571",
                "0351234572", "0351234573", "0351234574", "0351234575", "0351234576",
                "0351234577", "0351234578", "0351234579", "0351234580", "0351234581",
                "0351234582", "0351234583", "0351234584", "0351234585", "0351234586",
                "0351234587", "0351234588", "0351234589", "0351234590", "0351234591",
                "0351234592", "0351234593", "0351234594", "0351234595", "0351234596",
                "0351234597", "0351234598", "0351234599", "0351234600", "0351234601",
                "0351234602", "0351234603", "0351234604", "0351234605", "0351234606",
                "0351234607", "0351234608", "0351234609", "0351234610", "0351234611",
                "0351234612", "0351234613", "0351234614", "0351234615", "0351234616",
                "0351234617", "0351234618", "0351234619", "0351234620", "0351234621",
                "0351234622", "0351234623", "0351234624", "0351234625", "0351234626",
                "0351234627", "0351234628", "0351234629", "0351234630", "0351234631",
                "0351234632", "0351234633", "0351234634", "0351234635", "0351234636",
                "0351234637", "0351234638", "0351234639", "0351234640", "0351234641",
                "0351234642", "0351234643", "0351234644", "0351234645", "0351234646",
                "0351234647", "0351234648", "0351234649", "0351234650", "0351234651",
                "0351234652", "0351234653", "0351234654", "0351234655", "0351234656",
                "0351234657", "0351234658", "0351234659", "0351234660", "0351234661",
                "0351234662", "0351234663", "0351234664", "0351234665", "0351234666",
                "0351234667", "0351234668", "0351234669", "0351234670", "0351234671",
                "0351234672", "0351234673", "0351234674", "0351234675", "0351234676",
                "0351234677", "0351234678", "0351234679", "0351234680", "0351234681",
                "0351234682", "0351234683", "0351234684", "0351234685", "0351234686",
                "0351234687", "0351234688", "0351234689", "0351234690", "0351234691",
                "0351234692", "0351234693", "0351234694", "0351234695", "0351234696",
                "0351234697", "0351234698", "0351234699", "0341234567", "0341234568",
                "0341234569", "0341234570", "0341234571", "0341234572", "0341234573",
                "0341234574", "0341234575", "0341234576", "0341234577", "0341234578",
                "0341234579", "0341234580", "0341234581", "0341234582", "0341234583",
                "0341234584", "0341234585", "0341234586", "0341234587", "0341234588",
                "0341234589", "0341234590", "0341234591", "0341234592", "0341234593",
                "0341234594", "0341234595", "0341234596", "0341234597", "0341234598",
                "0341234599", "0341234600", "0341234601", "0341234602", "0341234603",
                "0341234604", "0341234605", "0341234606", "0341234607", "0341234608",
                "0341234609", "0341234610", "0341234611", "0341234612", "0341234613",
                "0341234614", "0341234615", "0341234616", "0341234617", "0341234618",
                "0341234619", "0341234620", "0341234621", "0341234622", "0341234623",
                "0341234624", "0341234625", "0341234626", "0341234627", "0341234628",
                "0341234629", "0341234630", "0341234631", "0341234632", "0341234633",
                "0341234634", "0341234635", "0341234636", "0341234637", "0341234638",
                "0341234639", "0341234640", "0341234641", "0341234642", "0341234643",
                "0341234644", "0341234645", "0341234646", "0341234647", "0341234648",
                "0341234649", "0341234650", "0341234651", "0341234652", "0341234653",
                "0341234654", "0341234655", "0341234656", "0341234657", "0341234658",
                "0341234659", "0341234660", "0341234661", "0341234662", "0341234663",
                "0341234664", "0341234665", "0341234666", "0341234667", "0341234668",
                "0341234669", "0341234670", "0341234671", "0341234672", "0341234673",
                "0341234674", "0341234675", "0341234676", "0341234677", "0341234678",
                "0341234679", "0341234680", "0341234681", "0341234682", "0341234683",
                "0341234684", "0341234685", "0341234686", "0341234687", "0341234688",
                "0341234689", "0341234690", "0341234691", "0341234692", "0341234693",
                "0341234694", "0341234695", "0341234696", "0341234697", "0341234698",
                "0341234699"
        };

        List<PolicyHotel> policyHotels = policyRepository.findAll();
        String[] addresses = {
                "15A Hoàng Hoa Thám, Ba Đình, Hà Nội",
                "20 Nguyễn Văn Ngọc, Ba Đình, Hà Nội",
                "35 Phan Đình Phùng, Ba Đình, Hà Nội",
                "8A Điện Biên Phủ, Ba Đình, Hà Nội",
                "10 Phạm Hùng, Cầu Giấy, Hà Nội",
                "25 Lạc Long Quân, Tây Hồ, Hà Nội",
                "42 Đội Cấn, Ba Đình, Hà Nội",
                "17 Huỳnh Thúc Kháng, Đống Đa, Hà Nội",
                "3A Văn Cao, Ba Đình, Hà Nội",
                "56 Trần Phú, Ba Đình, Hà Nội",
                "91 Nguyễn Chí Thanh, Đống Đa, Hà Nội",
                "18 Huế, Hai Bà Trưng, Hà Nội",
                "5A Nguyễn Tri Phương, Ba Đình, Hà Nội",
                "27B Nguyễn Huy Tưởng, Thanh Xuân, Hà Nội",
                "63 Đào Tấn, Ba Đình, Hà Nội",
                "12A Lý Nam Đế, Hoàn Kiếm, Hà Nội",
                "38 Quang Trung, Hoàn Kiếm, Hà Nội",
                "9B Phan Kế Bính, Ba Đình, Hà Nội",
                "22 Nguyễn Bỉnh Khiêm, Hai Bà Trưng, Hà Nội",
                "7C Ngô Quyền, Hoàn Kiếm, Hà Nội"
        };




        City city = cityRepository.findCityByName("Hồ Chí Minh");
        Random random = new Random();
        Faker faker = new Faker();

        List<User> list = userRepository.findAllByUserRole(UserRole.ROLE_HOTEL);

        for (int i = 0; i  < 20 ; i++) {
            Hotel hotel = Hotel.builder()
                    .name(hotelNames[i + 120])
                    .email(faker.internet().emailAddress())
                    .description(faker.lorem().paragraph())
                    .address(addresses[i])
                    .city(city)
                    .poster("/web/assets/image/dep.jpg")
//                    .policyHotel(policyHotels.get(i))
                    .user(list.get(i +100 ))
                    .star(faker.number().numberBetween(1, 5))
                    .hotline(phoneNumbers[i +100])
                    .rating((float) faker.number().randomDouble(1, 6, 10))
                    .rentalType(RentalType.values()[random.nextInt(RentalType.values().length)])
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .status(true)
                    .build();
            hotelRepository.save(hotel);
        }


//        try {
//            Document doc = Jsoup.connect("https://www.expedia.com.vn/Hanoi-Khach-San-Movenpick-Hotel-Hanoi.h481549.Thong-tin-khach-san?chkin=2024-07-09&chkout=2024-07-11&x_pwa=1&rfrr=HSR&pwa_ts=1720526619095&referrerUrl=aHR0cHM6Ly93d3cuZXhwZWRpYS5jb20udm4vSG90ZWwtU2VhcmNo&useRewards=true&rm1=a2&regionId=6054458&destination=H%C3%A0%20N%E1%BB%99i%2C%20Vi%C3%AA%CC%A3t%20Nam&destType=MARKET&neighborhoodId=6054471&latLong=21.035911%2C105.839431&sort=RECOMMENDED&top_dp=3000000&top_cur=VND&userIntent=&selectedRoomType=320849729&selectedRatePlan=387336180&searchId=d8527df5-0a76-4c8e-b3e6-71d9835bdfb6&propertyUnitRoomInfoDialog=room-info-320849724-1").get();
//            Element nameHotel = doc.selectFirst("h4[class=uitk-heading-4]");//
//            Element address = doc.selectFirst("div[data-stid=content-hotel-address]");
//            Element description = doc.selectFirst("div[class=uitk-text-default-theme]");///
//            Element imgimg = doc.selectFirst("img[class=uitk-image-media]");
//            Elements star = doc.select("svg[class=uitk-rating-icon]");
//            Element elementsRating = doc.selectFirst("h1[class=uitk-badge-base-text]");
//            Hotel hotel = new Hotel();
//            hotel.setStar(star.size());
//            hotel.setRating(Float.valueOf(elementsRating.text()));
//            hotel.setName(nameHotel.text());
//            hotel.setDescription(description.text());
//            hotel.setAddress(address.text());
//            hotel.setHotline("034" + random.nextInt(1000000, 9999999));
//            hotel.setRentalType(RentalType.values()[random.nextInt(RentalType.values().length)]);
//            hotel.setCreatedAt(LocalDate.now());
//            hotel.setStatus(true);
//            hotel.setCity(city);
//            hotel.setPoster(imgimg.attr("src"));
//            hotelRepository.save(hotel);
//            Elements nameRoom = doc.select("h3[class=uitk-heading-6]");
//            Elements nameroomSmal = (Elements) nameRoom.subList(4, nameRoom.size());
//            for (Element nameroom : nameroomSmal) {
//                Element imgRo = doc.selectFirst("img[importance=low]");
//                Room room = new Room();
//                room.setName(nameroom.text());
//                room.setHotel(hotel);
//                room.setName(nameHotel.text());
//                room.setDescription("Phòng Deluxe được thiết kế tinh tế sang trọng nội thất đầy đủ tiện nghi hiện đại, cửa sổ kính rộng thoáng bao quát toàn cảnh thành phố mang đến cho bạn một không gian thanh bình và dễ chịu sẽ là sự lựa chọn cho những doanh nhân và khách du lịch.");
//                room.setCapacity(random.nextInt(1, 4));
//                room.setRoomType(RoomType.values()[random.nextInt(RoomType.values().length)]);
//                room.setCreatedAt(LocalDate.now());
//                room.setArea(random.nextInt(15, 35));
//                room.setQuantity(random.nextInt(1, 5));
//                room.setBedType(BedType.values()[random.nextInt(BedType.values().length)]);
//                room.setBedSize(BedSize.values()[random.nextInt(BedSize.values().length)]);
//                room.setStatus(true);
//                room.setPriceDefault(random.nextInt(300000, 1000000));
//                room.setThumbnailRoom(imgimg.attr("src"));
//                room.setStatus(true);
//                room.setCreatedAt(LocalDate.now());
//                roomRepository.save(room);
//            }
//        } catch (IOException e) {
//            System.out.println("Lỗi");
//        }
    }


    @Test
    void createAmenytiHotel() {
        String[] restaurantFeatures = {
                "Nhà hàng buffet", "Thực đơn đặc biệt", "Phục vụ phòng ăn uống", "Có phục vụ bữa sáng"
        };

        String[] internetFeatures = {
                "Wifi miễn phí"
        };

        String[] fitnessCenterFeatures = {
                "Phòng tập gym"
        };

        String[] swimmingPoolFeatures = {
                "Hồ bơi"
        };

        String[] spaFeatures = {
                "Spa và massage"
        };

        String[] playgroundFeatures = {
                "Sân chơi"
        };

        String[] otherFeatures = {
                "Bar và quầy rượu", "Dịch vụ phòng 24/7", "Tiệc cưới và hội nghị", "Đưa đón sân bay", "Tour du lịch và thuê xe"
        };
        Random random = new Random();
        for (int i = 0; i < restaurantFeatures.length; i++) {
            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(restaurantFeatures[i]);
            amenityHotel.setAmenityHotelType(AmenityHotelType.RESTAURANT);
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotelRepository.save(amenityHotel);
        }
        for (int i = 0; i < internetFeatures.length; i++) {
            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(internetFeatures[i]);
            amenityHotel.setAmenityHotelType(AmenityHotelType.INTERNET);
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotelRepository.save(amenityHotel);
        }
        for (int i = 0; i < fitnessCenterFeatures.length; i++) {
            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(fitnessCenterFeatures[i]);
            amenityHotel.setAmenityHotelType(AmenityHotelType.INTERNET);
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotelRepository.save(amenityHotel);
        }
        for (int i = 0; i < swimmingPoolFeatures.length; i++) {
            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(swimmingPoolFeatures[i]);
            amenityHotel.setAmenityHotelType(AmenityHotelType.SWIMMING_POOL);
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotelRepository.save(amenityHotel);
        }
        for (int i = 0; i < spaFeatures.length; i++) {
            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(spaFeatures[i]);
            amenityHotel.setAmenityHotelType(AmenityHotelType.SPA);
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotelRepository.save(amenityHotel);
        }
        for (int i = 0; i < playgroundFeatures.length; i++) {
            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(playgroundFeatures[i]);
            amenityHotel.setAmenityHotelType(AmenityHotelType.PLAYGROUND);
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotelRepository.save(amenityHotel);
        }
        for (int i = 0; i < otherFeatures.length; i++) {
            AmenityHotel amenityHotel = new AmenityHotel();
            amenityHotel.setName(otherFeatures[i]);
            amenityHotel.setAmenityHotelType(AmenityHotelType.OTHER);
            amenityHotel.setCreatedAt(LocalDate.now());
            amenityHotelRepository.save(amenityHotel);
        }

    }

    @Test
    void createAmenytiRoom() {
        // Khai báo và khởi tạo mảng các tiện ích theo từng loại
        String[] bathroomAmenities = {
                "Áo choàng tắm", "Đồ dùng nhà tắm miễn phí", "Máy sấy tóc",
                "Phòng tắm riêng", "Vòi sen phun mưa (cố định)",
                "Bồn tắm và buồng tắm vòi sen riêng", "Dép đi trong nhà", "Khăn tắm"
        };

        String[] bedroomAmenities = {
                "Máy điều hòa nhiệt độ", "Không có bộ trải giường", "Không có nôi (cũi)/giường cho trẻ sơ sinh"
        };

        String[] entertainmentAmenities = {
                "Truyền hình cáp", "TV màn hình phẳng"
        };

        String[] diningAmenities = {
                "Dụng cụ pha cà phê/trà", "Nước đóng chai miễn phí", "Minibar",
                "Dịch vụ phòng (giới hạn thời gian)"
        };

        String[] internetAmenities = {
                "Wifi miễn phí", "Truy cập Internet có dây miễn phí"
        };

        String[] otherAmenities = {
                "Dịch vụ dọn phòng hàng ngày", "Bàn", "Bàn ủi/dụng cụ ủi quần áo",
                "Khu vực làm việc phù hợp cho laptop", "Không cửa sổ", "Điện thoại",
                "Két an toàn", "Phòng cách âm"
        };
        Random random = new Random();
        for (int i = 0; i < bathroomAmenities.length; i++) {
            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(bathroomAmenities[i]);
            amenityRoom.setAmenityRoomType(AmenityRoomType.BATHROOM);
            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoomRepository.save(amenityRoom);
        }
        for (int i = 0; i < bedroomAmenities.length; i++) {
            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(bedroomAmenities[i]);
            amenityRoom.setAmenityRoomType(AmenityRoomType.BEDROOM);
            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoomRepository.save(amenityRoom);
        }

        for (int i = 0; i < entertainmentAmenities.length; i++) {
            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(entertainmentAmenities[i]);
            amenityRoom.setAmenityRoomType(AmenityRoomType.ENTERTAINMENT);
            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoomRepository.save(amenityRoom);
        }

        for (int i = 0; i < diningAmenities.length; i++) {
            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(diningAmenities[i]);
            amenityRoom.setAmenityRoomType(AmenityRoomType.KITCHEN);
            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoomRepository.save(amenityRoom);
        }

        for (int i = 0; i < internetAmenities.length; i++) {
            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(internetAmenities[i]);
            amenityRoom.setAmenityRoomType(AmenityRoomType.INTERNET);
            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoomRepository.save(amenityRoom);
        }

        for (int i = 0; i < otherAmenities.length; i++) {
            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(otherAmenities[i]);
            amenityRoom.setAmenityRoomType(AmenityRoomType.OTHERS);
            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoomRepository.save(amenityRoom);
        }

    }

    @Test
    void creatUser() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        User user = User.builder()
                .name("Trương Văn Tuyên")
                .email("tuyen@gmail.com")
                .password(passwordEncoder.encode("tuyen123"))
                .avatar("/assets/image/avata-default.jpg")
                .userRole(UserRole.ROLE_ADMIN)
                .birthDay(LocalDate.parse("23/10/2001", dateTimeFormatter))
                .phoneNumber("0352075192")
                .address("Xuân khê , Lý Nhân  , Hà Nam")
                .gender(Gender.MALE)
                .enable(true)
                .createdAt(LocalDate.now())
                .build();
        userRepository.save(user);
    }

    // đã taạo
    @Test
    void createDataUser() {
        Faker faker = new Faker();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            User user = User.builder()
                    .name(faker.lorem().word())
                    .email(faker.internet().emailAddress())
                    .password(passwordEncoder.encode("tuyen123"))
                    .userRole(UserRole.ROLE_HOTEL)
                    .birthDay(faker.date().birthdayLocalDate())
                    .phoneNumber(faker.phoneNumber().phoneNumber())
                    .gender(Gender.values()[random.nextInt(Gender.values().length)])
                    .enable(true)
                    .address(faker.address().fullAddress())
                    .avatar("/web/assets/image/avata-default.jpg")
                    .createdAt(LocalDate.now())
                    .updateAt(LocalDate.now())
                    .build();

            userRepository.save(user);
        }

    }


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
    }


    // đã tạo
    @Test
    void createDataCity() {
        Faker faker = new Faker();
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
            City city = City.builder()
                    .name(provinces[i])
                    .country("Việt Nam")
                    .imageCity("/upload_image/image_city/1717777240306")
                    .createdAt(LocalDate.now())
                    .updatedAt(LocalDate.now())
                    .build();
            cityRepository.save(city);
        }
    }


    // đã tạo
    @Test
    void createDataPolicy() {

        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < 200; i++) {
            String age = String.valueOf(random.nextInt(18, 80));
            PolicyHotel policyHotel = PolicyHotel.builder()
                    .startCheckIn("05:00")
                    .startCheckOut("14:00")
                    .endCheckIn("12:00")
                    .endCheckOut("20:00")
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
    @Test
    void createDataAmenityHotel() {

        Faker faker = new Faker();
        Random random = new Random();
        List<Hotel> hotelList = hotelRepository.findAll();
        List<Room> roomList = roomRepository.findAll();
        int count = 0;

//        for (int i = 0; i < 100; i++) {
//            List<Hotel> hotelList1 = new ArrayList<>();
//            for (int j = 0; j < 10; j++) {
//                hotelList1.add(hotelList.get(random.nextInt(hotelList.size())));
//            }
//
//            AmenityHotel amenityHotel = new AmenityHotel();
//            amenityHotel.setName(faker.company().name());
//            amenityHotel.setCreatedAt(LocalDate.now());
//            amenityHotel.setUpdateAt(LocalDate.now());
//            amenityHotelRepository.save(amenityHotel);
//        }
//

        List<AmenityHotel> amenityHotelList = amenityHotelRepository.findAll();
        for (int i = 0; i < amenityHotelList.size(); i++) {
            amenityHotelList.get(i).setAmenityHotelType(AmenityHotelType.values()[random.nextInt(AmenityHotelType.values().length)]);

            amenityHotelRepository.save(amenityHotelList.get(i));
        }
//        List<AmenityRoom> amenityRoomList  = amenityRoomRepository.findAll();
//        for (int i = 0; i < amenityRoomList.size(); i++) {
//            List<AmenityRoom> amenityRoomList1 = new ArrayList<>();
//            for (int j = 0; j < random.nextInt(5,9); j++) {
//                amenityRoomList1.add(amenityRoomList.get(random.nextInt(amenityHotelList.size())));
//            }
//            roomList.get(i).setAmenityRoomList(amenityRoomList1);
//            roomRepository.save(roomList.get(i));
//        }
    }

    @Test
    void createDataAmenityRoom() {

        Faker faker = new Faker();
        Random random = new Random();

        List<Room> roomList = roomRepository.findAll();

        for (int i = 0; i < 100; i++) {
            AmenityRoom amenityRoom = new AmenityRoom();
            amenityRoom.setName(faker.company().name());
            amenityRoom.setCreatedAt(LocalDate.now());
            amenityRoom.setUpdateAt(LocalDate.now());
            amenityRoom.setAmenityRoomType(AmenityRoomType.values()[random.nextInt(AmenityRoomType.values().length)]);
            amenityRoomRepository.save(amenityRoom);
        }

//        List<AmenityRoom> amenityRoomList = amenityRoomRepository.findAll();
//        List<Hotel> hotelList = hotelRepository.findAll();
//        Random random = new Random();
//       for (AmenityRoom amenityRoom : amenityRoomList){
//           amenityRoom.setHotel(hotelList.get(random.nextInt(hotelList.size())));
//           amenityRoomRepository.save(amenityRoom);
//       }
    }

    // đã tạo
    @Test
    void createDataRoom() {
//        List<Hotel> hotelList = hotelRepository.findHotelByCity_Id(18);
//        Faker faker = new Faker();
//        Random random = new Random();
//        List<AmenityRoom> amenityRoomList = amenityRoomRepository.findAll();
//        System.out.println(amenityRoomList);
//        for (int i = 0; i < 800; i++) {
//            Hotel hotel = hotelList.get(random.nextInt(hotelList.size()));
//
//            List<AmenityRoom> amenityHotels1 = new ArrayList<>();
//            for (int j = 0; j < random.nextInt(5, 10) + 1; j++) {
//                int number = random.nextInt(0, amenityRoomList.size());
//                AmenityRoom amenityRoom = amenityRoomList.get(number);
//                if (!amenityHotels1.contains(amenityRoom)) {
//                    amenityHotels1.add(amenityRoom);
//                }
//            }
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
//                    .thumbnailRoom("https://indec.vn/wp-content/uploads/2022/08/image4-8.jpg")
//                    .priceDefault(random.nextInt(300000,0))
//                    .createdAt(LocalDate.now())
//                    .updatedAt(LocalDate.now())
//                    .build();
//
//            roomRepository.save(room);
//        }
        Random ne = new Random();
        List<Room> roomList = roomRepository.findAll();
        for (Room  ro : roomList){
            ro.setPriceDefault(ne.nextInt(300000,4000000));
            ro.setThumbnailRoom("https://indec.vn/wp-content/uploads/2022/08/image4-8.jpg");
            roomRepository.save(ro);
        }


    }
        }

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


