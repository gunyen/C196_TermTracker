package com.c196.c196_termtracker.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;


    private int termItemID;
    private String courseName;
    private String startDate;
    private String endDate;
    private int statusItem;
    private String status;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private String courseNotes;

    public Course(int courseID, int termItemID, String courseName, String startDate, String endDate, int statusItem,String status, String instructorName, String instructorPhone, String instructorEmail, String courseNotes) {
        this.courseID = courseID;
        this.termItemID = termItemID;
        this.courseName = courseName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusItem = statusItem;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.courseNotes = courseNotes;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getStatusItem() {
        return statusItem;
    }

    public void setStatusItem(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public int getTermItemID() {
        return termItemID;
    }

    public void setTermItemID(int termItemID) {
        this.termItemID = termItemID;
    }

    @Override
    public String toString() {
        return "course{" +
                "courseID=" + courseID +
                ", termItemID='" + termItemID + '\'' +
                ", courseName='" + courseName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", statusItem='" + statusItem + '\'' +
                ", status='" + status + '\'' +
                ", instructorName='" + instructorName + '\'' +
                ", instructorPhone='" + instructorPhone + '\'' +
                ", instructorEmail='" + instructorEmail + '\'' +
                ", courseNotes='" + courseNotes + '\'' +
                '}';
    }
}
