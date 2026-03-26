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

    private EditText etNombre, etApellido, etPrimerNumero, etSegundoNumero;
    private Button btnSiguiente, btnCerrar;
    private String singleVariable = "";

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    singleVariable = result.getData().getStringExtra("DATA");
                    
                    // Desbloqueamos nombres y apellidos al regresar de ventana 3
                    etNombre.setEnabled(true);
                    etApellido.setEnabled(true);

                    if (singleVariable != null && !singleVariable.isEmpty()) {
                        String[] parts = singleVariable.split(";");
                        if (parts.length >= 4) {
                            etNombre.setText(parts[0]);
                            etApellido.setText(parts[1]);
                            etPrimerNumero.setText(parts[2]);
                            etSegundoNumero.setText(parts[3]);
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        etNombre = findViewById(R.id.etNombre2);
        etApellido = findViewById(R.id.etApellido2);
        etPrimerNumero = findViewById(R.id.etPrimerNumero2);
        etSegundoNumero = findViewById(R.id.etSegundoNumero2);
        btnSiguiente = findViewById(R.id.btnSiguiente2);
        btnCerrar = findViewById(R.id.btnCerrar2);

        singleVariable = getIntent().getStringExtra("DATA");
        if (singleVariable != null && !singleVariable.isEmpty()) {
            String[] parts = singleVariable.split(";");
            if (parts.length >= 4) {
                etNombre.setText(parts[0]);
                etApellido.setText(parts[1]);
                etPrimerNumero.setText(parts[2]);
                etSegundoNumero.setText(parts[3]);
            }
        }

        btnSiguiente.setOnClickListener(v -> {
            // Actualizamos la variable con lo que haya en los campos antes de ir a V3
            String n = etNombre.getText().toString();
            String a = etApellido.getText().toString();
            String p1 = etPrimerNumero.getText().toString();
            String p2 = etSegundoNumero.getText().toString();
            singleVariable = n + ";" + a + ";" + p1 + ";" + p2;

            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
            intent.putExtra("DATA", singleVariable);
            launcher.launch(intent);
        });

        btnCerrar.setOnClickListener(v -> {
            String n = etNombre.getText().toString();
            String a = etApellido.getText().toString();
            
            if (!n.isEmpty() && !a.isEmpty()) {
                String p1 = etPrimerNumero.getText().toString();
                String p2 = etSegundoNumero.getText().toString();
                singleVariable = n + ";" + a + ";" + p1 + ";" + p2;

                Intent resultIntent = new Intent();
                resultIntent.putExtra("DATA", singleVariable);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Nombre y Apellido no pueden estar vacíos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
