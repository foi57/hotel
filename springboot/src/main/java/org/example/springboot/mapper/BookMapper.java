package org.example.springboot.mapper;

import org.apache.ibatis.annotations.*;
import org.example.springboot.entity.BookView;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BookMapper {
    @Insert("INSERT INTO book(user_id, room_id, room_count, name, phone, email, TimeStart, TimeEnd, arrivalTime, price,bookTime,state) VALUES (#{user_id}, #{room_id}, #{room_count}, #{name}, #{phone}, #{email},  #{timeStart}, #{timeEnd}, #{arrivalTime}, #{price},#{localDateTime},#{state})")
    void insertBook(@Param("room_id") int room_id, @Param("user_id") int user_id, @Param("room_count") int room_count, @Param("name") String name, @Param("phone") String phone, @Param("email") String email, @Param("timeStart") Timestamp timeStart, @Param("timeEnd") Timestamp timeEnd, @Param("arrivalTime") String arrivalTime, @Param("price") BigDecimal price,@Param("localDateTime") LocalDateTime localDateTime,
    @Param("state") String state);
    @Select("SELECT book.id,hotel.name,book.timeStart,book.timeEnd,book.arrivalTime,book.price,room.room_name,room.room_count,book.bookTime,book.state from book,room,hotel where book.user_id=#{userId} and room.id=book.room_id and hotel.id=room.hotel_id")
    List<BookView> selectBookByUserId(int userId);
    @Delete("delete from book where id=#{id}")
    void deleteBook(int id);
    @Update("update book set state=#{state} where id=#{id}")
    void UpdateStateBook(@Param("id") int id,@Param("state") String state);//back
}
