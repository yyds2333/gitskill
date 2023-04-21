package com.powernode.domain;

public class Student {
    private long id;
    private long stuId;
    private String stuName;
    private String gender;
    private String password;
    private int projectId;

    public Student() {
    }

    public Student(long id, long stuId, String stuName, String gender, String password, int projectId) {
        this.id = id;
        this.stuId = stuId;
        this.stuName = stuName;
        this.gender = gender;
        this.password = password;
        this.projectId = projectId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStuId() {
        return stuId;
    }

    public void setStuId(long stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", stuId=" + stuId +
                ", stuName='" + stuName + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                ", projectId='" + projectId + '\'' +
                '}';
    }
}
