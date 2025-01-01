package org.example.springboot.Controller;

import org.example.springboot.Service.HotelService;
import org.example.springboot.entity.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.example.springboot.Util.Util;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
@Component
@RequestMapping("/api")
public class RoomController {

    HotelService hotelService;
    Logger logger = LoggerFactory.getLogger(RoomController.class);
    @PostMapping("/selectRoomByHotelIdTime")
    public List<Room> selectRoomByHotelId(int hotelId, String timeStart, String timeEnd) {
        logger.info("hotelId{},timeStart{},timeEnd{}",hotelId,timeStart,timeEnd);
        List<Timestamp> timestamps= Util.getTimestamp(timeStart,timeEnd);
        Timestamp timestampStart=timestamps.get(0);
        Timestamp timestampEnd=timestamps.get(1);
        List<Room> room= hotelService.getHotelPicturesRoom(hotelId,timestampStart,timestampEnd);
        logger.info("Room{}",room);
        return room;
    }

}
