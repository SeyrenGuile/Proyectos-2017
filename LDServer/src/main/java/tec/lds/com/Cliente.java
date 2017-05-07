package tec.lds.com;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.queryparser.classic.ParseException;

public class Cliente implements Runnable {
    
    private DataInputStream entrada;
    private ObjectOutputStream salida;
    private Socket cliente;
    private String rutaDocumentos;
    
    public Cliente(Socket socketCliente){
        try {
            this.cliente = socketCliente;
            entrada = new DataInputStream(socketCliente.getInputStream());
            salida = new ObjectOutputStream(socketCliente.getOutputStream());
            rutaDocumentos = "colecciones";
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        try {
            System.out.println("Recibiendo... ");
            String mensaje = entrada.readUTF();
            System.out.println("Mensaje Obtenido: " + mensaje);
            String[] solicitud = mensaje.split(",");
            /*
            Cuando se requieran consultar las opciones disponibles de busqueda
            en la aplicacion Android
            */
            if(solicitud.length == 2){
                String tipo = solicitud[0];
                String consulta = solicitud[1];
                List<String> resultado = null;
                System.out.println("Tipo es: " + tipo);
                System.out.println("Consulta es: " + consulta);
                if (tipo.equals("Busqueda por palabra")){
                    IndexadorLucene index = new IndexadorLucene(rutaDocumentos);
                    resultado = index.encontrar(consulta);
                }else if (tipo.equals("Busqueda por categoria")){
                    IndexadorLucene index = new IndexadorLucene(rutaDocumentos + "/" + tipo);
                    resultado = index.encontrarCategoria(consulta);
                } 
                salida.writeObject(resultado);
            }
            else{
                String tipo = solicitud[0];
                String consulta = solicitud[1];
                String posDocumento = solicitud[2];
                
                IndexadorLucene index = new IndexadorLucene(rutaDocumentos);
                String resultado = null;
                if (tipo.equals("Busqueda por palabra")){
                    resultado = index.getContenido(consulta, posDocumento);
                }else if (tipo.equals("Busqueda por categoria")){
                    resultado = index.getContenidoCategoria(consulta, posDocumento);
                }                
                salida.writeObject(resultado);
                
            }
            salida.flush();
            cliente.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
