package com.seven.fzuborrow.data;

import com.seven.fzuborrow.network.Api;

public class Disc implements Comparable<Disc>{
    private int did;
    private long ctime;
    private String title;
    private long uid;
    private int likes;
    private String imgurl;
    private String username;
    private String useravatar;
    private int counts;
    private Integer grade;

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getImgurl() {
        if (imgurl != null) {
            return Api.baseURL + "img/get?url=" + imgurl;
        } else {
            return null;
        }
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseravatar() {
        if(useravatar!=null) {
            return Api.baseURL + "img/get?url=" + imgurl;
        } else {
            return null;
        }
    }

    public void setUseravatar(String useravatar) {
        this.useravatar = useravatar;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    @Override
    public int compareTo(Disc o) {
        if(o.getGrade()!=null && this.getGrade()!=null && !o.getGrade().equals(this.getGrade())) {
            return o.getGrade() - this.getGrade();
        } else {
            return (int) (o.getCtime() - this.getCtime());
        }
    }
}
