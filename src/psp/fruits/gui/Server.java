package psp.fruits.gui;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;
import psp.fruits.tools.Configuration;
import psp.fruits.tools.Utils;
import psp.fruits.biz.Manager;
import psp.fruits.biz.ObjetoCompartido;

/**
 *
 * @author Luis Quintano
 */
public class Server {
    
    private static Socket conexionCliente;
    
    public static void main(String[] args) {
        int id = 0;
        File filePublicKey = new File(Configuration.PUBLIC_KEY_FILE);
        PublicKey keyPublica = null;
        ObjetoCompartido uva = new ObjetoCompartido();
        ObjetoCompartido aceituna = new ObjetoCompartido();
        if (filePublicKey.exists()) {  //si no se ha generado el fichero de la clave va a dar un error y no va a dejar arrancar el Server.
            try {
                keyPublica = Utils.getPublicKey(Configuration.PUBLIC_KEY_FILE);  //se genera la clave pasándole la ruta de donde está el archivo con la clave para cifrar y descifrar.
            } catch (Exception ex) {
                System.err.println("ERROR. El fichero de la clave Pública no es válido.");
                System.exit(1);  //hacemos que se salga del programa cuando salte una Exception.
            }
            try (ServerSocket ss = new ServerSocket(Configuration.SEND_PORT)) {
                System.out.println("Esperando clientes....");
                do {
                    conexionCliente = ss.accept();
                    id++;
                    Manager hs = new Manager(conexionCliente, id, uva, aceituna, keyPublica);
                    hs.start();
                } while (true);
            } catch (Exception ex) {
                System.err.println("ERROR en el Server: ");
                ex.printStackTrace();
            }
        } else {
            System.err.println("No has generado la clave Pública. Antes de arrancar el servidor, generala.");
        }
    }
}
