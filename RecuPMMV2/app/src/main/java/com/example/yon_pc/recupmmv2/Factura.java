package com.example.yon_pc.recupmmv2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Factura extends ListActivity {
    //Actividad donde vemos los registros

    //acciones
    public static final int NEW_ITEM = 1;
    public static final int EDIT_ITEM = 2;
    public static final int SHOW_ITEM = 3;

    String[] columnasBilletes = new String[]{"id", "id_billete", "extras", "cantidad", "clase", "origen", "destino", "precio", "cliente", "recibido"};

    TextView textView;
    int id;

    //elemento seleccionado
    private int mLastRowSelected = 0;
    public static DataBaseHelper DbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);

        try {
            fillData();
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage(R.string.errorMessage);
        }
        registerForContextMenu(getListView());
    }

    private void showMessage(int message) {
        Context context = getApplicationContext();
        CharSequence text = getResources().getString(message);
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void fillData() {
        // se abre la base de datos y se obtienen los elementos
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);
        SQLiteDatabase DbHelper = dataBaseHelper.getWritableDatabase();

        Cursor c = DbHelper.query("Billetes", columnasBilletes,null,null,null,null,null);
        infoBilletes item = null;

        ArrayList<infoBilletes> resultList = new ArrayList<>();

        while (c.moveToNext()) {

            int getId = c.getInt(c.getColumnIndex("id"));
            int getIdBillete = c.getInt(c.getColumnIndex("id_billete"));
            String getExtras = c.getString(c.getColumnIndex("extras"));
            int getCantidad = c.getInt(c.getColumnIndex("cantidad"));
            String getClase = c.getString(c.getColumnIndex("clase"));
            String getOrigen = c.getString(c.getColumnIndex("origen"));
            String getDestino = c.getString(c.getColumnIndex("destino"));
            float getPrecio = c.getFloat(c.getColumnIndex("precio"));
            String getCliente = c.getString(c.getColumnIndex("cliente"));
            String getRecibido = c.getString(c.getColumnIndex("recibido"));

            item = new infoBilletes();

            item.id = getId;
            item.id_billete = getIdBillete;
            item.extras = getExtras;
            item.cantidad = getCantidad;
            item.clase = getClase;
            item.origen = getOrigen;
            item.destino = getDestino;
            item.precio = getPrecio;
            item.cliente = getCliente;
            item.recibido = getRecibido;
            resultList.add(item);
        }
        //cerramos la base de datos
        c.close();
        DbHelper.close();
        //se genera el adaptador
        TaskAdapter items = new TaskAdapter(this, R.layout.list_adapter, resultList, getLayoutInflater());
        //asignar adaptador a la lista
        setListAdapter(items);
    }

    //Menu contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context, menu);
    }

    //Opciones del menu contextual
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapter = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        mLastRowSelected = adapter.position;

        switch (item.getItemId()) {
            case R.id.delete_item:

                new AlertDialog.Builder(this).setTitle(
                        this.getString(R.string.alertDelete)).setMessage(
                        R.string.alertDeleteEntry).setPositiveButton(
                        android.R.string.ok, new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dlg, int i) {
                                deleteEntry();
                            }
                        }).setNegativeButton(android.R.string.cancel, null).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //Metodo para eliminar entradas
    protected void deleteEntry() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this, "DB", null, 1);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();

        String[] sqlString = new String[]{Integer.toString(((infoBilletes)getListAdapter().getItem(mLastRowSelected)).id_billete)};
        sqLiteDatabase.delete("Billetes","id = ?",sqlString);
        fillData();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_ITEM || requestCode == NEW_ITEM) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    fillData();
                } catch (SQLException e) {
                    e.printStackTrace();
                    showMessage(R.string.errorMessage);
                }
            }
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

    }

    //TaskAdapter clasico
    private class TaskAdapter extends ArrayAdapter<infoBilletes> {
        private LayoutInflater mInflater;
        private List<infoBilletes> mObjects;

        private TaskAdapter(Context context, int resource, List<infoBilletes> objects, LayoutInflater mInflater) {
            super(context, resource, objects);
            this.mInflater = mInflater;
            this.mObjects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            infoBilletes listEntry = mObjects.get(position);

            View row = mInflater.inflate(R.layout.list_adapter, null);

            TextView extras = row.findViewById(R.id.rowExtras);
            TextView cantidad =  row.findViewById(R.id.rowCantidad);
            TextView clase =  row.findViewById(R.id.rowClase);
            TextView origen =  row.findViewById(R.id.rowOrigen);
            TextView destino =  row.findViewById(R.id.rowDestino);
            TextView precio =  row.findViewById(R.id.rowPrecio);
            TextView cliente =  row.findViewById(R.id.rowCliente);
            TextView estado = row.findViewById(R.id.rowEstado);

            extras.setText(listEntry.extras);
            cantidad.setText(Integer.toString(listEntry.cantidad));
            clase.setText(listEntry.clase);
            origen.setText(listEntry.origen);
            destino.setText(listEntry.destino);
            precio.setText(Float.toString(listEntry.precio));
            cliente.setText(listEntry.cliente);
            estado.setText(listEntry.recibido);

            return row;
        }
    }

    //clase interna con las variables del billete
    protected class infoBilletes{
        int id;
        int id_billete;
        String extras;
        int cantidad;
        String clase;
        String origen;
        String destino;
        float precio;
        String cliente;
        String recibido;
    }
}
