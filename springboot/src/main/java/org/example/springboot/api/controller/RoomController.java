package org.example.springboot.api.controller;

import org.example.springboot.api.service.HotelService;
import org.example.springboot.api.service.RoomService;
import org.example.springboot.Util.Util;
import org.example.springboot.api.entity.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {

    HotelService hotelService;
    RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService, HotelService hotelService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
    }

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

    @PostMapping("/updateRoom")
    public ResponseEntity<Integer> update(@RequestBody Room room) {
        logger.info("updateRoom{}", room);
        return ResponseEntity.ok(roomService.updateRoom(room));
    }
}
