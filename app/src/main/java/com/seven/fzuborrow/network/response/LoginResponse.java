package com.seven.fzuborrow.network.response;

import com.squareup.moshi.Json;

public class LoginResponse {
    private int code;
    private String message;
    @Json(name = "data")
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
