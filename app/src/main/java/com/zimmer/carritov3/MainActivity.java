package com.zimmer.carritov3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText editTextUsuario = findViewById(R.id.editTextText2);
        EditText editTextContraseña = findViewById(R.id.editTextPassword3);
        Button buttonIniciarSesion = findViewById(R.id.button2);
        Button buttonVolver = findViewById(R.id.buttonBack);

        buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = editTextUsuario.getText().toString();
                String contraseña = editTextContraseña.getText().toString();

                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("MainActivity", "Intentando iniciar sesión con usuario: " + usuario);

                // Validación de las credenciales
                if (usuario.equals("tiopepito") && contraseña.equals("1234")) {
                    Toast.makeText(MainActivity.this, "Bienvenido, " + usuario, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, AdminMenuActivity.class));
                   // finish();
                } else {
                    Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Funcionalidad del botón "Volver"
        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
