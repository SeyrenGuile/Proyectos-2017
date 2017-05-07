package com.example.jorge.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BusquedaCategoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_categoria);
    }

    public void obtenerCategoria(View view) {
        Button b = (Button)view;
        String categoria = b.getText().toString();
        Log.d("myTag", categoria);

        Intent intent = new Intent(this, ResultadoCategoria.class);
        startActivity(intent);
    }
}