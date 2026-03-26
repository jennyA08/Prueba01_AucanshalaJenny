package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    private EditText jaNombre, jaApellido, jaPrimerNumero, jaSegundoNumero;
    private Button jaCerrar;
    private String jaUnicaVariable = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        jaNombre = findViewById(R.id.etNombre3);
        jaApellido = findViewById(R.id.etApellido3);
        jaPrimerNumero = findViewById(R.id.etPrimerNumero3);
        jaSegundoNumero = findViewById(R.id.etSegundoNumero3);
        jaCerrar = findViewById(R.id.btnCerrar3);

        // Se recibe la variable única
        jaUnicaVariable = getIntent().getStringExtra("DATOS_JA");
        if (jaUnicaVariable != null && jaUnicaVariable.contains("*")) {
            String[] jaDatos = jaUnicaVariable.split("\\*");
            if (jaDatos.length >= 2) {
                jaNombre.setText(jaDatos[0]);
                jaApellido.setText(jaDatos[1]);
            }
        }

        jaCerrar.setOnClickListener(v -> {
            String jaStrN1 = jaPrimerNumero.getText().toString().trim();
            String jaStrN2 = jaSegundoNumero.getText().toString().trim();

            if (!jaStrN1.isEmpty() && !jaStrN2.isEmpty()) {
                try {
                    int jaN1 = Integer.parseInt(jaStrN1);
                    int jaN2 = Integer.parseInt(jaStrN2);

                    // Condición: números no 0 o negativos
                    if (jaN1 > 0 && jaN2 > 0) {
                        String jaNom = jaNombre.getText().toString();
                        String jaApe = jaApellido.getText().toString();
                        
                        // Empaquetamos todo en la variable única con un separador diferente
                        jaUnicaVariable = jaNom + "*" + jaApe + "*" + jaN1 + "*" + jaN2;

                        Intent jaIntentResult = new Intent();
                        jaIntentResult.putExtra("DATOS_JA", jaUnicaVariable);
                        setResult(RESULT_OK, jaIntentResult);
                        finish();
                    } else {
                        Toast.makeText(this, "Ingrese números mayores a 0", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Ingrese números válidos", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Debe completar los campos de números", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
