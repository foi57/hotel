package org.example.springboot.entity;

import java.util.List;

public class HotelForm {
    private Integer id;
    private String name;
    private String  introduction;
    private List<String> picture_url; // 使用 List<String> 存储酒店图片
    private String province;
    private String city;
    private String district;
    private String address;
    private List<String> locations; // 存储位置
    private List<Room> rooms; // 房间信息的列表
    private String picture_urls;
    public Integer getId() {
        return id;
    }

    public String getPicture_urls() {
        return picture_urls;
    }

    public void setPicture_urls(String picture_urls) {
        this.picture_urls = picture_urls;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(List<String> picture_url) {
        this.picture_url = picture_url;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "HotelForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", picture_url=" + picture_url +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", address='" + address + '\'' +
                ", locations=" + locations +
                ", rooms=" + rooms +
                ", picture_urls='" + picture_urls + '\'' +
                '}';
    }
}

