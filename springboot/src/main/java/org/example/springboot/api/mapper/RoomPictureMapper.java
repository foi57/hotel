package org.example.springboot.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.example.springboot.api.entity.RoomPicture;

public interface RoomPictureMapper extends BaseMapper<RoomPicture> {
    @Update("UPDATE room_pictures SET room_picture_url=#{room_picture_url} WHERE room_id=#{room_id}")
    int updateRoomPicture(@Param("room_id") int roomId, @Param("room_picture_url") String pictureURL);
}
