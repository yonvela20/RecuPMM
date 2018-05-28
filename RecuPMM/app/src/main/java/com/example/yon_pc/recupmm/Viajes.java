package com.example.yon_pc.recupmm;

import java.io.Serializable;

/**
 * Created by Yon-PC on 25/05/2018.
 */

public class Viajes implements Serializable {

    String origen, destino;
    Double precio;

    public Viajes(String org, String des, Double pre) {
        this.origen = org;
        this.destino = des;
        this.precio = pre;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Viajes{" +
                "origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", precio=" + precio +
                '}';
    }
}
