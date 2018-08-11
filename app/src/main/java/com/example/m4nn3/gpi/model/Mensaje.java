package com.example.m4nn3.gpi.model;

import com.google.gson.annotations.SerializedName;

public class Mensaje {
    @SerializedName("msj")
    private String mensaje;
    @SerializedName("success")
    private String estado;

    public Mensaje() {
    }

    public Mensaje(String mensaje, String estado) {
        this.mensaje = mensaje;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
