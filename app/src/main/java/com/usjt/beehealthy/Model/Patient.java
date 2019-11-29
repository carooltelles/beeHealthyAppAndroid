package com.usjt.beehealthy.Model;


import java.io.Serializable;
import java.util.Date;

public class Patient extends User implements Serializable{

    private static final long serialVersionUID = 1L;
    public Double weight;
    public Double height;
    public String description;

    public Patient(Long iduser , String email, String fullname, String birthday, Double weight, Double height,String description ) {
        this.iduser = iduser;
        this.email = email;
        this.fullname = fullname;
        this.birthday = birthday;
        this.weight = weight;
        this.height = height;
        this.description = description;
    }

    public Patient(String email, String fullname, String birthday, Double weight, Double height,String description ) {
        this.email = email;
        this.fullname = fullname;
        this.birthday = birthday;
        this.weight = weight;
        this.height = height;
        this.description = description;
    }

    public Patient(){

    }


    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
