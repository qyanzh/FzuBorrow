package com.seven.fzuborrow.network.response;

import com.seven.fzuborrow.data.Apply;
import com.squareup.moshi.Json;

import java.util.List;

public class FindApplyResponse {
    private int code;
    private String message;
    @Json(name = "data")
    private List<Apply> applyList;

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

    public List<Apply> getApplyList() {
        return applyList;
    }

    public void setApplyList(List<Apply> applyList) {
        this.applyList = applyList;
    }
}
