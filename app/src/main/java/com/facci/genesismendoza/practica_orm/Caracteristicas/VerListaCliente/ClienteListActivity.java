package com.facci.genesismendoza.practica_orm.Caracteristicas.VerListaCliente;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facci.genesismendoza.practica_orm.Caracteristicas.CrearCliente.Cliente;
import com.facci.genesismendoza.practica_orm.Database.DatabaseQueryClass;
import com.facci.genesismendoza.practica_orm.Caracteristicas.CrearCliente.ClienteCrearDialogFragment;
import com.facci.genesismendoza.practica_orm.Caracteristicas.CrearCliente.ClienteCreateListener;
import com.facci.genesismendoza.practica_orm.R;
import com.facci.genesismendoza.practica_orm.Datos_BD.DatosBD;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ClienteListActivity extends AppCompatActivity implements ClienteCreateListener {

    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Cliente> clienteList = new ArrayList<>();

    private TextView ListEmptyTextView;
    private RecyclerView recyclerView;
    private ClienteListRecyclerViewAdapter clienteListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.clienteRecyclerView);
        ListEmptyTextView = (TextView) findViewById(R.id.emptyClienteListTextView);

        clienteList.addAll(databaseQueryClass.getAllCliente());

        clienteListRecyclerViewAdapter = new ClienteListRecyclerViewAdapter(this, clienteList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(clienteListRecyclerViewAdapter);

        viewVisibility();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClienteCreateDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_delete){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("¿Estas seguro de borrar toda la lista de clientes?");
            alertDialogBuilder.setPositiveButton("Si",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            boolean isAllDeleted = databaseQueryClass.deleteAllCliente();
                            if(isAllDeleted){
                                clienteList.clear();
                                clienteListRecyclerViewAdapter.notifyDataSetChanged();
                                viewVisibility();
                            }
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

        return super.onOptionsItemSelected(item);
    }

    public void viewVisibility() {
        if(clienteList.isEmpty())
            ListEmptyTextView.setVisibility(View.VISIBLE);
        else
            ListEmptyTextView.setVisibility(View.GONE);
    }

    private void openClienteCreateDialog() {
        ClienteCrearDialogFragment clienteCrearDialogFragment = ClienteCrearDialogFragment.newInstance("Crear Cliente", this);
        clienteCrearDialogFragment.show(getSupportFragmentManager(), DatosBD.CREAR_CLIENTE);
    }

    @Override
    public void onClienteCreated(Cliente cliente) {
        clienteList.add(cliente);
        clienteListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
    }

}
