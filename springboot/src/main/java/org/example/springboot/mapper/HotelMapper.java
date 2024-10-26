package org.example.springboot.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.MappedTypes;
import org.example.springboot.entity.HotelForm;
import org.example.springboot.entity.Room;

import java.util.List;
@MappedTypes(List.class)
public interface HotelMapper {
    @Select("SELECT UUID FROM picture WHERE fileName=#{fileName}")
    String selectPicture(String fileName);
    @Insert("insert into hotel(name, userId,introduction,province,city,district,address, locations) values(#{form.name},#{user_id},#{form.introduction},#{form.province},#{form.city},#{form.district},#{form.address},#{locations})")
    @Options(useGeneratedKeys = true, keyProperty = "form.id",keyColumn = "id")
    void insertHotel(@Param("form") HotelForm form,@Param("user_id") int user_id,@Param("locations") String locations);
    @Insert("INSERT INTO hotel_pictures(hotel_id, picture_url) values (#{hotelId}, #{pictureUrl})")
    void insertHotelPicture(@Param("hotelId") int hotelId,@Param("pictureUrl") String pictureUrl);
   @Insert("INSERT INTO room(hotel_id, room_name, bed_type, bed_count, facilities,price) VALUES (#{hotelId}, #{room.roomName}, #{room.bedType}, #{room.bedCount}, #{facilities},#{room.price})")
   @Options(useGeneratedKeys = true, keyProperty = "room.roomId")
   void insertRoom(@Param("hotelId") int hotelId, @Param("room") Room room,@Param("facilities") String facilitiesString);
   @Insert("INSERT INTO room_pictures(hotel_id,room_id, picture_url) VALUES (#{hotelId},#{roomId}, #{pictureUrl})")
    void insertRoomPicture(@Param("hotelId") int hotelId,@Param("roomId") int roomId,@Param("pictureUrl") String pictureUrl);
   @Select("SELECT hotel.*,GROUP_CONCAT(hotel_pictures.picture_url) as picture_urls  from hotel join hotel_pictures on  hotel.id=hotel_pictures.hotel_id group by hotel.id LIMIT 4")
    List<HotelForm> selectSpecialsHotels();
}