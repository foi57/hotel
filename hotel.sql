/*
 Navicat Premium Dump SQL

 Source Server         : localhost_3308
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3308
 Source Schema         : hotel

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 14/11/2024 10:39:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `room_id` int NULL DEFAULT NULL,
  `room_count` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `TimeStart` datetime NULL DEFAULT NULL,
  `TimeEnd` datetime NULL DEFAULT NULL,
  `arrivalTime` time NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `bookTime` datetime NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (26, 2, 4, 4, '黄毓峰', '17377344462', '2858712518@qq.com', '2024-11-12 00:00:00', '2024-11-13 00:00:00', '09:30:00', 4.00, '2024-11-07 10:56:11', '已预订');
INSERT INTO `book` VALUES (27, 2, 5, 1, '黄毓峰', '17377344462', '2858712518@qq.com', '2024-11-12 00:00:00', '2024-11-13 00:00:00', '11:30:00', 289.00, '2024-11-07 10:56:22', '已预订');

-- ----------------------------
-- Table structure for hotel
-- ----------------------------
DROP TABLE IF EXISTS `hotel`;
CREATE TABLE `hotel`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `introduction` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `district` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `locations` json NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 97 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hotel
-- ----------------------------
INSERT INTO `hotel` VALUES (94, 2, '对啊是固定vv酒店', '的傻瓜的卡不卡边防军互联', '辽宁省', '鞍山市', '立山区', '的萨科就给大家仨包括v就', '[\"123.029091\", \"41.150401\"]');
INSERT INTO `hotel` VALUES (95, 2, '大家撒表达v上课酒店', '的空间沙拉城市百科接个电话JFK了吧v就都能看见十八v恐惧夫v就', '广西壮族自治区', '桂林市', '临桂区', '桂林信息科技学院', '[\"110.123498\", \"25.257445\"]');

-- ----------------------------
-- Table structure for hotel_pictures
-- ----------------------------
DROP TABLE IF EXISTS `hotel_pictures`;
CREATE TABLE `hotel_pictures`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `hotel_id` int NULL DEFAULT NULL,
  `picture_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 119 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hotel_pictures
-- ----------------------------
INSERT INTO `hotel_pictures` VALUES (113, 94, 'http://localhost:8080/files/f060fb83-8a68-4a2c-a399-dc26277fdb79.jpg');
INSERT INTO `hotel_pictures` VALUES (114, 94, 'http://localhost:8080/files/111ab4c0-1e43-4c26-964b-6e3158aad283.jpg');
INSERT INTO `hotel_pictures` VALUES (115, 95, 'http://localhost:8080/files/65b0f8e3-6099-4ab6-aa0b-6d2b78a72053.png');
INSERT INTO `hotel_pictures` VALUES (116, 95, 'http://localhost:8080/files/b24e2be6-cdbd-4550-8b8c-576ef0719fca.png');

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `UUID` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `fileName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of picture
-- ----------------------------
INSERT INTO `picture` VALUES (96, '214653ca-8b21-4337-8ed3-e883e1a7ed29.jpg', '203720794.jpg');
INSERT INTO `picture` VALUES (97, '06622853-c2eb-45c6-ba3f-403988d4fb1d.jpg', '238194854.jpg');
INSERT INTO `picture` VALUES (98, '2795c532-3e5c-4bd1-bd62-1dd370e0c8bd.jpg', '203720794.jpg');
INSERT INTO `picture` VALUES (99, 'f060fb83-8a68-4a2c-a399-dc26277fdb79.jpg', '238194854.jpg');
INSERT INTO `picture` VALUES (100, '111ab4c0-1e43-4c26-964b-6e3158aad283.jpg', '18690165.jpg');
INSERT INTO `picture` VALUES (101, '49e4363f-d4e2-489b-8e40-5279a3623233.jpg', '238194854.jpg');
INSERT INTO `picture` VALUES (102, '65b0f8e3-6099-4ab6-aa0b-6d2b78a72053.png', '屏幕截图 2024-06-19 165631.png');
INSERT INTO `picture` VALUES (103, 'b24e2be6-cdbd-4550-8b8c-576ef0719fca.png', '屏幕截图 2024-06-19 171102.png');
INSERT INTO `picture` VALUES (104, '6f33be3c-8715-4f64-905b-d468e744ca1f.jpg', '91527361.jpg');
INSERT INTO `picture` VALUES (105, '9628ba1b-9f8a-4a2d-b71d-436eb08e5440.jpg', '238194854.jpg');
INSERT INTO `picture` VALUES (106, '56319fa3-7ca1-44d8-9617-fc2e3dce4f88.jpg', '238194854.jpg');
INSERT INTO `picture` VALUES (107, '1fd4fafc-a461-41a9-bc39-3b9a1fec61ce.jpg', '203720794.jpg');
INSERT INTO `picture` VALUES (108, '316fdbfb-bb19-4aea-8f48-0a3e65f523a9.jpg', '238194854.jpg');

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `hotel_id` int NULL DEFAULT NULL,
  `room_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `room_count` int NULL DEFAULT NULL,
  `bed_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `bed_count` int NULL DEFAULT NULL,
  `facilities` json NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES (4, 94, '但是俩号的玻璃杯v给', 4, '双人床', 3, '[false, true, false, true, true, false, false, false]', 4.00);
INSERT INTO `room` VALUES (5, 95, '湖底是v方式比较', 3, '单人床', 2, '[false, false, true, false, true, true, false, false]', 289.00);
INSERT INTO `room` VALUES (6, 95, '的手机卡v的v尽快房间', 3, '单人床', 4, '[false, true, true, false, true, false, false, false]', 6.00);

-- ----------------------------
-- Table structure for room_pictures
-- ----------------------------
DROP TABLE IF EXISTS `room_pictures`;
CREATE TABLE `room_pictures`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `hotel_id` int NULL DEFAULT NULL,
  `room_id` int NULL DEFAULT NULL,
  `room_picture_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of room_pictures
-- ----------------------------
INSERT INTO `room_pictures` VALUES (59, 94, 4, 'http://localhost:8080/files/49e4363f-d4e2-489b-8e40-5279a3623233.jpg');
INSERT INTO `room_pictures` VALUES (60, 95, 5, 'http://localhost:8080/files/6f33be3c-8715-4f64-905b-d468e744ca1f.jpg');
INSERT INTO `room_pictures` VALUES (61, 95, 6, 'http://localhost:8080/files/9628ba1b-9f8a-4a2d-b71d-436eb08e5440.jpg');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'foi57', '2858712518@qq.com', '123456');
INSERT INTO `user` VALUES (3, '的撒结果', 'NEWWIFE@163.com', '123456');

SET FOREIGN_KEY_CHECKS = 1;
