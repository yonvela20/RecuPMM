package com.example.yon_pc.recupmmv2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Formulario extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase ;
    private Spinner spinnerViaje;
    private Viajes[] viajes;
    String[] columnas = new String[] {"id","Origen","Destino","Precio"};
    int contador = 0;
    //ImageView img ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        final Button buttonCompra = findViewById(R.id.buttonCompra);
        final RadioButton rbTurista = findViewById(R.id.claseTurista);
        final RadioButton rbVip = findViewById(R.id.claseVip);
        final CheckBox chPreferente = findViewById(R.id.preferente);
        final CheckBox chSouvenir = findViewById(R.id.souvenir);
        final CheckBox chMenu = findViewById(R.id.menu);
        final EditText tvCantidad = findViewById(R.id.cantidad);
        spinnerViaje = findViewById(R.id.spinner);

        DataBaseHelper DbHelper = new DataBaseHelper(this,"DB",null,1);
        sqLiteDatabase = DbHelper.getWritableDatabase();

        Cursor c = sqLiteDatabase.query("Viajes", columnas,null,null,null,null,null);

        viajes = new Viajes[c.getCount()];

        int i = 0;
        int a = c.getCount();

        System.out.println(a);

        if (c.moveToFirst()){
            do {
                int id = c.getInt(0);
                String origen = c.getString(1);
                String destino = c.getString(2);
                float precio = c.getFloat(3);

                viajes[i] = new Viajes(origen, destino, precio, id);

                i++;
            }while(c.moveToNext());
        }
        ViajesArrayAdapter viajesArrayAdapter = new ViajesArrayAdapter(this,viajes);
        spinnerViaje.setAdapter(viajesArrayAdapter);

        spinnerViaje.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView arg0, View arg1, int position, long id) {
                String result ="Viaje elegido: \nOrigen"+viajes[position].getOrigen()+
                        "Destino"+viajes[position].getDestino()+
                        "\nPrecio: "+ Float.toString(viajes[position].getPrecio());
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Esto será el fragment
        /*
        buttonCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle objetos = new Bundle();

                Viajes viaje = new Viajes(
                        viajes[spinnerViaje.getSelectedItemPosition()].getOrigen(),
                        viajes[spinnerViaje.getSelectedItemPosition()].getDestino(),
                        viajes[spinnerViaje.getSelectedItemPosition()].getPrecio(),
                        viajes[spinnerViaje.getSelectedItemPosition()].getId());

                objetos.putSerializable("informacion", viajes);


                CheckBox preferente = findViewById(R.id.preferente);
                CheckBox souvenir = findViewById(R.id.souvenir);
                CheckBox menu = findViewById(R.id.menu);

                RadioButton claseVip = findViewById(R.id.claseVip);
                RadioButton claseTurista = findViewById(R.id.claseTurista);

                RadioGroup grupo = findViewById(R.id.radiogroup);

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
                android.support.v4.app.FragmentManager fragmentmanager = getSupportFragmentManager();

                //Creamos un nuevo fragment y lo añadimos
                Fragment fragment = new FragmentDetalles();
                fragment.setArguments(objetos);

                fragmentmanager.beginTransaction().replace(R.id.activity_formulario, fragment).commit();
            }
        });
        */

        buttonCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String cantidad = tvCantidad.getText().toString();
                float canti = Float.parseFloat(cantidad);
                int pos = spinnerViaje.getSelectedItemPosition();
                int cantida = (int)canti;

                int cont =0;

                float precio = viajes[pos].getPrecio()*canti;

                float a= añadido(precio);
                float b = clase(a);
                float total = b;

                int resultado = (int) total;
                String extras = extras();
                String clase = clase();
                String origen  = viajes[pos].getOrigen();
                String destino = viajes[pos].getDestino();
                int id = viajes[pos].getId();

                Bundle mibundle = getIntent().getExtras();

                String cliente = mibundle.getString("cliente");

                String recibido = "A recoger en el aeropuerto";

                sqLiteDatabase.execSQL("INSERT INTO Billetes (id_billete,extras,cantidad,clase,origen,destino,precio,cliente,recibido) VALUES ( '"+id+"',' " +extras+"',' "+ cantida +  "','" +clase +
                        "',' " +origen+"','"+destino+"','"+ resultado+"','" +  cliente + "','"+recibido+"') ");
                extra = " ";
                Intent miIntent = new Intent(Formulario.this, Factura.class);


                startActivity(miIntent);

            }

            public float añadido(float cont) {
                if (chSouvenir.isChecked() && contador % 2 != 0)
                    cont++;
                if (chMenu.isChecked() && contador % 2 != 0)
                    cont++;
                if (chPreferente.isChecked() && contador % 2 != 0)
                    cont++;
                else
                    cont++;

                return cont;
            }

            public float clase(float precio) {
                float total = 0;
                if (rbVip.isChecked() && contador % 2 != 0){
                    total = (float) (precio + precio * 0.1);
                    return total;}
                else
                    contador++;

                return precio;

            }

            public float cantidad(float numero, float precio) {
                float total = numero * precio;
                return total;
            }

            public String clase() {
                String x = "";
                if (rbVip.isChecked() && contador % 2 != 0) {
                    x = "Clase VIP";
                    contador++;
                } else if (rbTurista.isChecked() && contador %2 == 0) {
                    x = "clase Turista";
                    contador++;
                }else
                    contador++;
                return x;

            }
            String extra = " ";

            public String extras() {
                if (chMenu.isChecked() ) {
                    extra = extra + "Menu en el avion ";

                }
                if (chPreferente.isChecked()) {
                    extra = extra + "Entrada preferente ";

                }
                if (chSouvenir.isChecked() ) {
                    extra = extra + "souvenir de la agencia ";

                }else {

                }

                return extra;
            }
        });
    }
}
