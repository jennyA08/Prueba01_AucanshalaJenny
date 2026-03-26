package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private EditText jaEtNom, jaEtApe, jaEtNum1, jaEtNum2;
    private Button jaBtnPasarAV3, jaBtnFinalizar;
    
    // Variable única: "nom|||ape|||n1|||n2"
    private String jaPaqueteGlobal = ""; 

    private final ActivityResultLauncher<Intent> jaLauncherV3 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            jaResultado -> {
                if (jaResultado.getResultCode() == RESULT_OK && jaResultado.getData() != null) {
                    jaPaqueteGlobal = jaResultado.getData().getStringExtra("JA_CADENA_PROYECTO");
                    
                    // Al retornar de V3, se habilitan los campos de nombre y apellido
                    jaEtNom.setEnabled(true);
                    jaEtApe.setEnabled(true);

                    jaPoblarCampos();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        jaEtNom = findViewById(R.id.etNombre2);
        jaEtApe = findViewById(R.id.etApellido2);
        jaEtNum1 = findViewById(R.id.etPrimerNumero2);
        jaEtNum2 = findViewById(R.id.etSegundoNumero2);
        jaBtnPasarAV3 = findViewById(R.id.btnSiguiente2);
        jaBtnFinalizar = findViewById(R.id.btnCerrar2);

        // Bloqueados por defecto (o según XML)
        jaEtNom.setEnabled(false);
        jaEtApe.setEnabled(false);
        jaEtNum1.setEnabled(false);
        jaEtNum2.setEnabled(false);

        jaPaqueteGlobal = getIntent().getStringExtra("JA_CADENA_PROYECTO");
        jaPoblarCampos();

        jaBtnPasarAV3.setOnClickListener(v -> {
            jaSincronizarCadena();
            Intent jaIntent = new Intent(this, ThirdActivity.class);
            jaIntent.putExtra("JA_CADENA_PROYECTO", jaPaqueteGlobal);
            jaLauncherV3.launch(jaIntent);
        });

        jaBtnFinalizar.setOnClickListener(v -> {
            String jaN = jaEtNom.getText().toString().trim();
            String jaA = jaEtApe.getText().toString().trim();

            if (jaN.isEmpty() || jaA.isEmpty()) {
                Toast.makeText(this, "Nombre y Apellido obligatorios para cerrar", Toast.LENGTH_SHORT).show();
            } else {
                jaSincronizarCadena();
                Intent jaIntentCierre = new Intent();
                jaIntentCierre.putExtra("JA_CADENA_PROYECTO", jaPaqueteGlobal);
                setResult(RESULT_OK, jaIntentCierre);
                finish();
            }
        });
    }

    private void jaSincronizarCadena() {
        String jaN = jaEtNom.getText().toString();
        String jaA = jaEtApe.getText().toString();
        String ja1 = jaEtNum1.getText().toString();
        String ja2 = jaEtNum2.getText().toString();
        jaPaqueteGlobal = jaN + "|||" + jaA + "|||" + ja1 + "|||" + ja2;
    }

    private void jaPoblarCampos() {
        if (jaPaqueteGlobal != null && jaPaqueteGlobal.contains("|||")) {
            String[] jaDatos = jaPaqueteGlobal.split("\\|\\|\\|");
            if (jaDatos.length >= 1) jaEtNom.setText(jaDatos[0]);
            if (jaDatos.length >= 2) jaEtApe.setText(jaDatos[1]);
            if (jaDatos.length >= 3) jaEtNum1.setText(jaDatos[2]);
            if (jaDatos.length >= 4) jaEtNum2.setText(jaDatos[3]);
        }
    }
}
