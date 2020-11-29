package com.example.cvmaker.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "cvModel_tb")
public class CVModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pid")
    private int pid;

    // Achievement Frag
    @ColumnInfo(name = "picture")
    private String picture;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "mobileNo")
    private String mobileNo;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "email")
    private String email;

    // About Frag
    @ColumnInfo(name = "profession")
    private String profession;

    @ColumnInfo(name = "yourself")
    private String yourself;

    // eduction frag
    @ColumnInfo(name = "institute")
    private String institute;

    @ColumnInfo(name = "from")
    private String from;

    @ColumnInfo(name = "to")
    private String to;


    @ColumnInfo (name="marks")
    private String marks;

    // Experienced frag
    @ColumnInfo(name = "experienced")
    private String experienced;

    // language frag
    @ColumnInfo (name = "language")
    private String language;

    // project frag
    @ColumnInfo(name = "projectName")
    private String projectName;

    @ColumnInfo (name = "projectDes")
    private String projectDes;

    @ColumnInfo (name = "projectTools")
    private String projectTools;

    // skill frag
    @ColumnInfo(name = "skill")
    private String skill;

    // achievement  frag
    @ColumnInfo (name = "achievement")
    private String achievement;



    public int getId() {
        return pid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // about Fragment
    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getYourself() {
        return yourself;
    }

    public void setYourself(String yourself) {
        this.yourself = yourself;
    }

    // education model
    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    // experienced frag
    public String getExperienced() {
        return experienced;
    }

    public void setExperienced(String experienced) {
        this.experienced = experienced;
    }

    // language frag
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    // project model
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDes() {
        return projectDes;
    }

    public void setProjectDes(String projectDes) {
        this.projectDes = projectDes;
    }

    public String getProjectTools() {
        return projectTools;
    }

    public void setProjectTools(String projectTools) {
        this.projectTools = projectTools;
    }

    // skill frag
    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    // achievement frag
    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }
}
