package com.seven.fzuborrow.data;

import com.squareup.moshi.Json;

public class User {

    private long uid;
    private String imgurl;
    private String name;//真实姓名
    private String schoolid;
    private String phonenum;
    private String username;//登录名
    private String password;
    private String address;
    private String qq;
    private String wechat;
    private String department;
    private String speciality;
    @Json(name = "cls")
    private String clazz;//班级

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private static volatile User loggedInUser;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getImgurl() {
        if(imgurl!=null) {
            if(imgurl.startsWith("http")) {
                return imgurl;
            } else {
                return "http://49.235.150.59:8080/jiebei/img/get?url=" + imgurl;
            }
        } else {
            return null;
        }
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}