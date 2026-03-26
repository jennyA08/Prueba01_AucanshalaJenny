package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText jaEtNombre, jaEtApellido, jaEtNumUno, jaEtNumDos, jaEtMult, jaEtPot, jaEtFact;
    private Button jaBtnFlujo, jaBtnCalcular;

    // Paquete único: "Nombre:::ja:::Apellido:::ja:::Num1:::ja:::Num2"
    private String jaPaqueteDeDatos = ""; 

    private final ActivityResultLauncher<Intent> jaLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            jaResult -> {
                if (jaResult.getResultCode() == RESULT_OK && jaResult.getData() != null) {
                    jaPaqueteDeDatos = jaResult.getData().getStringExtra("JA_CADENA_PROYECTO");
                    jaBtnCalcular.setEnabled(true);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jaEtNombre = findViewById(R.id.etNombre);
        jaEtApellido = findViewById(R.id.etApellido);
        jaEtNumUno = findViewById(R.id.etPrimerNumero);
        jaEtNumDos = findViewById(R.id.etSegundoNumero);
        jaEtMult = findViewById(R.id.etMultiplicacion);
        jaEtPot = findViewById(R.id.etPotencia);
        jaEtFact = findViewById(R.id.etFactorial);
        jaBtnFlujo = findViewById(R.id.btnSiguiente);
        jaBtnCalcular = findViewById(R.id.btnResultados);

        jaBtnFlujo.setOnClickListener(v -> {
            Intent jaIntent = new Intent(this, SecondActivity.class);
            jaIntent.putExtra("JA_CADENA_PROYECTO", jaPaqueteDeDatos);
            jaLauncher.launch(jaIntent);
        });

        jaBtnCalcular.setOnClickListener(v -> {
            if (jaPaqueteDeDatos != null && jaPaqueteDeDatos.contains(":::ja:::")) {
                String[] jaPartes = jaPaqueteDeDatos.split(":::ja:::");
                if (jaPartes.length >= 4) {
                    jaEtNombre.setText(jaPartes[0]);
                    jaEtApellido.setText(jaPartes[1]);
                    jaEtNumUno.setText(jaPartes[2]);
                    jaEtNumDos.setText(jaPartes[3]);

                    int jaV1 = Integer.parseInt(jaPartes[2]);
                    int jaV2 = Integer.parseInt(jaPartes[3]);

                    // Operaciones solo con sumas
                    jaEtMult.setText(String.valueOf(jaCalculoM(jaV1, jaV2)));
                    jaEtPot.setText(String.valueOf(jaCalculoP(jaV1, jaV2)));
                    jaEtFact.setText(String.valueOf(jaCalculoF(jaV1)));
                }
            }
        });
    }

    private int jaCalculoM(int jaX, int jaY) {
        int jaResultado = 0;
        for (int i = 0; i < jaY; i++) {
            jaResultado += jaX;
        }
        return jaResultado;
    }

    private int jaCalculoP(int jaBase, int jaExp) {
        if (jaExp == 0) return 1;
        int jaResultado = jaBase;
        for (int i = 1; i < jaExp; i++) {
            jaResultado = jaCalculoM(jaResultado, jaBase);
        }
        return jaResultado;
    }

    private int jaCalculoF(int jaN) {
        if (jaN <= 0) return 1;
        int jaResultado = 1;
        for (int i = 1; i <= jaN; i++) {
            jaResultado = jaCalculoM(jaResultado, i);
        }
        return jaResultado;
    }
}
