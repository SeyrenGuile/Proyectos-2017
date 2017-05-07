package com.example.jorge.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class BusquedaSencilla extends AppCompatActivity {

    public final static String EXTRA_CONSULTA = "consulta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_sencilla);
    }
    public void obtenerPalabra(View view) {
        EditText campo = (EditText) findViewById(R.id.TPalabra);
        String consulta = campo.getText().toString();

        Intent intent = new Intent(this, ResultadoSencilla.class);
        intent.putExtra(EXTRA_CONSULTA, consulta);
        startActivity(intent);
    }
}
