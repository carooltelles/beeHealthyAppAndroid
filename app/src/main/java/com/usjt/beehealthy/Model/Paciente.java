package com.usjt.beehealthy.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Paciente extends Usuario implements Serializable {

    private Double weight;
    private Double height;
    private String description;

    public Paciente(String nome, String email, String senha, String nascimento, Double weight, Double height, String description) {
        super(nome, email, senha, "patient", nascimento);
        this.weight = weight;
        this.height = height;
        this.description = description;
    }

    public Paciente() {

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


    public Paciente(int id,String nome, String email, String senha, String nascimento) {
        super(id,nome, email, senha, "patient", nascimento);
    }

    public Paciente(String nome, String email, String senha, String nascimento) {
        super(nome, email, senha, "patient", nascimento);
    }

    public JSONObject json(){
        JSONObject json = new JSONObject();
        try {
            json.put("iduser",getId());
            json.put("fullname", getNome());
            json.put("email", getEmail());
            json.put("password", getSenha());
            json.put("type", getTipo());
            json.put("birthday",getNascimento());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
