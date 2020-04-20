package com.ravikantsingh.hackathon;

public class MedicineModalClass {
    String applicantname;
    String diseasename;
    String medicinename;
    String roomno;

    public MedicineModalClass(String applicantname, String diseasename, String medicinename, String roomno) {
        this.applicantname = applicantname;
        this.diseasename = diseasename;
        this.medicinename = medicinename;
        this.roomno = roomno;
    }

    public MedicineModalClass() {
    }

    public String getApplicantname() {
        return applicantname;
    }

    public void setApplicantname(String applicantname) {
        this.applicantname = applicantname;
    }

    public String getDiseasename() {
        return diseasename;
    }

    public void setDiseasename(String diseasename) {
        this.diseasename = diseasename;
    }

    public String getMedicinename() {
        return medicinename;
    }

    public void setMedicinename(String medicinename) {
        this.medicinename = medicinename;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }
}
