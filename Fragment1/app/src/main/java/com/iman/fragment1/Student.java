package com.iman.fragment1;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Student implements Parcelable {

    public String id = "";
    public String name = "";
    public int score = 0;

    public Student(String id, String name, int score){
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public static List<Student> sampleStudentList(){
        ArrayList<Student> list = new ArrayList<>();
        int i = 0;
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1236" + ++i, "fatima", 96));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1236" + ++i, "fatima", 96));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1236" + ++i, "fatima", 96));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1236" + ++i, "fatima", 96));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1236" + ++i, "fatima", 96));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1236" + ++i, "fatima", 96));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1236" + ++i, "fatima", 96));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1236" + ++i, "fatima", 96));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1236" + ++i, "fatima", 96));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1236" + ++i, "fatima", 96));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1236" + ++i, "fatima", 96));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));
        list.add(new Student("1236" + ++i, "fatima", 96));
        list.add(new Student("1234" + ++i, "iman", 100));
        list.add(new Student("1235" + ++i, "akbar", 98));

        return list;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(score);
    }



    public Student(Parcel parcel){
        this.id = parcel.readString();
        this.name = parcel.readString();
        this.score = parcel.readInt();
    }

    public static Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
