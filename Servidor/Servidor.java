import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    
    private final int puerto = 5000;
    private ServerSocket socketServidor;
    private Socket socketCliente;
    
    /* Se encarga de crear un socket servidor en el puerto 5000 y genera hilos para recibir
    y enviar datos a los clientes */
    public void iniciar(){
        try {
            socketServidor = new ServerSocket(puerto);
            System.out.println("Esperando conexiones");
            while(true){
                socketCliente = socketServidor.accept();
                //Se crea un hilo para atender la consulta
                Runnable cliente = new Cliente(socketCliente);
                Thread hilo = new Thread(cliente);
                hilo.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
