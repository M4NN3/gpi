package com.example.m4nn3.gpi.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Cuidador implements Serializable{

    @SerializedName("cuidadorId")
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private String familia;
    @SerializedName("familiaTelefono")
    private String familiatelef;
    private String rfid;
    private Usuario usuario;

    public Cuidador() {
    }

    public Cuidador(String nombre, String correo, String telefono, String familia, String familiatelef, String rfid, Usuario usuario) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.familia = familia;
        this.familiatelef = familiatelef;
        this.rfid = rfid;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getFamiliatelef() {
        return familiatelef;
    }

    public void setFamiliatelef(String familiatelef) {
        this.familiatelef = familiatelef;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
