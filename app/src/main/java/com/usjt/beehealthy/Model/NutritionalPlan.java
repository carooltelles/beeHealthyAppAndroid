package com.usjt.beehealthy.Model;

import java.io.Serializable;
public class NutritionalPlan implements Serializable{

    public Long idplan;
    public String weekDay;
    public String breakfast;
    public String lunch;
    public String dinner;
    public Nutritionist nutritionist;
    public Patient patient;


    public NutritionalPlan
            ( Long idplan, String weekDay, String breakfast,String lunch,
              String dinner, Nutritionist nutritionist,Patient patient){
        this.idplan = idplan;
        this.weekDay = weekDay;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.nutritionist = nutritionist;
        this.patient = patient;
    }

    public NutritionalPlan
            ( String weekDay, String breakfast,String lunch,
              String dinner, Nutritionist nutritionist,Patient patient){
        this.weekDay = weekDay;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.nutritionist = nutritionist;
        this.patient = patient;
    }

    public Long getIdplan() {
        return idplan;
    }

    public void setIdplan(Long idplan) {
        this.idplan = idplan;
    }

    public String getWeekDay() {
        return weekDay;
    }
    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }
    public String getBreakfast() {
        return breakfast;
    }
    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }
    public String getLunch() {
        return lunch;
    }
    public void setLunch(String lunch) {
        this.lunch = lunch;
    }
    public String getDinner() {
        return dinner;
    }
    public void setDinner(String dinner) {
        this.dinner = dinner;
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

