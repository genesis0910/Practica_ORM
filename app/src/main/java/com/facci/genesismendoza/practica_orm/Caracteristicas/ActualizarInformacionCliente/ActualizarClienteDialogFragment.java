package com.facci.genesismendoza.practica_orm.Caracteristicas.ActualizarInformacionCliente;

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
import com.facci.genesismendoza.practica_orm.Caracteristicas.CrearCliente.Cliente;
import com.facci.genesismendoza.practica_orm.R;

public class ActualizarClienteDialogFragment extends DialogFragment {

    private static String clienteRegNo;
    private static int clienteItemPosition;
    private static ActualizarClienteListener actualizarClienteListener;

    private Cliente mCliente;

    private EditText nameEditText;
    private EditText registrationEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private Button updateButton;
    private Button cancelButton;

    private String nameString = "";
    private String cedula = "";
    private String phoneString = "";
    private String emailString = "";

    private DatabaseQueryClass databaseQueryClass;

    public ActualizarClienteDialogFragment() {
        // Required empty public constructor
    }

    public static ActualizarClienteDialogFragment newInstance(String cedula, int position, ActualizarClienteListener listener){
        clienteRegNo = cedula;
        clienteItemPosition = position;
        actualizarClienteListener = listener;
        ActualizarClienteDialogFragment actualizarClienteDialogFragment = new ActualizarClienteDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update information");
        actualizarClienteDialogFragment.setArguments(args);

        actualizarClienteDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return actualizarClienteDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_update_dialog, container, false);

        databaseQueryClass = new DatabaseQueryClass(getContext());

        nameEditText = view.findViewById(R.id.clienteNameEditText);
        registrationEditText = view.findViewById(R.id.registrationEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        updateButton = view.findViewById(R.id.updateClienteInfoButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(DatosBD.TITLE);
        getDialog().setTitle(title);

        mCliente = databaseQueryClass.getClienteByRegNum(clienteRegNo);

        if(mCliente !=null){
            nameEditText.setText(mCliente.getName());
            registrationEditText.setText(String.valueOf(mCliente.getCedula()));
            phoneEditText.setText(mCliente.getPhoneNumber());
            emailEditText.setText(mCliente.getEmail());

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameString = nameEditText.getText().toString();
                    cedula = registrationEditText.getText().toString();
                    phoneString = phoneEditText.getText().toString();
                    emailString = emailEditText.getText().toString();

                    mCliente.setName(nameString);
                    mCliente.setCedula(cedula);
                    mCliente.setPhoneNumber(phoneString);
                    mCliente.setEmail(emailString);

                    long id = databaseQueryClass.updateClienteInfo(mCliente);

                    if(id>0){
                        actualizarClienteListener.onClienteInfoUpdated(mCliente, clienteItemPosition);
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

        }

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
