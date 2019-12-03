package com.usjt.beehealthy.Model;

import java.io.Serializable;

public class PlanoNutricionista implements Serializable {
    private int id;
    private String DiaSem,cafe,almoco,lanche,Nutricionista;

    public PlanoNutricionista(int id, String diaSem, String cafe, String almoco, String lanche, String nutricionista) {
        this.id = id;
        DiaSem = diaSem;
        this.cafe = cafe;
        this.almoco = almoco;
        this.lanche = lanche;
        Nutricionista = nutricionista;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiaSem() {
        return DiaSem;
    }

    public void setDiaSem(String diaSem) {
        DiaSem = diaSem;
    }

    public String getCafe() {
        return cafe;
    }

    public void setCafe(String cafe) {
        this.cafe = cafe;
    }

    public String getAlmoco() {
        return almoco;
    }

    public void setAlmoco(String almoco) {
        this.almoco = almoco;
    }

    public String getLanche() {
        return lanche;
    }

    public void setLanche(String lanche) {
        this.lanche = lanche;
    }

    public String getNutricionista() {
        return Nutricionista;
    }

    public void setNutricionista(String nutricionista) {
        Nutricionista = nutricionista;
    }
}
