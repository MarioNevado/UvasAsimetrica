/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psp.fruits.biz;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Arrays;
import psp.fruits.tools.Utils;

/**
 *
 * @author Luis Quintano
 */
public class Manager extends Thread {
    private Socket costumer;
    private int id;
    private ObjetoCompartido uva;
    private ObjetoCompartido aceituna;
    private PublicKey publicKey;


    //constructor
    public Manager(Socket conexionCliente, int id, ObjetoCompartido ocUvas, ObjetoCompartido ocAceitunas, PublicKey keyPublica) {
        this.costumer = conexionCliente;
        this.id = id;
        this.uva = ocUvas;
        this.aceituna = ocAceitunas;
        this.publicKey = keyPublica;
    }


    //metodo Run
    @Override
    public void run() {
        Fruit fruit;
        String data;
        ObjectInputStream ois = null;
        try {
            do {
                ois = new ObjectInputStream(costumer.getInputStream());  //hacemos la conexion del cliente para recibir los datos que nos envíe.
                fruit = (Fruit) ois.readObject();  //se lee el objeto que recibimos del cliente y lo guardamos en una variable.
                byte[] decryptedData = Utils.decryptDataPublicKey(fruit.getData(), this.publicKey);  //pasamos el Array de bytes con el nombre al método de descifrar los datos con la clave y guardamos el nombre descifrado.
                System.out.println(Arrays.toString(decryptedData));
                data = new String(decryptedData);
                
                if (!data.equals("*")) {
                    if (fruit.getType().equals("U")) {
                        this.uva.addObjeto(data, fruit.getKilos(), fruit.getType());
                    } else if (fruit.getType().equals("A")) {
                        this.aceituna.addObjeto(data, fruit.getKilos(), fruit.getType());
                    }
                }
            } while (!data.equals("*"));
            //cerrando el cliente de manera normal.
            System.out.println("*Cierre Controlado del Cliente " + this.id + "*");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("CIERRE ABRUPTO DEL CLIENTE!!!ghj");
        } finally {
            if (this.costumer != null) {
                try {
                    this.costumer.close();
                } catch (IOException ex) {
                    System.err.println("ERROR al cerrar el Socket del Cliente");
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ex) {
                    System.err.println("ERROR al cerrar el ObjectOutputStream");
                }
            }
        }
    }
}
