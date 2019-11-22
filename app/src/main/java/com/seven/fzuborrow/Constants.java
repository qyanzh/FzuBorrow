package com.seven.fzuborrow;

public class Constants {

    //活动室或者物品
    public static final String GOOD_TYPE_GOOD = "good";
    public static final String GOOD_TYPE_ROOM = "room";
    //同意或者拒绝
    public static final int APPLY_TYPE_ACCEPT = 1;
    public static final int APPLY_TYPE_REJECT = 0;

    public static final int APPLY_STATUS_PENDING = 0;
    public static final int APPLY_STATUS_USING = 1;
    public static final int APPLY_STATUS_FINISHED = 2;
    public static final int APPLY_STATUS_REJECTED = 3;
    public static final int APPLY_STATUS_WAITING = 4; //等待拥有者确认已归还

    public static final int CONFIRM_TYPE_OK = 1; //拥有者确认归还
    public static final int CONFIRM_TYPE_NO = 0; //拥有者拒绝确认

    public static final int UPLOAD_TYPE_AVATAR = 1;
    public static final int UPLOAD_TYPE_GOOD = 2;
    public static final int UPLOAD_TYPE_DISC = 3;

    public static final int GOOD_STATUS_PENDING = 0;
    public static final int GOOD_STATUS_BORROWED = 1;
    public static final int GOOD_STATUS_AVAILABLE = 2;





}
