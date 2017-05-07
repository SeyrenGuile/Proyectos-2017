package com.example.jorge.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;


public class SolicitudResultados extends AsyncTask<String, Void, List<String>> {

    private final int puerto = 5000;
    private Activity actividad;
    private ProgressDialog pDialog;
    private Socket socket;
    private ObjectInputStream entrada;
    private DataOutputStream salida;

    public SolicitudResultados(Activity actividad){
        this.actividad = actividad;
        pDialog = new ProgressDialog(actividad);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected List<String> doInBackground(String... params) {
        String ip = params[0];
        String opcion = params[1];
        String consulta = params[2];
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, puerto), 3000);
            entrada = new ObjectInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
            salida.writeUTF(opcion + "," + consulta);
            List<String> resultados = (List<String>) entrada.readObject();
            return resultados;
        } catch (IOException e) {
            Log.i("ErrorIO", "Error en el socket");
        } catch (ClassNotFoundException e) {
            Log.i("ErrorNotFound", "Error en el socket");
        }
        return null;
    }

    @Override
    protected void onPreExecute(){
        pDialog.setMessage("Cargando...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(List<String> result) {
        if (!result.isEmpty()){
            ListView listview = (ListView) actividad.findViewById(R.id.LLista1);
            ArrayAdapter adapter = new ArrayAdapter(actividad, android.R.layout.simple_list_item_1, result);
            listview.setAdapter(adapter);
        }
        else{
            Intent intent = new Intent(actividad, NoResultados.class);
            actividad.startActivity(intent);
            actividad.finish();
        }
        pDialog.dismiss();
    }

}
