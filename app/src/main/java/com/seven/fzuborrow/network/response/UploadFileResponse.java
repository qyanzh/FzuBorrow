package com.seven.fzuborrow.network.response;

public class UploadFileResponse {
    int code;
    String message;
    Data data;
    static class Data {
        private String imgurl;

        public String getImgurl() {
//            return "http://49.235.150.59:8080/jiebei/img/get/?url=" + imgurl;
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
