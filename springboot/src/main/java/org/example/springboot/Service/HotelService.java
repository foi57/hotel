package org.example.springboot.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springboot.entity.HotelForm;
import org.example.springboot.entity.Room;
import org.example.springboot.mapper.HotelMapper;
import org.example.springboot.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class HotelService {
    Logger logger = LoggerFactory.getLogger(HotelService.class);
    HotelMapper hotelMapper;
    ObjectMapper objectMapper;
    @Autowired
    public HotelService(HotelMapper hotelMapper,ObjectMapper objectMapper) {
        this.hotelMapper = hotelMapper;
        this.objectMapper = objectMapper;
    }
    @Autowired
    UserMapper userMapper;
    public void addPicture(String UUID,String fileName) {
        logger.info("addPictureFileName{}",fileName);
        userMapper.addPicture(UUID,fileName);
    }
    public String SelectPicture(String fileName) {
        logger.info("DeleteFileName{}",fileName);
        return hotelMapper.selectPicture(fileName);
    }
    public void insertHotelPicture(int hotelId,HotelForm hotelForm) {
        for (int i = 0; i < hotelForm.getPicture_url().size(); i++) {
            hotelMapper.insertHotelPicture(hotelId,hotelForm.getPicture_url().get(i));
        }
    }

    public List<Integer> insertRoom(int hotelId,HotelForm hotelForm) {
        List<Integer> roomIds=new ArrayList<>();
         hotelForm.getRooms().forEach(room -> {
           List<Boolean> facilities = room.getFacilities();
             try {
                 String facilitiesString = objectMapper.writeValueAsString(facilities);
                 hotelMapper.insertRoom(hotelId,room,facilitiesString);
                 roomIds.add(room.getRoomId()); ;
             } catch (JsonProcessingException e) {
                 throw new RuntimeException(e);
             }

         });
         return roomIds;
    }
    public int insertHotel(HotelForm hotelForm,int user_id) throws JsonProcessingException {
        logger.info("insertHotel{}",hotelForm);
        String locations = objectMapper.writeValueAsString(hotelForm.getLocations());
        logger.info("insertHotelLocations{}",locations);
        try {
          hotelMapper.insertHotel(hotelForm,user_id,locations);

            return hotelForm.getId();
        } catch (Exception e) {
            logger.info("insertHotel",e);
        }
        return -1;
    }
    public void insertRoomPicture(int hotelId,List<Integer> roomIds,HotelForm hotelForm) {
       for (int i = 0; i< hotelForm.getRooms().size(); i++) {
           for (int j = 0; j < hotelForm.getRooms().get(i).getRoomPictures().size(); j++) {
               hotelMapper.insertRoomPicture(hotelId,roomIds.get(i),hotelForm.getRooms().get(i).getRoomPictures().get(j));
           }
       }
    }

    public List<HotelForm> selectSpecialsHotels() {
       List<HotelForm> hotelForms=hotelMapper.selectSpecialsHotels();
       logger.info("selectSpecialsHotels{}",hotelForms);
       for (HotelForm hotelForm : hotelForms) {
           if (hotelForm.getPicture_urls()!=null){
               hotelForm.setPicture_url(Arrays.asList(hotelForm.getPicture_urls().split(",")));
           }else {
               hotelForm.setPicture_url(new ArrayList<>());
               logger.info("pictures为null");
           }
       }
       return hotelForms;
    }
}
