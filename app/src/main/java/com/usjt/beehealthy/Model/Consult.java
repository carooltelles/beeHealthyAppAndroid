package com.usjt.beehealthy.Model;

import java.io.Serializable;
import java.util.Date;

public class Consult implements Serializable {

    private Long idconsult;
    private String place;
    private String date;
    private Nutritionist nutritionist;
    private Patient patient;

    public Consult(Long idconsult, String place, String date, Nutritionist nutritionist, Patient patient) {
        this.idconsult = idconsult;
        this.place = place;
        this.date = date;
        this.nutritionist = nutritionist;
        this.patient = patient;
    }

    public Long getIdconsult() {
        return idconsult;
    }

    public void setIdconsult(Long idconsult) {
        this.idconsult = idconsult;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Nutritionist getNutritionist() {
        return nutritionist;
    }

    public void setNutritionist(Nutritionist nutritionist) {
        this.nutritionist = nutritionist;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Consult [idconsult=" + idconsult + ", place=" + place + ", date=" + date + ", nutritionist="
                + nutritionist + ", patient=" + patient + "]";
    }
}
