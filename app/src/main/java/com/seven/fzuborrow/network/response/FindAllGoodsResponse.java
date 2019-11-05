package com.seven.fzuborrow.network.response;

import com.seven.fzuborrow.data.Good;
import com.squareup.moshi.Json;

import java.util.List;

public class FindAllGoodsResponse {

    private int code;
    private String message;
    @Json(name="data")
    private List<Good> goodList;

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

    public List<Good> getGoodList() {
        return goodList;
    }

    public void setGoodList(List<Good> goodList) {
        this.goodList = goodList;
    }
}
