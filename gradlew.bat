package org.ceti.ventas;

import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Cliente extends AppCompatActivity {
    CheckBox chkAceptar;
    Button btnRegistrar;
    EditText etDni_cliente, etApellido_paterno, etApellido_materno, etNombres, etCelular;
    String dni_cliente = "", apellido_paterno = "", apellido_materno = "", nombres = "", celular = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        chkAceptar = (CheckBox) findViewById(R.id.chkAceptar);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setEnabled(false);
        etDni_cliente = (EditText) findViewById(R.id.etDni_cliente);
        etApellido_paterno = (EditText) findViewById(R.id.etApellido_paterno);
        etApellido_materno = (EditText) findViewById(R.id.etApellido_materno);
        etNombres = (EditText) findViewById(R.id.etNombres);
        etCelular = (EditText) findViewById(R.id.etCelular);
        etDni_cliente.requestFocus();
    }

    public void onActivar(View view) {
        if (chkAceptar.isChecked() == true) {
            btnRegistrar.setEnabled(true);
        } else {
            btnRegistrar.setEnabled(false);
        }
    }


    public void onRegistrar(View view) {
        dni_cliente = etDni_cliente.getText().toString();
        Log.i("depurar", "click");
        Thread tr = new Thread() {
            @Override
            public void run() {
                apellido_paterno = etApellido_paterno.getText().toString();
                apellido_materno = etApellido_materno.getText().toString();
                nombres = etNombres.getText().toString();
                celular = etCelular.getText().toString();
                f