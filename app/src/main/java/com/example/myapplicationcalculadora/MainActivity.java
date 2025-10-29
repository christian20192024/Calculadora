package com.example.myapplicationcalculadora;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spiner1;
    private Spinner spiner2;
    private EditText txtPanel;
    private TextView txtMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spiner1 = findViewById(R.id.spiner1);
        spiner2 = findViewById(R.id.spiner2);
        txtPanel = findViewById(R.id.txtPanel);
        txtMostrar = findViewById(R.id.txtMostrar);

        List<String> sistemasNumericos = new ArrayList<>();
        sistemasNumericos.add("Seleccionar");
        sistemasNumericos.add("Decimal");
        sistemasNumericos.add("Hexadecimal");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                sistemasNumericos
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spiner1.setAdapter(adapter);
        spiner2.setAdapter(adapter);
    }
    public void realizarConversion_onClick(View v) {
        String baseDesde = spiner1.getSelectedItem().toString();
        String baseHacia = spiner2.getSelectedItem().toString();
        String valorEntrada = txtPanel.getText().toString().trim();
        String resultado;

        // Validamos si alguno de los Spinners tiene "Seleccionar"
        if (baseDesde.equals("Seleccionar") || baseHacia.equals("Seleccionar")) {
            txtMostrar.setText("Error: Seleccione la conversión.");
            return;
        }

        if (valorEntrada.isEmpty()) {
            txtMostrar.setText("Error: Ingrese un valor.");
            return;
        }

        // Lógica de Conversión
        if (baseDesde.equals("Decimal") && baseHacia.equals("Hexadecimal")) {
            try {
                long decimalValue = Long.parseLong(valorEntrada);
                resultado = Long.toHexString(decimalValue).toUpperCase();
            } catch (NumberFormatException e) {
                resultado = "Error: Decimal Inválido.";
            }

        } else if (baseDesde.equals("Hexadecimal") && baseHacia.equals("Decimal")) {
            try {
                long decimalValue = Long.parseLong(valorEntrada, 16);
                resultado = String.valueOf(decimalValue);
            } catch (NumberFormatException e) {
                resultado = "Error: Hexadecimal Inválido.";
            }

        } else if (baseDesde.equals(baseHacia)) {
            resultado = valorEntrada;

        } else {
            resultado = "Error de lógica interna.";
        }

        txtMostrar.setText(resultado);
    }

    public void cmdReiniciar_onClick(View v) {
        txtPanel.setText("");
        txtMostrar.setText("");
        spiner1.setSelection(0);
        spiner2.setSelection(0);
    }
}