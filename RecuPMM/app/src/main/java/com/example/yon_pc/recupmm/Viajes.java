package com.example.yon_pc.recupmm;

/**
 * Created by Yon-PC on 25/05/2018.
 */

public class Viajes {

    String origen, destino, clase;
    Double precio;

    public Viajes(String org, String des, String clas, Double pre) {
        this.origen = org;
        this.destino = des;
        this.clase = clas;
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

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
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
                ", clase='" + clase + '\'' +
                ", precio=" + precio +
                '}';
    }
}
