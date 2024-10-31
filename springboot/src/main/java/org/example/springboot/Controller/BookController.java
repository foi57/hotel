package org.example.springboot.Controller;


import org.example.springboot.Service.BookService;
import org.example.springboot.entity.BookInfo;
import org.example.springboot.entity.BookView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api")
public class BookController {
    Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final HotelController hotelController;
    @Autowired
    public BookController(BookService bookService,HotelController hotelController) {
        this.bookService = bookService;
        this.hotelController=hotelController;
    }

    @PostMapping("/booking")
    public void InsertBook(@RequestBody BookInfo bookInfo ,@RequestHeader("Authorization") String authorizationHeader) {
        bookInfo.setUser_id(hotelController.getUserId(authorizationHeader));
        logger.info("bookInfo{}",bookInfo);
            bookService.InsertBook(bookInfo);
    }
    @GetMapping("/showBook")
    public List<BookView> getBook(@RequestHeader("Authorization") String authorizationHeader) {
        List<BookView> bookViewList = bookService.getBook(hotelController.getUserId(authorizationHeader));
        logger.info("bookView{}",bookViewList);
         return bookViewList;
    }
}
