package org.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.entity.Room;

@Mapper
public interface RoomMapper extends BaseMapper<Room> {
    @Override
    int updateById(Room entity);
}
