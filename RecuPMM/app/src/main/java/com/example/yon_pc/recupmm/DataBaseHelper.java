package com.example.yon_pc.recupmm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yon-PC on 25/05/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper{

    String usuarios = "CREATE TABLE Usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT,usuario TEXT, password TEXT)";
    String viajes = "CREATE TABLE Viajes (Origen TEXT, Destino TEXT, Precio DOUBLE)";
    String billetes = "CREATE TABLE Billetes (usuarios TEXT, Origen TEXT, Destino TEXT, Precio DOUBLE, Clase TEXT, Extras TEXT, " +
            "FOREIGN KEY (usuarios) REFERENCES Usuarios (id))";


    public DataBaseHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory almacen, int version) {
        super(contexto, nombre, almacen, version);
    }

    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(usuarios);
        bd.execSQL(viajes);
        bd.execSQL(billetes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int versionAnterior, int versionNueva) {
        //Eliminamos la version anterior de la tabla
        bd.execSQL("DROP TABLE IF EXISTS Usuarios");
        bd.execSQL(usuarios);

    }
}
