package com.example.jorge.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ResultadoCategoria extends AppCompatActivity {

    public final static String EXTRA_CONSULTA = "consulta";
    public final static String EXTRA_OPCION = "opcion";
    public final static String EXTRA_POSDOCUMENTO = "pos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_categoria);

        Intent intent = getIntent();
        final String consulta = intent.getStringExtra(BusquedaSencilla.EXTRA_CONSULTA);
        final String ip = MainActivity.IP;

        if(!consulta.isEmpty()) {
            final Activity actividad = this;
            SolicitudResultados solicitud = new SolicitudResultados(actividad);
            solicitud.execute(ip, "Busqueda por categoria", consulta);

            ListView listview = (ListView) findViewById(R.id.LLista1);
            listview.setClickable(true);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(actividad, Documento.class);
                    intent.putExtra(EXTRA_OPCION, "Busqueda por categoria");
                    intent.putExtra(EXTRA_CONSULTA, consulta);
                    intent.putExtra(EXTRA_POSDOCUMENTO, String.valueOf(position));
                    startActivity(intent);
                }
            });
        }
        else{
            Intent intent2 = new Intent(this, NoResultados.class);
            startActivity(intent2);
        }
    }
}
