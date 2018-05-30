package com.example.yon_pc.recupmmv2;

/**
 * Created by Yon-PC on 30/05/2018.
 */

public class Viajes {

    private String Origen, Destino;
    private float Precio;
    int ids;

    public Viajes (String org, String des, float pre, int id){
        Origen = org;
        Destino = des;
        Precio = pre;
        ids = id;
    }

    public String getOrigen(){return Origen;}

    public String getDestino(){return Destino;}

    public float getPrecio(){return Precio;}

    public int getId(){return ids;}



}
