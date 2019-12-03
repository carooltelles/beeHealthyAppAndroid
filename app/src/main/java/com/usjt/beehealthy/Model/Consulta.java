package com.usjt.beehealthy.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Consulta implements Serializable {

    private List<Horario> horarios;
    private String data;
    private Nutricionista nutricionista;

    public Consulta(List<Horario> horarios, String data, Nutricionista nutricionista) {
        this.horarios = horarios;
        this.data = data;
        this.nutricionista = nutricionista;
    }

    public Nutricionista getNutricionista() {
        return nutricionista;
    }

    public void setNutricionista(Nutricionista nutricionista) {
        this.nutricionista = nutricionista;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getHorario(){
        return horarios.size();
    }

}
