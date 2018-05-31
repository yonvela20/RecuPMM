package com.example.yon_pc.recupmmv2;

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

    //declaracion de los widgets
    EditText usuarioCorrecto, contraseñaCorrecta;
    Cursor cursor;
    Button buttonLogin, buttonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioCorrecto = findViewById(R.id.usuarioCorrecto);
        contraseñaCorrecta = findViewById(R.id.contraseñaCorrecta);
        buttonLogin = findViewById(R.id.Entrar);
        buttonRegistro = findViewById(R.id.buttonRegistro);

        //metodo para entrar a la app que llama a ingresar()
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingresar();
            }
        });

        //Con este boton vamos a la actividad Registro para crear nuestro usuario
        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //Con este metodo entramos a la actividad Formulario en el caso de tener un usuario existente
    public void ingresar() {
        DataBaseHelper DbHelper = new DataBaseHelper(MainActivity.this, "DB", null, 1);
        SQLiteDatabase sqLiteDatabase = DbHelper.getWritableDatabase();

        String users = usuarioCorrecto.getText().toString();
        String pass = contraseñaCorrecta.getText().toString();
        cursor = sqLiteDatabase.rawQuery("select usuario,password from Usuarios where usuario = '" + users + "' and password = '" + pass + "'", null);

        if (cursor.moveToFirst() == true) {

            String user = cursor.getString(0);
            String password = cursor.getString(1);

            if (users.equals(user) && pass.equals(password)) {
                Intent ven = new Intent(MainActivity.this, Formulario.class);
                Bundle bundle = new Bundle();
                bundle.putString("usuario", user);
                ven.putExtras(bundle);
                startActivity(ven);

                usuarioCorrecto.setText("");
                contraseñaCorrecta.setText("");

                finish();
            } else {
                String msg = "Contraseña o usuario erroneo";

                Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            }
        }
    }
}

