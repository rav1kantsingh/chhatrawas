package com.ravikantsingh.hackathon;

public class ModalClass {

    private String applicantname;
    private String blooggroup;
    private String date;
    private String time;
    private String description;

    public ModalClass() {
    }

    public ModalClass(String applicantname, String blooggroup, String date, String time, String description) {
        this.applicantname = applicantname;
        this.blooggroup = blooggroup;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public String getApplicantname() {
        return applicantname;
    }

    public void setApplicantname(String applicantname) {
        this.applicantname = applicantname;
    }

    public String getBlooggroup() {
        return blooggroup;
    }

    public void setBlooggroup(String blooggroup) {
        this.blooggroup = blooggroup;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
