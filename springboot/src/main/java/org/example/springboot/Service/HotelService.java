package org.example.springboot.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springboot.entity.HotelForm;
import org.example.springboot.entity.Picture;
import org.example.springboot.entity.Room;
import org.example.springboot.mapper.HotelMapper;
import org.example.springboot.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

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
    public Long addPicture(String uuid, String fileName) {
        Picture picture = new Picture();
        picture.setUuid(uuid);
        picture.setFileName(fileName);
        logger.info("AddPicture{}", picture);
        userMapper.addPicture(picture); // 直接传递 Picture 对象
        return picture.getId(); // 此时应该能正确获取生成的 ID
    }

    public String SelectPicture(String id) {
        return userMapper.selectPicture(id);
    }
    public void deletePicture(String id) {
        userMapper.deletePicture(id);
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
                 roomIds.add(Integer.valueOf(room.getId())); ;
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
           hotelMapper.insertRoomPicture(hotelId,roomIds.get(i),hotelForm.getRooms().get(i).getRoom_picture_url());
       }
    }

    public List<HotelForm> selectSpecialsHotels() {

        List<HotelForm> hotelForms= null;
        try {
            hotelForms = hotelMapper.selectSpecialsHotels();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
      hotelForms= getHotelPicturesRoom(hotelForms);
        logger.info("selectSpecialsHotels{}",hotelForms);
       return hotelForms;
    }



   public Map<String,Object> SelectHotelByCityTime(String city, Timestamp timeStart, Timestamp timeEnd,int pageIndex,int pageSize) {
       int offset = (pageIndex - 1) * pageSize;
       int count = hotelMapper.countHotelsByCityCityTime(city,timeStart,timeEnd);
       List<HotelForm> hotelFormList = hotelMapper.selectHotelByCityCityTime(city,timeStart,timeEnd,pageSize,offset);
       int totalPage = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
       logger.info("hotelFormList{}",hotelFormList);
       hotelFormList=getHotelPicturesRoom(hotelFormList,timeStart,timeEnd);
       Map<String,Object> result = new HashMap<>();
       result.put("totalPage",totalPage);
       result.put("currentPage",pageIndex);
       result.put("hotelFormList",hotelFormList);
       return result;
   }

    public List<HotelForm> selectHotelByUserId(int userId, int pageIndex, int pageSize) {
        int count = hotelMapper.selectHotelCountByUserId(userId);
        int offset = (pageIndex - 1) * pageSize;
        int totalPage = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        List<HotelForm> hotelFormList = hotelMapper.selectHotelsByUserId(userId,pageSize,offset);
        hotelFormList=getHotelPicturesRoom(hotelFormList);
        logger.info("ByUserIdHotelFormList{}",hotelFormList);
        return hotelFormList;
    }

    public List<HotelForm> getHotelPicturesRoom(List<HotelForm> hotelFormList){
        for (HotelForm hotelForm : hotelFormList) {
            if (hotelForm.getPicture_urls()!=null){
                hotelForm.setPicture_url(Arrays.asList(hotelForm.getPicture_urls().split(",")));
            }else {
                hotelForm.setPicture_url(new ArrayList<>());
                logger.info("pictures为null");
            }
            List<Room> rooms= null;
            try {
                rooms = hotelMapper.selectRoomByHotelId(hotelForm.getId());
                logger.info("rooms{}",rooms);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            hotelForm.setRooms(rooms);
        }
        return hotelFormList;
    }

    public void deleteHotelById(int id) {
        hotelMapper.deleteHotelById(id);
    }
    public List<HotelForm> getHotelPicturesRoom(List<HotelForm> hotelFormList,Timestamp timeStart, Timestamp timeEnd){
        for (HotelForm hotelForm : hotelFormList) {
            if (hotelForm.getPicture_urls()!=null){
                hotelForm.setPicture_url(Arrays.asList(hotelForm.getPicture_urls().split(",")));
            }else {
                hotelForm.setPicture_url(new ArrayList<>());
                logger.info("pictures为null");
            }
            List<Room> rooms= null;
            try {
                rooms = hotelMapper.selectRoomByHotelIdTime(hotelForm.getId(),timeStart,timeEnd);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            hotelForm.setRooms(rooms);
        }
        return hotelFormList;
    }
    public List<Room> getHotelPicturesRoom(int hotelId,Timestamp timeStart, Timestamp timeEnd){

            List<Room> rooms= null;
            try {
                rooms = hotelMapper.selectRoomByHotelIdTime(hotelId,timeStart,timeEnd);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return rooms;
        }
    @Transactional
    public void updateHotel(HotelForm hotelForm) throws JsonProcessingException {
        logger.info("updateHotel{}",hotelForm);
        String location = objectMapper.writeValueAsString(hotelForm.getLocations());
        hotelMapper.updateHotel(hotelForm,location);
        hotelMapper.deleteHotelPicture(hotelForm.getId());
        for (String pictureUrl : hotelForm.getPicture_url()) {
            hotelMapper.insertHotelPicture(hotelForm.getId(), pictureUrl);
        }
    }

}
