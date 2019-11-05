package com.seven.fzuborrow.data;

import com.squareup.moshi.Json;

public class Comment {
    private long cid;
    private long ctime;
    private String content;
    @Json(name = "uid")
    private long userId;
    @Json(name = "did")
    private long discId;
    private int likes;
    private String username;

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getDiscId() {
        return discId;
    }

    public void setDiscId(long discId) {
        this.discId = discId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
