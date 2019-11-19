package com.seven.fzuborrow.network.response;

import com.seven.fzuborrow.data.User;
import com.squareup.moshi.Json;

public class FindUserResponse {
    private int code;
    private String message;
    @Json(name="data")
    private User user;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
