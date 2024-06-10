package methodsecuritynew.bookingapp.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCreateHotelRequest {


    UpsertHotelRequest upsertHotelRequest;
    UpsertRoomRequest upsertRoomRequest;
    UpsertPolicyRequest upsertPolicyRequest;
//    String nameHotel;
//    String description;
//    Integer iCity;
//    String address;
//    String hotline;
//    String email;
//    String rental;
//    List<Integer> amenityHotelList;
//    Integer star;
//
//    String nameRoom;
//    String roomType;
//    List<Integer> amenityRoomList;
//    String descriptionRoom;
//    Integer areaRoom;
//    Integer capacityRoom;
//    Integer quantityRoom;
//    String bedType;
//    String bedSize;
//
//    String checkIn;
//    String checkOut;
//    String cancelPolicy;
//    String service;
//    String animal;
//    String age;
//    String other;







}
