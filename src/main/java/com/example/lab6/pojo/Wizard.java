package com.example.lab6.pojo;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Wizard")

public class Wizard {
    @Id
    private String _id;
    private String name, sex, school, house, position;
    private Double money;
    public Wizard() {}
    public Wizard(String _id, String name, String sex, String school, String house, String position, Double money) {
        this._id = _id;
        this.name = name;
        this.sex = sex;
        this.school = school;
        this.house = house;
        this.position = position;
        this.money = money;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getPosition() {
        return position;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
