package psp.fruits.gui;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



import java.io.*;
import java.net.Socket;
import java.security.PrivateKey;
import java.util.Scanner;
import psp.fruits.tools.Configuration;
import psp.fruits.tools.Utils;
import psp.fruits.biz.Fruit;

/**
 *
 * @author Luis Quintano
 */
public class Grapes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String name;
        int kilos;
        Fruit uva;
        File file = new File(Configuration.PRIVATE_KEY_FILE);
        PrivateKey privateKey = null;
        ObjectOutputStream oos = null;
        if (file.exists()) {  //si no se ha generado el fichero de la clave va a dar un error y no va a dejar arrancar el Cliente.
            try {
                privateKey = Utils.getPrivateKey(Configuration.PRIVATE_KEY_FILE);  //se genera la clave pasándole la ruta de donde está el archivo con la clave para cifrar y descifrar.
            } catch (Exception ex) {
                System.err.println("ERROR. El fichero de la clave Privada no es válido.");
                System.exit(1);  //hacemos que se salga del programa cuando salte una Exception.
            }
            try (Socket socket = new Socket(Configuration.HOST, Configuration.SEND_PORT)) {
                System.out.println("\tSi introduces un * dejará de pedir uvas y saldrá del programa.");
                do {
                    System.out.print("Nombre de Uva: ");
                    name = sc.nextLine();
                    byte[] encryptedData = Utils.encryptDataPrivateKey(name.getBytes(), privateKey);  //pasamos el nombre a Array de bytes y se lo pasamos al método de cifrar los datos con la clave y guardamos el nombre cifrado.
                    if (!name.equals("*")) {
                        System.out.print("Kilos de uva recogidos: ");
                        kilos = Integer.parseInt(sc.nextLine());
                        uva = new Fruit(encryptedData, kilos, "U");
                    } else {
                        uva = new Fruit(encryptedData, 0, "");
                    }
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(uva);
                } while (!name.equals("*"));
            } catch (Exception ex) {
                System.err.println("CIERRE ABRUPTO DEL SERVIDOR!!!");
            } finally {
                if (oos != null) {
                    try {
                        oos.close();
                    } catch (IOException ex) {
                        System.err.println("ERROR al cerrar el ObjectOutputStream: ");
                    }
                }
            }
        } else {
                System.err.println("No has generado la clave Privada. Antes de arrancar el cliente, generala.");
        }
    }
}
