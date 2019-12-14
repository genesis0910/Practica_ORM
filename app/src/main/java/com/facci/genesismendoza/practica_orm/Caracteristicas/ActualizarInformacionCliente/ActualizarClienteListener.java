package com.facci.genesismendoza.practica_orm.Caracteristicas.ActualizarInformacionCliente;


import com.facci.genesismendoza.practica_orm.Caracteristicas.CrearCliente.Cliente;

public interface ActualizarClienteListener {
    void onClienteInfoUpdated(Cliente cliente, int position);
}
