package com.example.workersmanager.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WorkerModel {
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("age")
    @Expose
    private int age;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("salary")
    @Expose
    private int salary;
    @SerializedName("position")
    @Expose
    private String position;

    public WorkerModel(String firstName, String lastName, int age, String gender, String info,
                       String data, int salary, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.info = info;
        this.data = data;
        this.salary = salary;
        this.position = position;
    }

    public WorkerModel(String _id, String firstName, String lastName, int age, String gender, String info,
                       String data, int salary, String position) {
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.info = info;
        this.data = data;
        this.salary = salary;
        this.position = position;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public class GetWorkers {
        @SerializedName("workers")
        @Expose
        private List<WorkerModel> workers = null;
        @SerializedName("countWorkers")
        @Expose
        private Integer countWorkers;

        public List<WorkerModel> getWorkers() {
            return workers;
        }

        public void setWorkers(List<WorkerModel> workers) {
            this.workers = workers;
        }

        public Integer getCountWorkers() {
            return countWorkers;
        }

        public void setCountWorkers(Integer countWorkers) {
            this.countWorkers = countWorkers;
        }
    }
}

