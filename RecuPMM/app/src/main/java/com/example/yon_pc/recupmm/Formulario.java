package com.example.yon_pc.recupmm;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Formulario extends AppCompatActivity implements FragmentDetalles.OnFragmentInteractionListener {
    public ArrayList<Viajes> viajes = new ArrayList<Viajes>();
    private Viajes[] listado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        final Button buttonCompra = (Button) findViewById(R.id.buttonCompra);

        DataBaseHelper DbHelper = new DataBaseHelper(this, "Usuarios", null, 1);
        final SQLiteDatabase bd = DbHelper.getWritableDatabase();

        String[] campos = new String[]{"Origen", "Destino", "Precio"};
        Cursor c = bd.query("Viajes", campos, null, null, null, null, null);
        listado = new Viajes[c.getCount()];
        int i = 0;

        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String origen = c.getString(0);
                String destino = c.getString(1);
                Double precio = c.getDouble(2);

                listado[i] = new Viajes(origen, destino, precio);

                i++;

            } while (c.moveToNext());
        }

        final AdaptadorViajes adaptador = new AdaptadorViajes(this);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adaptador);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView arg0, View arg1, int position, long id) {
                String mensaje = "Origen: " + listado[position].getOrigen() + ", Destino: " + listado[position].getDestino() + ", Precio: " + listado[position].getPrecio();
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bd.close();

        buttonCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle objetos = new Bundle();

                Viajes viaje = new Viajes(
                        listado[spinner.getSelectedItemPosition()].getOrigen(),
                        listado[spinner.getSelectedItemPosition()].getDestino(),
                        listado[spinner.getSelectedItemPosition()].getPrecio());

                objetos.putSerializable("informacion", viaje);


                CheckBox preferente = (CheckBox) findViewById(R.id.preferente);
                CheckBox souvenir = (CheckBox) findViewById(R.id.souvenir);
                CheckBox menu = (CheckBox) findViewById(R.id.menu);

                RadioButton claseVip = (RadioButton) findViewById(R.id.claseVip);
                RadioButton claseTurista = (RadioButton) findViewById(R.id.claseTurista);

                RadioGroup grupo = (RadioGroup) findViewById(R.id.radiogroup);

                boolean selected1 = false;
                boolean selected2 = false;
                boolean selected3 = false;
                if (preferente.isChecked()) {
                    selected1 = true;
                }
                objetos.putBoolean("boolean1", selected1);
                objetos.putString("preferente", preferente.getText().toString());

                if (souvenir.isChecked()) {
                    selected2 = true;
                }
                objetos.putBoolean("boolean2", selected2);
                objetos.putString("souvenir", souvenir.getText().toString());

                if (menu.isChecked()) {
                    selected3 = true;
                }
                objetos.putBoolean("boolean3", selected3);
                objetos.putString("menu", menu.getText().toString());

                if (grupo.getCheckedRadioButtonId() == R.id.claseVip) {
                    objetos.putString("grupo", claseVip.getText().toString());
                } else {
                    objetos.putString("grupo", claseTurista.getText().toString());
                }

                String user = getIntent().getStringExtra("usuario");
                objetos.putSerializable("usuario", user);

                //Fragment

                //FragmentManager fragmentmanager = getFragmentManager();
                android.support.v4.app.FragmentManager fragmentmanager = getSupportFragmentManager();

                //android.support.v4.app.FragmentTransaction transaction = fragmentmanager.beginTransaction();

                //Creamos un nuevo fragment y lo añadimos
                Fragment fragment = new FragmentDetalles();
                fragment.setArguments(objetos);

                //transaction.add(R.id.activity_formulario, fragment);
                fragmentmanager.beginTransaction().replace(R.id.activity_formulario, fragment).commit();

                //lo confirmamos
                //transaction.commit();
            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.AcercaDe:
                Intent acercaDe = new Intent(Formulario.this, AcercaDe.class);
                startActivity(acercaDe);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class AdaptadorViajes extends ArrayAdapter {
        Activity context;

        AdaptadorViajes(Activity context) {
            super(context, R.layout.viajes, listado);
            this.context = context;
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View vistaDesplegada = getView(position, convertView, parent);
            return vistaDesplegada;
        }

        public View getView(int i, View convertView, ViewGroup parent) {

            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.viajes, null);

            TextView nom = (TextView) item.findViewById(R.id.viajeOrigen);
            nom.setText(listado[i].getOrigen());

            TextView est = (TextView) item.findViewById(R.id.viajeDestino);
            est.setText(listado[i].getDestino());

            TextView pre = (TextView) item.findViewById(R.id.viajePrecio);
            pre.setText(String.valueOf(listado[i].getPrecio()));

            return (item);
        }
    }
}
