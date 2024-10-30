package org.example.springboot.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.MappedTypes;
import org.example.springboot.Config.BooleanListTypeHandler;
import org.example.springboot.Config.StringListTypeHandler;
import org.example.springboot.entity.HotelForm;
import org.example.springboot.entity.Room;

import java.math.BigDecimal;
import java.util.List;
@Mapper
public interface HotelMapper {
    @Select("SELECT UUID FROM picture WHERE fileName=#{fileName}")
    String selectPicture(String fileName);
    @Insert("insert into hotel(name, userId,introduction,province,city,district,address, locations) values(#{form.name},#{user_id},#{form.introduction},#{form.province},#{form.city},#{form.district},#{form.address},#{locations})")
    @Options(useGeneratedKeys = true, keyProperty = "form.id",keyColumn = "id")
    void insertHotel(@Param("form") HotelForm form,@Param("user_id") int user_id,@Param("locations") String locations);
    @Insert("INSERT INTO hotel_pictures(hotel_id, picture_url) values (#{hotelId}, #{pictureUrl})")
    void insertHotelPicture(@Param("hotelId") int hotelId,@Param("pictureUrl") String pictureUrl);
   @Insert("INSERT INTO room(hotel_id, room_name, bed_type, bed_count, facilities,price) VALUES (#{hotelId}, #{room.room_name}, #{room.bed_type}, #{room.bed_count}, #{facilities},#{room.price})")
   @Options(useGeneratedKeys = true, keyProperty = "room.id")
   void insertRoom(@Param("hotelId") int hotelId, @Param("room") Room room,@Param("facilities") String facilitiesString);
   @Insert("INSERT INTO room_pictures(hotel_id,room_id, room_picture_url) VALUES (#{hotelId},#{roomId}, #{pictureUrl})")
    void insertRoomPicture(@Param("hotelId") int hotelId,@Param("roomId") int roomId,@Param("pictureUrl") String pictureUrl);
   @Results({@Result(property = "locations",column = "locations" ,typeHandler = StringListTypeHandler.class)})
   @Select("SELECT hotel.*,GROUP_CONCAT(hotel_pictures.picture_url) as picture_urls  from hotel join hotel_pictures on  hotel.id=hotel_pictures.hotel_id group by hotel.id LIMIT 4")
    List<HotelForm> selectSpecialsHotels();
   @Results({@Result(property = "facilities",column = "facilities" ,typeHandler = BooleanListTypeHandler.class)})
   @Select("SELECT room.*,room_pictures.* from room,room_pictures where room.id=room_pictures.room_id and room.hotel_id=#{hotelId}")
    List<Room> selectRoomByHotelId(int hotelId);
   @Insert("INSERT INTO book(user_id, room_id, room_count, guest_name, guest_phone, guest_email, guest_arrivalTime, TimeStart, TimeEnd, arrivalTime, price) VALUES (#{form.user_id}, #{form.room_id}, #{form.room_count}, #{form.guest_name}, #{form.guest_phone}, #{form.guest_email}, #{form.guest_arrivalTime}, #{form.TimeStart}, #{form.TimeEnd}, #{form.arrivalTime}, #{form.price})")
    void insertBook(HotelForm form);
}