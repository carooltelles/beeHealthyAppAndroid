package com.usjt.beehealthy.Model;

import java.io.Serializable;
import java.util.Date;

public class Nutritionist extends User implements Serializable{

    private static final long serialVersionUID = 1L;


    public String specialization;
    public String crn;
    public String address;

    public Nutritionist(Long iduser , String email, String fullname,String password, String birthday, String specialization, String crn, String address) {
        this.iduser = iduser;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.birthday = birthday;
        this.specialization = specialization;
        this.crn = crn;
        this.address = address;
    }

    public Nutritionist(Long iduser , String email, String fullname, String birthday, String specialization, String crn, String address) {
        this.iduser = iduser;
        this.email = email;
        this.fullname = fullname;
        this.birthday = birthday;
        this.specialization = specialization;
        this.crn = crn;
        this.address = address;
    }

    public Nutritionist(){

    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
