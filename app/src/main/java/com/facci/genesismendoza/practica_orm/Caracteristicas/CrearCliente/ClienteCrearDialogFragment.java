package com.facci.genesismendoza.practica_orm.Caracteristicas.CrearCliente;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.facci.genesismendoza.practica_orm.Database.DatabaseQueryClass;
import com.facci.genesismendoza.practica_orm.Datos_BD.DatosBD;
import com.facci.genesismendoza.practica_orm.R;

public class ClienteCrearDialogFragment extends DialogFragment {

    private static ClienteCreateListener clienteCreateListener;

    private EditText nameEditText;
    private EditText registrationEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private Button createButton;
    private Button cancelButton;

    private String nameString = "";
    private String cedula = "";
    private String phoneString = "";
    private String emailString = "";

    public ClienteCrearDialogFragment() {
        // Required empty public constructor
    }

    public static ClienteCrearDialogFragment newInstance(String title, ClienteCreateListener listener){
        clienteCreateListener = listener;
        ClienteCrearDialogFragment clienteCrearDialogFragment = new ClienteCrearDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        clienteCrearDialogFragment.setArguments(args);

        clienteCrearDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return clienteCrearDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_dialog, container, false);

        nameEditText = view.findViewById(R.id.clienteNameEditText);
        registrationEditText = view.findViewById(R.id.registrationEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(DatosBD.TITLE);
        getDialog().setTitle(title);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameString = nameEditText.getText().toString();
                cedula = registrationEditText.getText().toString();
                phoneString = phoneEditText.getText().toString();
                emailString = emailEditText.getText().toString();

                Cliente cliente = new Cliente(-1, nameString, cedula, phoneString, emailString);

                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());

                long id = databaseQueryClass.insertCliente(cliente);

                if(id>0){
                    cliente.setId(id);
                    clienteCreateListener.onClienteCreated(cliente);
                    getDialog().dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }

}
