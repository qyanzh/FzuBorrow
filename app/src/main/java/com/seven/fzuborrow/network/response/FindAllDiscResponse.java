package com.seven.fzuborrow.network.response;

import com.seven.fzuborrow.data.Disc;
import com.squareup.moshi.Json;

import java.util.List;

public class FindAllDiscResponse {
    private int code;

    private String message;

    @Json(name="data")
    private List<Disc> discList;

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

    public List<Disc> getDiscList() {
        return discList;
    }

    public void setDiscList(List<Disc> discList) {
        this.discList = discList;
    }
}
