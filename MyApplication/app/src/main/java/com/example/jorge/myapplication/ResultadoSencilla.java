package com.example.jorge.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class ResultadoSencilla extends AppCompatActivity {

    public final static String EXTRA_CONSULTA = "consulta";
    public final static String EXTRA_OPCION = "opcion";
    public final static String EXTRA_POSDOCUMENTO = "pos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_sencilla);

        Intent intent = getIntent();
        final String consulta = intent.getStringExtra(BusquedaSencilla.EXTRA_CONSULTA);
        final String ip = MainActivity.IP;

        Log.i("Error", "Llegue aqui " + ip);

        final Activity actividad = this;
        SolicitudResultados solicitud = new SolicitudResultados(actividad);
        solicitud.execute(ip, "Busqueda por palabra", consulta);

        Calendar fecha = Calendar.getInstance();
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int year = fecha.get(Calendar.YEAR);
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(openFileOutput("Historial.txt", MODE_APPEND));
            writer.write(consulta + "-" + (dia + "/" + mes + "/" + year) + "\n");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListView listview = (ListView) findViewById(R.id.LLista1);
        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(actividad, Documento.class);
                intent.putExtra(EXTRA_OPCION, "Busqueda por palabra");
                intent.putExtra(EXTRA_CONSULTA, consulta);
                intent.putExtra(EXTRA_POSDOCUMENTO, String.valueOf(position));
                startActivity(intent);
            }
        });

    }
}
