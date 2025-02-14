package org.example.springboot.api.service;

import jakarta.transaction.Transactional;
import org.example.springboot.api.entity.Room;
import org.example.springboot.api.mapper.RoomMapper;
import org.example.springboot.api.mapper.RoomPictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    RoomMapper roomMapper;
    RoomPictureMapper roomPictureMapper;

    @Autowired
    public RoomService(RoomMapper roomMapper, RoomPictureMapper roomPictureMapper) {
        this.roomMapper = roomMapper;
        this.roomPictureMapper = roomPictureMapper;
    }

    @Transactional
    @CacheEvict(value = {"SelectHotelByCityTimeCache", "selectHotelByUserIdCache", "getHotelPicturesRoomCache", "getPicturesRoomCache", "getHotelPicturesRoomCache1"}, allEntries = true)
    public int updateRoom(Room room) {
        roomMapper.updateById(room);
        roomPictureMapper.updateRoomPicture(room.getId(), room.getRoom_picture_url());
        return 1;
    }

}
