package Model;

public class Student {
    private String mssv;
    private String name;
    private String className;
    private double gpa;

    public Student() {
    }

    public Student(String mssv, String name, String className, double gpa) {
        this.mssv = mssv;
        this.name = name;
        this.className = className;
        this.gpa = gpa;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}

