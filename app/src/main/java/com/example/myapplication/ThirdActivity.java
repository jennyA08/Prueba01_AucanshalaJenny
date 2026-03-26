package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etPrimerNumero, etSegundoNumero;
    private Button btnCerrar;
    private String singleVariable = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        etNombre = findViewById(R.id.etNombre3);
        etApellido = findViewById(R.id.etApellido3);
        etPrimerNumero = findViewById(R.id.etPrimerNumero3);
        etSegundoNumero = findViewById(R.id.etSegundoNumero3);
        btnCerrar = findViewById(R.id.btnCerrar3);

        singleVariable = getIntent().getStringExtra("DATA");
        if (singleVariable != null && !singleVariable.isEmpty()) {
            String[] parts = singleVariable.split(";");
            if (parts.length >= 2) {
                etNombre.setText(parts[0]);
                etApellido.setText(parts[1]);
            }
        }


}
