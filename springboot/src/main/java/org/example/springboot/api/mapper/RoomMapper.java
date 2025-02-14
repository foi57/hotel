package org.example.springboot.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.springboot.api.entity.Room;

@Mapper
public interface RoomMapper extends BaseMapper<Room> {

}
