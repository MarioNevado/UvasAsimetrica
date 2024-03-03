/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psp.fruits.biz;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Luis Quintano
 */
public class ObjetoCompartido {
    private TreeMap<String, Integer> fruits = new TreeMap<>();  //para ordenar las uvas Alfabeticamente tenemos que crear el mapa como TreeMap.
    //private int kilosTotales = 0;
    
    //metodos
    public synchronized void addObjeto(String data, int kilos, String type) {
        //para añadir uvas o aceitunas:
        if (!this.fruits.containsKey(data)) {
            this.fruits.put(data, kilos);
        } else {
                //this.kilosTotales = this.listaObjetos.get(tipoUva);
                this.fruits.put(data, this.fruits.get(data) + kilos);
        }
        //para mostrar las uvas o aceitunas:
        if (type.equals("U")) {
            System.out.println("--------Listado-de-UVAS--------");
        } else {
            System.out.println("--------Listado-de-ACEITUNAS--------");
        }
        for (Map.Entry<String, Integer> entry : this.fruits.entrySet()) {
            String keyTipo = entry.getKey();
            Integer valKilos = entry.getValue();
            
            System.out.println(keyTipo + ": " + valKilos + "Kg");
        }
    }
}
