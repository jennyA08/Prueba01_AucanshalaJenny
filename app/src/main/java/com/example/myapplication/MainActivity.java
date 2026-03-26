package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText jaEtNombre, jaEtApellido, jaEtNum1, jaEtNum2, jaEtProd, jaEtPot, jaEtFact;
    private Button jaBtnPasar, jaBtnCalcular;

    // Unica variable de transporte: "Nombre:::Apellido:::N1:::N2"
    private String jaPaqueteUnico = ""; 

    private final ActivityResultLauncher<Intent> jaLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    jaPaqueteUnico = result.getData().getStringExtra("JA_TRANSFER_DATA");
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
        jaEtN1 = findViewById(R.id.etPrimerNumero);
        jaEtN2 = findViewById(R.id.etSegundoNumero);
        jaEtMult = findViewById(R.id.etMultiplicacion); // Assuming XML IDs match or I should adjust
        jaEtPot = findViewById(R.id.etPotencia);
        jaEtFact = findViewById(R.id.etFactorial);
        
        // Let's re-map based on the activity_main.xml I saw earlier
        jaEtNombre = findViewById(R.id.etNombre);
        jaEtApellido = findViewById(R.id.etApellido);
        jaEtNum1 = findViewById(R.id.etPrimerNumero);
        jaEtNum2 = findViewById(R.id.etSegundoNumero);
        jaEtProd = findViewById(R.id.etMultiplicacion);
        jaEtPot = findViewById(R.id.etPotencia);
        jaEtFact = findViewById(R.id.etFactorial);
        jaBtnPasar = findViewById(R.id.btnSiguiente);
        jaBtnCalcular = findViewById(R.id.btnResultados);

        jaBtnPasar.setOnClickListener(v -> {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("JA_TRANSFER_DATA", jaPaqueteUnico);
            jaLauncher.launch(intent);
        });

        jaBtnCalcular.setOnClickListener(v -> {
            if (jaPaqueteUnico != null && jaPaqueteUnico.contains(":::")) {
                String[] jaPartes = jaPaqueteUnico.split(":::");
                if (jaPartes.length >= 4) {
                    jaEtNombre.setText(jaPartes[0]);
                    jaEtApellido.setText(jaPartes[1]);
                    jaEtNum1.setText(jaPartes[2]);
                    jaEtNum2.setText(jaPartes[3]);

                    int jaV1 = Integer.parseInt(jaPartes[2]);
                    int jaV2 = Integer.parseInt(jaPartes[3]);

                    jaEtProd.setText(String.valueOf(jaSumaProd(jaV1, jaV2)));
                    jaEtPot.setText(String.valueOf(jaSumaPot(jaV1, jaV2)));
                    jaEtFact.setText(String.valueOf(jaSumaFact(jaV1)));
                }
            }
        });
    }

    private int jaSumaProd(int a, int b) {
        int jaRes = 0;
        for (int i = 0; i < b; i++) {
            jaRes += a;
        }
        return jaRes;
    }

    private int jaSumaPot(int b, int e) {
        if (e == 0) return 1;
        int jaRes = b;
        for (int i = 1; i < e; i++) {
            jaRes = jaSumaProd(jaRes, b);
        }
        return jaRes;
    }

    private int jaSumaFact(int n) {
        if (n <= 0) return 1;
        int jaRes = 1;
        for (int i = 1; i <= n; i++) {
            jaRes = jaSumaProd(jaRes, i);
        }
        return jaRes;
    }
}
