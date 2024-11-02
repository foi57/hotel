package org.example.springboot.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.MappedTypes;
import org.example.springboot.Config.BooleanListTypeHandler;
import org.example.springboot.Config.StringListTypeHandler;
import org.example.springboot.entity.BookInfo;
import org.example.springboot.entity.HotelForm;
import org.example.springboot.entity.Room;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
@Mapper
public interface HotelMapper {

    @Insert("insert into hotel(name, userId,introduction,province,city,district,address, locations) values(#{form.name},#{user_id},#{form.introduction},#{form.province},#{form.city},#{form.district},#{form.address},#{locations})")
    @Options(useGeneratedKeys = true, keyProperty = "form.id",keyColumn = "id")
    void insertHotel(@Param("form") HotelForm form,@Param("user_id") int user_id,@Param("locations") String locations);

    @Insert("INSERT INTO hotel_pictures(hotel_id, picture_url) values (#{hotelId}, #{pictureUrl})")
    void insertHotelPicture(@Param("hotelId") int hotelId,@Param("pictureUrl") String pictureUrl);

    @Insert("INSERT INTO room(hotel_id, room_name,room_count,bed_type, bed_count, facilities,price) VALUES (#{hotelId}, #{room.room_name},#{room.room_count},#{room.bed_type}, #{room.bed_count}, #{facilities},#{room.price})")
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

    @Results({@Result(property = "locations", column = "locations", typeHandler = StringListTypeHandler.class)})
    @Select("SELECT hotel.*, " +
            "GROUP_CONCAT(hotel_pictures.picture_url) as picture_urls, " +
            "SUM(room.room_count) AS total_rooms, " +
            "COALESCE(SUM(booked.room_count), 0) AS booked_rooms " +
            "FROM hotel " +
            "JOIN hotel_pictures ON hotel.id = hotel_pictures.hotel_id " +
            "JOIN room ON room.hotel_id = hotel.id " +
            "LEFT JOIN ( " +
            "    SELECT room_id, SUM(room_count) AS room_count " +
            "    FROM book " +
            "    WHERE TimeStart < #{timeEnd} AND TimeEnd > #{timeStart} " +
            "    GROUP BY room_id " +
            ") AS booked ON booked.room_id = room.id " +
            "WHERE hotel.city = #{city} " +
            "GROUP BY hotel.id " +
            "HAVING total_rooms - COALESCE(booked_rooms, 0) > 0 " +
            "LIMIT #{pageSize} OFFSET #{offset}")
    List<HotelForm> selectHotelByCityCityTime(@Param("city") String city,
                                              @Param("timeStart") Timestamp timeStart,
                                              @Param("timeEnd") Timestamp timeEnd,
                                              @Param("pageSize") int pageSize,
                                              @Param("offset") int offset);

    @Select("SELECT COUNT(DISTINCT hotel.id) " +
            "FROM hotel " +
            "JOIN hotel_pictures ON hotel.id = hotel_pictures.hotel_id " +
            "JOIN room ON room.hotel_id = hotel.id " +
            "LEFT JOIN ( " +
            "    SELECT room_id, SUM(room_count) AS room_count " +
            "    FROM book " +
            "    WHERE TimeStart < #{timeEnd} AND TimeEnd > #{timeStart} " +
            "    GROUP BY room_id " +
            ") AS booked ON booked.room_id = room.id " +
            "WHERE hotel.city = #{city} " +
            "HAVING SUM(room.room_count) - COALESCE(SUM(booked.room_count), 0) > 0")
    int countHotelsByCityCityTime(@Param("city") String city,
                                  @Param("timeStart") Timestamp timeStart,
                                  @Param("timeEnd") Timestamp timeEnd);


    @Results({@Result(property = "facilities",column = "facilities" ,typeHandler = BooleanListTypeHandler.class)})
    @Select("SELECT room.*, room_pictures.*, (room.room_count- COALESCE(booked.room_count, 0)) AS available_rooms " +
            "FROM room " +
            "JOIN room_pictures ON room.id = room_pictures.room_id " +
            "LEFT JOIN ( " +
            "    SELECT room_id, SUM(room_count) AS room_count " +
            "    FROM book " +
            "    WHERE TimeStart < #{timeEnd} AND TimeEnd > #{timeStart} " +
            "    GROUP BY room_id " +
            ") AS booked ON booked.room_id = room.id " +
            "WHERE room.hotel_id = #{hotelId} " +
            "AND (room.room_count - COALESCE(booked.room_count, 0)) > 0;")  // 仅返回可用的房间
    List<Room> selectRoomByHotelIdTime(@Param("hotelId") int hotelId,
                                       @Param("timeStart") Timestamp timeStart,
                                       @Param("timeEnd") Timestamp timeEnd);

//    @Select("SELECT COUNT(*) from room,hotel where  hotel.city=#{city} and  room.hotel_id=hotel.id and room.id not IN (SELECT room_id from book where TimeStart <= #{timeEnd} and TimeEnd > #{timeStart}) ")
//    int selectHotelCountByCityTime(@Param("city") String city,@Param("timeStart") Timestamp timeStart,@Param("timeEnd") Timestamp timeEnd);
}