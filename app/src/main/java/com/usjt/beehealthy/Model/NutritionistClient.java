package com.usjt.beehealthy.Model;

import java.io.Serializable;

public class NutritionistClient implements Serializable{

    public Long idclient;
    public Nutritionist nutritionist;
    public Patient patient;

    public NutritionistClient(Long idclient, Patient patient){
        this.idclient = idclient;
        this.patient = patient;
    }


    public Long getIdclient() {
        return idclient;
    }
    public void setIdclient(Long idclient) {
        this.idclient = idclient;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public Nutritionist getNutritionist() {
        return nutritionist;
    }
    public void setNutritionist(Nutritionist nutritionist) {
        this.nutritionist = nutritionist;
    }

}
