package com.example.yon_pc.recupmm;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DataBaseHelper DbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button entrar = (Button) findViewById(R.id.Entrar);
        Button formulario = (Button) findViewById(R.id.buttonRegistro);

        final EditText usuarioCorrecto = (EditText) findViewById(R.id.usuarioCorrecto);
        final EditText contraseñaCorrecta = (EditText) findViewById(R.id.contraseñaCorrecta);

        DbHelper = new DataBaseHelper(this, "Usuarios", null, 1);
        entrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                SQLiteDatabase bd = DbHelper.getWritableDatabase();

                String usuario = usuarioCorrecto.getText().toString();
                String contraseña = contraseñaCorrecta.getText().toString();

                Cursor fila = bd.rawQuery("SELECT usuario,password FROM Usuarios WHERE usuario='" + usuario + "' and password='" + contraseña + "'", null);

                if (fila.moveToFirst()) {
                    String user = fila.getString(0);
                    String pass = fila.getString(1);

                    if (usuario.equals(user) && contraseña.equals(pass)) {

                        Intent acceso = new Intent(MainActivity.this, Formulario.class);
                        acceso.putExtra("usuario", user);
                        startActivity(acceso);
                        Toast.makeText(getApplicationContext(), "Completada la conexión", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Usuario incorrecto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        formulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(MainActivity.this, Registro.class);
                startActivity(registro);
            }
        });
    }
}
