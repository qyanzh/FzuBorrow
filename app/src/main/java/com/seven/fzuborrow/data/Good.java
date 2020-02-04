package com.seven.fzuborrow.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.seven.fzuborrow.network.Api;


public class Good implements Parcelable {
    private long gid;            //物品的id
    private long uid;           //用户的id
    private String location;    //物品的地址？
    private String imgurl;      //图片的url
    private int status;         //租借的状态
    private String type;        //区分闲置物品还是活动室
    private String name;        //物品的名字
    private String detail;      // 物品的详细说明

    private Float grade;

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public static Creator<Good> getCREATOR() {
        return CREATOR;
    }

    public Good() {
    }

    public Good(long gid, String name, String profile) {
        this.gid = gid;
        this.name = name;
        this.detail = profile;
    }

    protected Good(Parcel in) {
        gid = in.readLong();
        uid = in.readLong();
        location = in.readString();
        imgurl = in.readString();
        status = in.readInt();
        type = in.readString();
        name = in.readString();
        detail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(gid);
        dest.writeLong(uid);
        dest.writeString(location);
        dest.writeString(imgurl);
        dest.writeInt(status);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(detail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Good> CREATOR = new Creator<Good>() {
        @Override
        public Good createFromParcel(Parcel in) {
            return new Good(in);
        }

        @Override
        public Good[] newArray(int size) {
            return new Good[size];
        }
    };

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
        if(imgurl!=null) {
            if(imgurl.startsWith("http")) {
                return imgurl;
            } else {
                return Api.baseURL + "img/get?url=" + imgurl;
            }
        } else {
            return null;
        }
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
