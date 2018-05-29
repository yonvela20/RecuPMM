package com.example.yon_pc.recupmm;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Factura extends ListActivity {

    private Viajes viaje;
    TextView nom;
    String[] columnasBillete = new String[]{"usuarios", "Origen", "Destino", "Precio", "Clase", "Extras"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        fillData();
    }

    private void fillData(){
        DataBaseHelper DbHelper = new DataBaseHelper(this, "Billetes", null, 1);
        final SQLiteDatabase bd = DbHelper.getWritableDatabase();

        Cursor c = bd.query("Billetes", columnasBillete, null, null, null, null, null);
        infoBillete item  = null;

        ArrayList<infoBillete> resultList = new ArrayList<>();

        while (c.moveToNext()) {
            String getUsarios = c.getString(c.getColumnIndex("usuarios"));
            String getOrigen = c.getString(c.getColumnIndex("Origen"));
            String getDestino = c.getString(c.getColumnIndex("Destino"));
            Float getPrecio = c.getFloat(c.getColumnIndex("Precio"));
            String getClase = c.getString(c.getColumnIndex("Clase"));
            String getExtras = c.getString(c.getColumnIndex("Extras"));

            item = new infoBillete();

            item.usuarios = getUsarios;
            item.Origen = getOrigen;
            item.Destino = getDestino;
            item.Precio = getPrecio;
            item.Clase = getClase;
            item.Extras = getExtras;
            resultList.add(item);
        }
        c.close();
        bd.close();

        TaskAdapter items = new TaskAdapter(this, R.layout.list_adapter, resultList, getLayoutInflater());
        setListAdapter(items);
    }

    private class infoBillete{
        String usuarios;
        String Origen;
        String Destino;
        Float Precio;
        String Clase;
        String Extras;
    }

    private class TaskAdapter extends ArrayAdapter<infoBillete> {
        private LayoutInflater mInflater;
        private List<infoBillete> mObjects;

        private TaskAdapter(Context context, int resource, List<infoBillete> objects, LayoutInflater mInflater) {
            super(context, resource, objects);
            this.mInflater = mInflater;
            this.mObjects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            infoBillete listEntry = mObjects.get(position);

            View row = mInflater.inflate(R.layout.list_adapter, null);

            TextView usuarios = (TextView) row.findViewById(R.id.rowUsuario);
            TextView origen = (TextView) row.findViewById(R.id.rowOrigen);
            TextView destino = (TextView) row.findViewById(R.id.rowDestino);
            TextView precio = (TextView) row.findViewById(R.id.rowPrecio);
            TextView clase = (TextView) row.findViewById(R.id.rowClase);
            TextView extras = (TextView) row.findViewById(R.id.rowExtras);

            usuarios.setText(listEntry.usuarios);
            origen.setText(listEntry.Origen);
            destino.setText(listEntry.Destino);
            precio.setText(Float.toString(listEntry.Precio));
            clase.setText(listEntry.Clase);
            extras.setText(listEntry.Extras);

            return row;
        }
    }
}
