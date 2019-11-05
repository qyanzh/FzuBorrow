package com.seven.fzuborrow.network.response;

import com.seven.fzuborrow.data.Comment;

import java.util.List;

public class FindAllCommentResponse {
    private int code;
    private String message;
    private List<Comment> commentList;

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

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
