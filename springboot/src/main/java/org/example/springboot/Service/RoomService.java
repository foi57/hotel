package org.example.springboot.Service;

import org.example.springboot.entity.Room;
import org.example.springboot.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    RoomMapper roomMapper;

    @Autowired
    public RoomService(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    public int updateRoom(Room room) {
        return roomMapper.updateById(room);
    }
}
