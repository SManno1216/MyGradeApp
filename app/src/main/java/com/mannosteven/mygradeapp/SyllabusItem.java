package com.mannosteven.mygradeapp;

import android.os.Parcel;
import android.os.Parcelable;

public class SyllabusItem implements Parcelable {

    public String itemName;
    public String weight;
    public String grade;

    public SyllabusItem(){


    }

    public SyllabusItem(String itemName, String weight, String grade) {
        this.itemName = itemName;
        this.weight = weight;
        this.grade = grade;
    }

    protected SyllabusItem(Parcel in) {
        itemName = in.readString();
        weight = in.readString();
        grade = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(weight);
        dest.writeString(grade);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SyllabusItem> CREATOR = new Creator<SyllabusItem>() {
        @Override
        public SyllabusItem createFromParcel(Parcel in) {
            return new SyllabusItem(in);
        }

        @Override
        public SyllabusItem[] newArray(int size) {
            return new SyllabusItem[size];
        }
    };

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {

        return "SyllabusItem{" +
                "itemName='" + itemName + '\'' +
                ", weight='" + weight + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
