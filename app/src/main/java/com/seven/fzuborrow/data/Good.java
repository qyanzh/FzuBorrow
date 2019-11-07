package com.seven.fzuborrow.data;

public class Good {
    private long gid;            //物品的id
    private long uid;           //用户的id
    private String location;    //物品的地址？
    private String imgurl;      //图片的url
    private int status;         //租借的状态
    private String type;        //区分闲置物品还是活动室
    private String name;        //物品的名字
    private String detail;      // 物品的详细说明

    public Good() {
    }

    public Good(long gid, String name, String profile) {
        this.gid = gid;
        this.name = name;
        this.detail = profile;
    }

    public long getGid() {
        return gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgurl() {
        return "http://49.235.150.59:8080/jiebei/img/get?url=" + imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
