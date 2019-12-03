package com.usjt.beehealthy.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Horario  implements Serializable {

    private String horario;

    public Horario(String horario) {
        this.horario = horario;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @NonNull
    @Override
    public String toString() {
        return "Horario: " + horario;
    }
}


