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

    private EditText jaEditNom, jaEditApe, jaEditN1, jaEditN2;
    private Button jaBtnPasar, jaBtnSalir;
    
    // Almacenamos todo en esta cadena: "Nombre*Apellido*Num1*Num2"
    private String jaCadenaDatos = ""; 

    private final ActivityResultLauncher<Intent> jaLauncherV3 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            jaResult -> {
                if (jaResult.getResultCode() == RESULT_OK && jaResult.getData() != null) {
                    jaCadenaDatos = jaResult.getData().getStringExtra("DATOS_JA");
                    
                    // Al volver de V3, habilitamos edición de Nombre y Apellido
                    jaEditNom.setEnabled(true);
                    jaEditApe.setEnabled(true);

                    jaRefrescarCampos();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        jaEditNom = findViewById(R.id.etNombre2);
        jaEditApe = findViewById(R.id.etApellido2);
        jaEditN1 = findViewById(R.id.etPrimerNumero2);
        jaEditN2 = findViewById(R.id.etSegundoNumero2);
        jaBtnPasar = findViewById(R.id.btnSiguiente2);
        jaBtnSalir = findViewById(R.id.btnCerrar2);

        jaCadenaDatos = getIntent().getStringExtra("DATOS_JA");
        jaRefrescarCampos();

        jaBtnPasar.setOnClickListener(v -> {
            // Guardamos lo que haya (aunque esté bloqueado al inicio)
            jaCapturarInfo();
            Intent jaIntent = new Intent(this, ThirdActivity.class);
            jaIntent.putExtra("DATOS_JA", jaCadenaDatos);
            jaLauncherV3.launch(jaIntent);
        });

        jaBtnSalir.setOnClickListener(v -> {
            String jaN = jaEditNom.getText().toString().trim();
            String jaA = jaEditApe.getText().toString().trim();

            if (jaN.isEmpty() || jaA.isEmpty()) {
                Toast.makeText(this, "Debe ingresar Nombre y Apellido para cerrar", Toast.LENGTH_SHORT).show();
            } else {
                jaCapturarInfo();
                Intent jaResIntent = new Intent();
                jaResIntent.putExtra("DATOS_JA", jaCadenaDatos);
                setResult(RESULT_OK, jaResIntent);
                finish();
            }
        });
    }

    private void jaCapturarInfo() {
        String jaN = jaEditNom.getText().toString();
        String jaA = jaEditApe.getText().toString();
        String ja1 = jaEditN1.getText().toString();
        String ja2 = jaEditN2.getText().toString();
        jaCadenaDatos = jaN + "*" + jaA + "*" + ja1 + "*" + ja2;
    }

    private void jaRefrescarCampos() {
        if (jaCadenaDatos != null && jaCadenaDatos.contains("*")) {
            String[] jaP = jaCadenaDatos.split("\\*");
            if (jaP.length >= 1) jaEditNom.setText(jaP[0]);
            if (jaP.length >= 2) jaEditApe.setText(jaP[1]);
            if (jaP.length >= 3) jaEditN1.setText(jaP[2]);
            if (jaP.length >= 4) jaEditN2.setText(jaP[3]);
        }
    }
}
