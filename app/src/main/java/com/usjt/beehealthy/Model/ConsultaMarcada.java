package com.usjt.beehealthy.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConsultaMarcada implements Serializable {
    private  int idConsult;
    private String data;
    private Nutricionista Nutricionist;
    private int idPaciente;
    private String Local;
    private String horario;

    public ConsultaMarcada(int idConsult,String data, Nutricionista nutricionist, String local, String horario) {
        this.idConsult = idConsult;
        this.data = data;
        Nutricionist = nutricionist;
        Local = local;
        this.horario = horario;
    }

    public int getId() {
        return idConsult;
    }

    public void setId(int idConsult) {
        this.idConsult = idConsult;
    }

    public Nutricionista getNutricionist() {
        return Nutricionist;
    }

    public void setNutricionist(Nutricionista nutricionist) {
        Nutricionist = nutricionist;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocal() {
        return Local;
    }

    public void setLocal(String local) {
        Local = local;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public ConsultaMarcada(String data, Nutricionista nutricionista, String local, String horario, int idPaciente) {
        this.data = data;
        this.Nutricionist = nutricionista;
        Local = local;
        this.horario = horario;
        this.idPaciente = idPaciente;
    }

    public String dataArray() {
        return getData() + " " + getHorario();
    }

    public JSONObject array() {
        JSONObject array = new JSONObject();
        try {
            array.put("date", dataArray());
            array.put("place", getLocal());
            array.put("idnutritionist", getNutricionist().getId());
            array.put("idpatient", getIdPaciente());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return array;
    }
}
