package com.facci.genesismendoza.practica_orm.Caracteristicas.VerListaCliente;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.facci.genesismendoza.practica_orm.Caracteristicas.CrearCliente.Cliente;
import com.facci.genesismendoza.practica_orm.Database.DatabaseQueryClass;
import com.facci.genesismendoza.practica_orm.Caracteristicas.ActualizarInformacionCliente.ActualizarClienteDialogFragment;
import com.facci.genesismendoza.practica_orm.Caracteristicas.ActualizarInformacionCliente.ActualizarClienteListener;
import com.facci.genesismendoza.practica_orm.R;
import com.facci.genesismendoza.practica_orm.Datos_BD.DatosBD;

import java.util.List;

public class ClienteListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Cliente> clienteList;
    private DatabaseQueryClass databaseQueryClass;

    public ClienteListRecyclerViewAdapter(Context context, List<Cliente> clienteList) {
        this.context = context;
        this.clienteList = clienteList;
        databaseQueryClass = new DatabaseQueryClass(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.clientes_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Cliente cliente = clienteList.get(position);

        holder.nameTextView.setText(cliente.getName());
        holder.registrationNumTextView.setText(String.valueOf(cliente.getCedula()));
        holder.emailTextView.setText(cliente.getEmail());
        holder.phoneTextView.setText(cliente.getPhoneNumber());

        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("¿Seguro que quieres borrar a este cliente?");
                        alertDialogBuilder.setPositiveButton("Si",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        deleteCliente(itemPosition);
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActualizarClienteDialogFragment actualizarClienteDialogFragment = ActualizarClienteDialogFragment.newInstance(cliente.getCedula(), itemPosition, new ActualizarClienteListener() {
                    @Override
                    public void onClienteInfoUpdated(Cliente clientes, int position) {
                        clienteList.set(position, clientes);
                        notifyDataSetChanged();
                    }
                });
                actualizarClienteDialogFragment.show(((ClienteListActivity) context).getSupportFragmentManager(), DatosBD.ACTUALIZAR_CLIENTE);
            }
        });
    }

    private void deleteCliente(int position) {
        Cliente cliente = clienteList.get(position);
        long count = databaseQueryClass.deleteClienteByRegNum(cliente.getCedula());

        if(count>0){
            clienteList.remove(position);
            notifyDataSetChanged();
            ((ClienteListActivity) context).viewVisibility();
            Toast.makeText(context, "Cliente deleted successfully", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "Cliente not deleted. Something wrong!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return clienteList.size();
    }
}
