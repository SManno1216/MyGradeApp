package com.mannosteven.mygradeapp;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class StudentClass implements Parcelable {

    private String className;
    private int currentGrade;
    private int targetGrade;
    private ArrayList<SyllabusItem> sItems;

    public StudentClass(){

    }

    public StudentClass(String className, int currentGrade, int targetGrade, ArrayList<SyllabusItem> sItems) {
        this.className = className;
        this.currentGrade = currentGrade;
        this.targetGrade = targetGrade;
        this.sItems = sItems;
    }

    protected StudentClass(Parcel in) {
        className = in.readString();
        currentGrade = in.readInt();
        targetGrade = in.readInt();
        sItems = in.readArrayList(StudentClass.class.getClassLoader());
    }

    public static final Creator<StudentClass> CREATOR = new Creator<StudentClass>() {
        @Override
        public StudentClass createFromParcel(Parcel in) {
            return new StudentClass(in);
        }

        @Override
        public StudentClass[] newArray(int size) {
            return new StudentClass[size];
        }
    };

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCurrentGrade() {
        return currentGrade;
    }

    public void setCurrentGrade(int currentGrade) {
        this.currentGrade = currentGrade;
    }

    public int getTargetGrade() {
        return targetGrade;
    }

    public void setTargetGrade(int targetGrade) {
        this.targetGrade = targetGrade;
    }

    public ArrayList<SyllabusItem> getsItems() {
        return sItems;
    }

    public void setsItems(ArrayList<SyllabusItem> sItems) {
        this.sItems = sItems;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(className);
        dest.writeInt(currentGrade);
        dest.writeInt(targetGrade);
        dest.writeList(sItems);
    }
}
