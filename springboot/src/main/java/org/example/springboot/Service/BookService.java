package org.example.springboot.Service;

import org.example.springboot.entity.BookInfo;
import org.example.springboot.entity.BookView;
import org.example.springboot.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class BookService {
    private BookMapper bookMapper;
    Logger logger = LoggerFactory.getLogger(BookService.class);
    @Autowired
    BookService(BookMapper bookMapper){
        this.bookMapper=bookMapper;
    }
    public void InsertBook(BookInfo bookInfo){
        logger.info("bookInfo{}",bookInfo);
        int room_id = bookInfo.getRoom_id();
        int user_id = bookInfo.getUser_id();
        int room_count = bookInfo.getRoom_count();
        String name = bookInfo.getName();
        String phone = bookInfo.getPhone();
        String email = bookInfo.getEmail();
        String timeStartString = bookInfo.getTimeStart();
        String timeEndString = bookInfo.getTimeEnd();
        Timestamp timeStart = Timestamp.valueOf(OffsetDateTime.parse(timeStartString).withOffsetSameInstant(OffsetDateTime.now().getOffset()).toLocalDateTime());
        Timestamp timeEnd = Timestamp.valueOf(OffsetDateTime.parse(timeEndString).withOffsetSameInstant(OffsetDateTime.now().getOffset()).toLocalDateTime());
        String state = "已预订";
        String arrivalTime = bookInfo.getArrivalTime();
        BigDecimal price = bookInfo.getPrice();
        logger.info("room_id{}",room_id);
        LocalDateTime localDateTime = LocalDateTime.now();
        bookMapper.insertBook(room_id, user_id, room_count, name, phone, email, timeStart, timeEnd, arrivalTime, price, localDateTime,state);
    }

    public List<BookView> getBook(int userId) {
        return bookMapper.selectBookByUserId(userId);
    }

    public void deleteBook(int id) {
        bookMapper.deleteBook(id);
    }

    public void UpdateStateBook(int id ,String state) {
        bookMapper.UpdateStateBook(id,state);
    }
}
