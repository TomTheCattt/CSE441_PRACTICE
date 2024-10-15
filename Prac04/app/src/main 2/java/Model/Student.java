package Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Student implements Parcelable {
    private String id;
    private FullName full_name;
    private String gender;
    private String birth_date;
    private String email;
    private String address;
    private String major;
    private double gpa;
    private int year;

    protected Student(Parcel in) {
        id = in.readString();
        gender = in.readString();
        birth_date = in.readString();
        email = in.readString();
        address = in.readString();
        major = in.readString();
        gpa = in.readDouble();
        year = in.readInt();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(gender);
        dest.writeString(birth_date);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(major);
        dest.writeDouble(gpa);
        dest.writeInt(year);
    }

    // Inner class for full name structure
    public static class FullName {
        private String first;
        private String last;
        private String midd;

        // Constructor
        public FullName(String first, String last, String midd) {
            this.first = first;
            this.last = last;
            this.midd = midd;
        }

        // Getters and setters
        public String getFirst() { return first; }
        public void setFirst(String first) { this.first = first; }
        public String getLast() { return last; }
        public void setLast(String last) { this.last = last; }
        public String getMidd() { return midd; }
        public void setMidd(String midd) { this.midd = midd; }
    }

    // Constructor
    public Student(String id, FullName full_name, String gender, String birth_date,
                   String email, String address, String major, double gpa, int year) {
        this.id = id;
        this.full_name = full_name;
        this.gender = gender;
        this.birth_date = birth_date;
        this.email = email;
        this.address = address;
        this.major = major;
        this.gpa = gpa;
        this.year = year;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public FullName getFull_name() { return full_name; }
    public void setFull_name(FullName full_name) { this.full_name = full_name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBirth_date() { return birth_date; }
    public void setBirth_date(String birth_date) { this.birth_date = birth_date; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    // Helper method to get full name as a single string
    public String getFullNameAsString() {
        return full_name.first + " " + full_name.midd + " " + full_name.last;
    }
}
