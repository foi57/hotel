package org.example.springboot.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.*;
import org.example.springboot.Service.HotelService;
import org.example.springboot.entity.HotelForm;
import org.example.springboot.entity.HotelSearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.example.springboot.Util.Util.getTimestamp;

@RestController
@RequestMapping("/api")
public class HotelController {
    Logger logger = LoggerFactory.getLogger(HotelController.class);
    @Value("${server.url}")
    private String serverUrl;
    HotelService hotelService;

    @Value("${jwt.secret}")
    private  String secretKey;

    @Autowired
    HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
    @PostMapping("/PictureUpload")
    public Map<String,Object> hotelPictureUpload(@RequestParam("file") MultipartFile file) throws IOException {
            if (file.isEmpty()) {
                return null;
            }
            Files.createDirectories(Paths.get("springboot/src/main/resources/static/files"));
            String fileName = file.getOriginalFilename();
            String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
            String uniqueFileName = UUID.randomUUID() + fileExtension;
            Path path = Paths.get("springboot/src/main/resources/static/files", uniqueFileName);
            Files.write(path, file.getBytes());
           Long id = hotelService.addPicture(uniqueFileName,fileName);
           Map<String,Object> map = new HashMap<>();
           map.put("id",id);
            String fileUrl = serverUrl + "/files/" + uniqueFileName;
            map.put("fileUrl",fileUrl);
            logger.info("PictureMap{}",map);
            return map;

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
    public ResponseEntity<String> deletePicture(@RequestParam("fileId") String id) throws IOException {

        String uniqueFileName = hotelService.SelectPicture(id);
        hotelService.deletePicture(id);
        logger.info("UUID{}",uniqueFileName);
        if (uniqueFileName == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
        // 校验文件名
        if (!uniqueFileName.matches("[a-zA-Z0-9_-]+\\.[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("Invalid file name");
        }
        Path path = Paths.get("springboot/src/main/resources/static/files", uniqueFileName);
                    Files.deleteIfExists(path);
        return ResponseEntity.ok("File deleted successfully");
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
    public Map<String, Object> selectHotelCountByCityTime(@RequestParam("city") String city, @RequestParam("timeStart")  String timeStart, @RequestParam("timeEnd") String timeEnd, @RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        logger.info("city{},timeStart{},timeEnd{},page{},pageSize{}",city,timeStart,timeEnd,page,pageSize);
       List<Timestamp> timestampList= getTimestamp(timeStart,timeEnd);
        Timestamp timestampStart = timestampList.get(0);
        Timestamp timestampEnd = timestampList.get(1);
        Map<String, Object> map= hotelService.SelectHotelByCityTime(city,timestampStart,timestampEnd,page,pageSize);
        logger.info("Hotel{}",map);
        return map;
    }
    public int getUserId(@RequestHeader("Authorization") String authorizationHeader) {
          int id = -1;
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                Claims claims=Jwts.parser()
                        .setSigningKey(secretKey.getBytes())
                        .parseClaimsJws(token)
                        .getBody();
                id = claims.get("id", Integer.class);
            }
            else {
                logger.info("No token provided");
            }
        } catch (ExpiredJwtException e) {
            logger.info("Token expired：{}",e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            logger.info("Token is unsupported：{}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            logger.info("Token is malformed：{}", e.getMessage());
            throw e;
        } catch (SignatureException e) {
            logger.info("Signature validation failed：{}", e.getMessage());
            throw e;
        }
        return id;
    }


    @PostMapping("/selectHotelByUserId")
    public ResponseEntity<List<HotelForm>> selectHotelByUserId(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, @RequestHeader("Authorization") String authorizationHeader) {
        int userId;
        try {
            userId = getUserId(authorizationHeader);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }

        List<HotelForm> hotelForm = hotelService.selectHotelByUserId(userId, page, pageSize);
        return ResponseEntity.ok(hotelForm);
    }
    @PostMapping("deleteHotelById")
    public void deleteHotelById(@RequestParam("id") int id) {
        hotelService.deleteHotelById(id);
    }
    @PostMapping("/updateHotel")
    public void updateHotel(@RequestBody HotelForm hotelForm) throws JsonProcessingException {
        hotelService.updateHotel(hotelForm);
    }
}
