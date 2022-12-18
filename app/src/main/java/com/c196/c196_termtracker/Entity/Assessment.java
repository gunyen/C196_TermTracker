package com.c196.c196_termtracker.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessment")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private int courseItemID;
    private String assessmentName;
    private int typeID;
    private String type;
    private String endDate;

    public Assessment(int assessmentID, String assessmentName, int typeID, String type, String endDate, int courseItemID) {
        this.assessmentID = assessmentID;
        this.courseItemID = courseItemID;
        this.assessmentName = assessmentName;
        this.typeID = typeID;
        this.type = type;
        this.endDate = endDate;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public int getCourseItemID() {
        return courseItemID;
    }

    public void setCourseItemID(int courseItemID) {
        this.courseItemID = courseItemID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "assessment{" +
                "assessmentID=" + assessmentID +
                ", courseItemID='" + courseItemID + '\'' +
                ", assessmentName='" + assessmentName + '\'' +
                ", typeID='" + typeID + '\'' +
                ", type='" + type + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
