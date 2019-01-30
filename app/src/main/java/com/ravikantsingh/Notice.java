package com.ravikantsingh;

public class Notice {
    String Name,Time,image,NoticeText;

    public Notice(String name, String time, String image, String noticeText) {
        Name = name;
        Time = time;
        this.image = image;
        NoticeText = noticeText;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNoticeText() {
        return NoticeText;
    }

    public void setNoticeText(String noticeText) {
        NoticeText = noticeText;
    }
}
