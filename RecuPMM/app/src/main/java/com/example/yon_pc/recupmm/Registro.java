package com.example.yon_pc.recupmm;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {

    private DataBaseHelper DbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button registrarse = (Button) findViewById(R.id.registro);
        Button volverLogin = (Button) findViewById(R.id.volverLogin);
        final EditText registroUsuario = (EditText) findViewById(R.id.registroUsuario);
        final EditText registroContraseña = (EditText) findViewById(R.id.registroContraseña);

        DbHelper = new DataBaseHelper(this, "Usuarios", null, 1);

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //se guarda los datos del registro en la base de datos de USUARIOS  Y CONTRASEÑAS

                //Obtenemos referencia a la base de datos para poder modificarla.
                SQLiteDatabase bd = DbHelper.getWritableDatabase();
                bd.execSQL("INSERT INTO Usuarios (usuario, password) VALUES ('" + registroUsuario.getText().toString() + "','" + registroContraseña.getText().toString() + "')");
                bd.close();
                Intent main = new Intent(Registro.this, MainActivity.class);
                startActivity(main);
                Toast.makeText(getApplicationContext(), "Usuario Registrado", Toast.LENGTH_SHORT).show();
            }
        });

        volverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volver = new Intent(Registro.this, MainActivity.class);
                startActivity(volver);
            }
        });
    }
}
