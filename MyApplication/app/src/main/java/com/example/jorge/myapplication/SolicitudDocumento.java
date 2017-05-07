package com.example.jorge.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


public class SolicitudDocumento extends AsyncTask<String, Void, String> {

    private final int puerto = 5000;
    private Activity actividad;
    private ProgressDialog pDialog;
    private Socket socket;
    private ObjectInputStream entrada;
    private DataOutputStream salida;

    public SolicitudDocumento(Activity actividad){
        this.actividad = actividad;
        pDialog = new ProgressDialog(actividad);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected String doInBackground(String... params) {
        String ip = params[0];
        String opcion = params[1];
        String consulta = params[2];
        String posDocumento = params[3];
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, puerto), 3000);
            entrada = new ObjectInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
            salida.writeUTF(opcion + "," + consulta + "," + posDocumento);

            String documento = (String) entrada.readObject();
            return documento;
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
    protected void onPostExecute(String result) {
        if (result != null){
            TextView textview = (TextView) actividad.findViewById(R.id.textView4);
            if(result.isEmpty()){
                result = "Este documento no tiene el contenido";
            }
            textview.setText(result);
        }
        pDialog.dismiss();
    }

}
