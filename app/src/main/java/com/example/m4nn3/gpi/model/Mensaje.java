package com.example.m4nn3.gpi.model;

import com.google.gson.annotations.SerializedName;

public class Mensaje {
    @SerializedName("msj")
    String mensaje;

    public Mensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
