package com.example.yon_pc.recupmmv2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yon-PC on 30/05/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    String usuarios = "CREATE TABLE Usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT,usuario TEXT, password TEXT)";
    String viajes = "CREATE TABLE Viajes (id INTEGER PRIMARY KEY AUTOINCREMENT, id_usuario INTEGER, Origen TEXT, Destino TEXT, Precio DOUBLE, FOREIGN KEY (id_usuario) REFERENCES Usuarios (id))";
    String billetes = "CREATE TABLE Billetes (id INTEGER PRIMARY KEY AUTOINCREMENT, id_billete INTEGER, extras TEXT, cantidad TEXT NOT NULL, clase TEXT NOT NULL, origen TEXT NOT NULL, destino TEXT NOT NULL, precio FLOAT NOT NULL, cliente TEXT, recibido TEXT)";

    public DataBaseHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory almacen, int version) {
        super(contexto, nombre, almacen, version);
    }

    private Context mCtx = null;
    private DataBaseHelper mDbHelper = null;
    private SQLiteDatabase mDb = null;

    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(usuarios);
        bd.execSQL(viajes);
        bd.execSQL(billetes);


        bd.execSQL("INSERT INTO Viajes (id, Origen, Destino, Precio) VALUES (null, 'Valencia','Amsterdam','220')");
        bd.execSQL("INSERT INTO Viajes (id, Origen, Destino, Precio) VALUES (null, 'Barcelona','Berlin','175')");
        bd.execSQL("INSERT INTO Viajes (id, Origen, Destino, Precio) VALUES (null, 'Castellon','Paris','260')");
        bd.execSQL("INSERT INTO Viajes (id, Origen, Destino, Precio) VALUES (null, 'Madrid','Praga','330')");
        bd.execSQL("INSERT INTO Viajes (id, Origen, Destino, Precio) VALUES (null, 'Alicante','Polonia','140')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int versionAnterior, int versionNueva) {
        //Eliminamos la version anterior de la tabla
        bd.execSQL("DROP TABLE IF EXISTS Usuarios");
        bd.execSQL("DROP TABLE IF EXISTS Billetes");
        bd.execSQL(usuarios);

    }

    public void close() {
        mDbHelper.close();
    }
}