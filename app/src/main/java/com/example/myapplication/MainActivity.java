package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etPrimerNumero, etSegundoNumero, etMultiplicacion, etPotencia, etFactorial;
    private Button btnSiguiente, btnResultados;

    private String dataReceived = ""; // Formato: "nombre;apellido;n1;n2"

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    dataReceived = result.getData().getStringExtra("DATA");
                    btnResultados.setEnabled(true);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etPrimerNumero = findViewById(R.id.etPrimerNumero);
        etSegundoNumero = findViewById(R.id.etSegundoNumero);
        etMultiplicacion = findViewById(R.id.etMultiplicacion);
        etPotencia = findViewById(R.id.etPotencia);
        etFactorial = findViewById(R.id.etFactorial);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnResultados = findViewById(R.id.btnResultados);

        btnSiguiente.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            // Pasamos la variable única (incluso si está vacía inicialmente)
            intent.putExtra("DATA", dataReceived);
            launcher.launch(intent);
        });

        btnResultados.setOnClickListener(v -> {
            if (dataReceived != null && !dataReceived.isEmpty()) {
                String[] parts = dataReceived.split(";");
                if (parts.length >= 4) {
                    String nombre = parts[0];
                    String apellido = parts[1];
                    int n1 = Integer.parseInt(parts[2]);
                    int n2 = Integer.parseInt(parts[3]);

                    etNombre.setText(nombre);
                    etApellido.setText(apellido);
                    etPrimerNumero.setText(String.valueOf(n1));
                    etSegundoNumero.setText(String.valueOf(n2));

                    etMultiplicacion.setText(String.valueOf(multiplicar(n1, n2)));
                    etPotencia.setText(String.valueOf(potencia(n1, n2)));
                    etFactorial.setText(String.valueOf(factorial(n1)));
                }
            }
        });
    }

    private int multiplicar(int a, int b) {
        int res = 0;
        for (int i = 0; i < b; i++) {
            res += a;
        }
        return res;
    }

    private int potencia(int a, int b) {
        if (b == 0) return 1;
        int res = a;
        for (int i = 1; i < b; i++) {
            res = multiplicar(res, a);
        }
        return res;
    }

    private int factorial(int a) {
        if (a <= 0) return 1;
        int res = 1;
        for (int i = 1; i <= a; i++) {
            res = multiplicar(res, i);
        }
        return res;
    }
}
