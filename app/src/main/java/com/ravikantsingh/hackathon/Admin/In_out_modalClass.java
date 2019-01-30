package com.ravikantsingh.hackathon.Admin;

public class In_out_modalClass {
    String name,roomNo,message,time;

    public In_out_modalClass(String name, String roomNo, String message, String time) {
        this.name = name;
        this.roomNo = roomNo;
        this.message = message;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
