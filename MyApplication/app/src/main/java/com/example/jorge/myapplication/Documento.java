package com.example.jorge.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Documento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documento);

        Intent intent = getIntent();
        final String opcion = intent.getStringExtra(ResultadoSencilla.EXTRA_OPCION);
        final String consulta = intent.getStringExtra(ResultadoSencilla.EXTRA_CONSULTA);
        final String pos = intent.getStringExtra(ResultadoSencilla.EXTRA_POSDOCUMENTO);
        final String ip = MainActivity.IP;
        Log.i("Doc", "LLegue a la clase documento");

        SolicitudDocumento solicitud = new SolicitudDocumento(this);
        solicitud.execute(ip, opcion, consulta, pos);

    }
}
