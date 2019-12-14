package com.facci.genesismendoza.practica_orm.Caracteristicas.CrearCliente;

public class Cliente {
    private long id;
    private String name;
    private String cedula;
    private String phoneNumber;
    private String email;

    public Cliente(int id, String name, String cedula, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.cedula = cedula;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
