package com.seven.fzuborrow.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

public class Apply implements Parcelable {
    private int rid;
    private int uid;
    private int gid;

    @Json(name = "req_time")
    private long requestTime;

    @Json(name = "start_time")
    private long startTime;

    @Json(name = "end_time")
    private long endTime;

    private String reason;
    private int status;

    @Json(name = "resp")
    private String response;

    private int pid;
    private Float grade;


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
        if (in.readByte() == 0) {
            grade = null;
        } else {
            grade = in.readFloat();
        }
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
        if (grade == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(grade);
        }
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

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }



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

    @Override
    public String toString() {
        return "Apply{" +
                "rid=" + rid +
                ", uid=" + uid +
                ", gid=" + gid +
                ", requestTime=" + requestTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                ", response='" + response + '\'' +
                ", pid=" + pid +
                ", grade=" + grade +
                '}';
    }
}
