package com.seven.fzuborrow.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

public class Apply implements Parcelable {
    public Apply(){}
    private int rid;
    private int uid;
    private int gid;

    @Json(name="req_name")
    private long requestTime;

    @Json(name="start_name")
    private long startTime;

    @Json(name="end_name")
    private long endTime;

    private String reason;
    private int status;

    @Json(name="resp")
    private String response;

    private int pid;

    protected Apply(Parcel in) {
        rid = in.readInt();
        uid = in.readInt();
        gid = in.readInt();
        requestTime = in.readLong();
        startTime = in.readLong();
        endTime = in.readLong();
        reason = in.readString();
        status = in.readInt();
        response = in.readString();
        pid = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rid);
        dest.writeInt(uid);
        dest.writeInt(gid);
        dest.writeLong(requestTime);
        dest.writeLong(startTime);
        dest.writeLong(endTime);
        dest.writeString(reason);
        dest.writeInt(status);
        dest.writeString(response);
        dest.writeInt(pid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Apply> CREATOR = new Creator<Apply>() {
        @Override
        public Apply createFromParcel(Parcel in) {
            return new Apply(in);
        }

        @Override
        public Apply[] newArray(int size) {
            return new Apply[size];
        }
    };

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
