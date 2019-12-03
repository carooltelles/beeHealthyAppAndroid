package com.usjt.beehealthy.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class Nutricionista extends Usuario implements Serializable {

    private int crn;
    private String specialization;
    private String local;

    public Nutricionista(String nome, String email, String senha, String nascimento, int crn,
                         String specialization, String local) {
        super(nome,email,senha,"nutritionist", nascimento);
        this.crn = crn;
        this.specialization = specialization;
        this.local = local;
    }

    public Nutricionista(int IdUser,String nome,String email, String senha, String nascimento,  int crn,
                         String specialization, String local){
        super(IdUser,nome,email,senha,"Nutritionist", nascimento);
        this.crn = crn;
        this.specialization = specialization;
        this.local = local;
    }

    public JSONObject array() {
        JSONObject array = new JSONObject();
        try {
            array.put("fullname", getNome());
            array.put("email", getEmail());
            array.put("password", getSenha());
            array.put("type", getTipo());
            array.put("specialization", getSpecialization());
            array.put("crn", getCrn());
            array.put("address",getLocal());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public int getCrn() {
        return crn;
    }

    public void setCrn(int crn) {
        this.crn = crn;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }


    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

}
