package com.example.jorge.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BusquedaCategoria extends AppCompatActivity {

    public final static String EXTRA_CONSULTA = "consulta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_categoria);
    }

    public void obtenerHimnos(View view) {
        String consulta = "himnos";

        Intent intent = new Intent(this, ResultadoCategoria.class);
        intent.putExtra(EXTRA_CONSULTA, consulta);
        startActivity(intent);
    }

    public void obtenerJovenes(View view) {
        String consulta = "jovenes";

        Intent intent = new Intent(this, ResultadoCategoria.class);
        intent.putExtra(EXTRA_CONSULTA, consulta);
        startActivity(intent);
    }

    public void obtenerAdultosSolteros(View view) {
        String consulta = "adultosSolteros";

        Intent intent = new Intent(this, ResultadoCategoria.class);
        intent.putExtra(EXTRA_CONSULTA, consulta);
        startActivity(intent);
    }

    public void obtenerSociedadSocorro(View view) {
        String consulta = "sociedadSocorro";

        Intent intent = new Intent(this, ResultadoCategoria.class);
        intent.putExtra(EXTRA_CONSULTA, consulta);
        startActivity(intent);
    }

    public void obtenerSacerdocio(View view) {
        String consulta = "sacerdocio";

        Intent intent = new Intent(this, ResultadoCategoria.class);
        intent.putExtra(EXTRA_CONSULTA, consulta);
        startActivity(intent);
    }
}