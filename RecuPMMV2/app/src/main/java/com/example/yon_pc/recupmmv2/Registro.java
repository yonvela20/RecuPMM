package com.example.yon_pc.recupmmv2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registro extends AppCompatActivity {

    EditText registroContraseña, registroUsuario;
    Button buttonVolver,buttonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        registroContraseña = findViewById(R.id.registroContraseña);
        registroUsuario = findViewById(R.id.registroUsuario);
        buttonVolver = findViewById(R.id.volverLogin);
        buttonRegistro = findViewById(R.id.registro);

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(Registro.this, "DB", null, 1);
                SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();


                String users = registroUsuario.getText().toString();
                String pass = registroContraseña.getText().toString();

                sqLiteDatabase.execSQL("INSERT INTO Usuarios (usuario,password) VALUES('"+users+"','"+pass+"')");
                sqLiteDatabase.close();

                Intent intent = new Intent(Registro.this,MainActivity.class);
                startActivity(intent);
            }
        });

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volver(view);
            }
        });
    }

    public  void  volver(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
