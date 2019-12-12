package com.facci.genesismendoza.practica_orm.Data;

public class Cliente {

    String nombre;
    String celular;
    String email;
    Integer id;

    public Cliente(Integer id, String nombre, String celular, String email) {
        this.nombre = nombre;
        this.celular = celular;
        this.email = email;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
