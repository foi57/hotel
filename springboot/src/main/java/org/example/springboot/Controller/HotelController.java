package org.example.springboot.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.*;
import org.apache.ibatis.type.MappedTypes;
import org.example.springboot.Service.HotelService;
import org.example.springboot.entity.BookInfo;
import org.example.springboot.entity.HotelForm;
import org.example.springboot.entity.HotelSearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api")
public class HotelController {
    Logger logger = LoggerFactory.getLogger(HotelController.class);
    @Value("${server.url}")
    private String serverUrl;
    HotelService hotelService;
    @Autowired
    HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
    @PostMapping("/PictureUpload")
    public ResponseEntity<String> hotelPictureUpload(@RequestParam("file") MultipartFile file) throws IOException {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
            }
            Files.createDirectories(Paths.get("springboot/src/main/resources/static/files"));
            String fileName = file.getOriginalFilename();
            String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
            String uniqueFileName = UUID.randomUUID() + fileExtension;
            Path path = Paths.get("springboot/src/main/resources/static/files", uniqueFileName);
            Files.write(path, file.getBytes());
            hotelService.addPicture(uniqueFileName,fileName);
            String fileUrl = serverUrl + "/files/" + uniqueFileName;
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("/addHotel")
    public ResponseEntity<String> addHotel(@RequestBody HotelForm form, @RequestHeader("Authorization") String authorizationHeader) throws JsonProcessingException {
        int id = getUserId(authorizationHeader);
        try {
            logger.info("form{}",form);
            int hotelId= hotelService.insertHotel(form,id);
            logger.info("hotelId{}",hotelId);
            hotelService.insertHotelPicture(hotelId,form);
            List<Integer> roomIds=hotelService.insertRoom(hotelId,form);
            hotelService.insertRoomPicture(hotelId,roomIds,form);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    @PostMapping("/deletePicture")
    public ResponseEntity<String> deletePicture(@RequestParam("fileName") String fileName) {
        String uniqueFileName = hotelService.SelectPicture(fileName);
        logger.info("UUID{}",uniqueFileName);
        if (uniqueFileName == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
        Path path = Paths.get("springboot/src/main/resources/static/files", uniqueFileName);
        try {
            Files.deleteIfExists(path);
            return ResponseEntity.ok("File deleted successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/selectSpecialsHotels")
    public ResponseEntity<List<HotelForm>> selectSpecialsHotels() {
        List<HotelForm> hotelForm = null;
        try {
            hotelForm = hotelService.selectSpecialsHotels();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(hotelForm);
    }
    @PostMapping("/selectHotelBySearchBox")
    public ResponseEntity<List<HotelForm>> selectHotelBySearchBox(@RequestBody HotelSearchRequest hotelSearchRequest) {
        logger.info("hotelSearchRequest{}",hotelSearchRequest);
        return null;
    }
    @PostMapping("/selectHotelByCityTime")
    public List<HotelForm> selectHotelCountByCityTime(@RequestParam("city") String city,@RequestParam("timeStart")  String timeStart,@RequestParam("timeEnd") String timeEnd) {
        logger.info("city{},timeStart{},timeEnd{}",city,timeStart,timeEnd);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestampStart;
        Timestamp timestampEnd;

        try {
            Date dateStart = sdf.parse(timeStart);
            Date dateEnd = sdf.parse(timeEnd);
            timestampStart = new Timestamp(dateStart.getTime());
            timestampEnd = new Timestamp(dateEnd.getTime());
        } catch (ParseException | java.text.ParseException e) {
            logger.error("时间格式错误: ", e);
            return null; // 或者处理错误的逻辑
        }
        int count= hotelService.SelectHotelCountByCityTime(city,timestampStart,timestampEnd);
        logger.info("HotelCount{}",count);
        return null;
    }
    public int getUserId(@RequestHeader("Authorization") String authorizationHeader) {
          int id = -1;
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String key = "your-256-bit-secret-long-string-here";
                Claims claims=Jwts.parser()
                        .setSigningKey(key.getBytes())
                        .parseClaimsJws(token)
                        .getBody();
                id = claims.get("id", Integer.class);
            }
            else {
                logger.info("No token provided");
            }
        } catch (ExpiredJwtException e) {
            logger.info("Token expired：{}",e.getMessage());
        }
        return id;
    }

}
