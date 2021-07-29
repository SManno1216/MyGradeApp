package com.mannosteven.mygradeapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ExampleItem implements Parcelable {

    private int mImageResource;
    private String mText1;
    private String mText2;
    private StudentClass studentClass;

    public ExampleItem(int imageResource, String text1, String text2, StudentClass studentClass) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        this.studentClass = studentClass;
    }

    protected ExampleItem(Parcel in) {
        mImageResource = in.readInt();
        mText1 = in.readString();
        mText2 = in.readString();
        studentClass = in.readParcelable(this.getClass().getClassLoader());
    }

    public static final Creator<ExampleItem> CREATOR = new Creator<ExampleItem>() {
        @Override
        public ExampleItem createFromParcel(Parcel in) {
            return new ExampleItem(in);
        }

        @Override
        public ExampleItem[] newArray(int size) {
            return new ExampleItem[size];
        }
    };

    public int getImageResource() {
        return mImageResource;
    }

    public void setImageResource(int imageResource) {
        mImageResource = imageResource;
    }

    public String getText1() {
        return mText1;
    }

    public void setText1(String text1) {
        mText1 = text1;
    }

    public String getText2() {
        return mText2;
    }

    public void setText2(String text2) {
        mText2 = text2;
    }

    public StudentClass getStudentClass(){
        return studentClass;
    }
    public void setStudentClass(StudentClass studentClass){
        this.studentClass = studentClass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mImageResource);
        dest.writeString(mText1);
        dest.writeString(mText2);
        dest.writeParcelable(studentClass, flags);
    }
}
