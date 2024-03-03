/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package psp.fruits.biz;

import java.io.Serializable;

/**
 *
 * @author Luis Quintano
 */
public class Fruit implements Serializable {
    private byte[] data;
    private int kilos;
    private String type;
    
    //constructor
    public Fruit(byte[] bytesNombre, int kilos, String tipo) {
        this.data = bytesNombre;
        this.kilos = kilos;
        this.type = tipo;
    }

    //getters and setters
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getKilos() {
        return kilos;
    }

    public void setKilos(int kilos) {
        this.kilos = kilos;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
