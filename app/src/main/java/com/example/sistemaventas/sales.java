package com.example.sistemaventas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class sales extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        EditText idseller = findViewById(R.id.etidsellers);
        EditText fullname = findViewById(R.id.etfullnames);
        EditText datesale = findViewById(R.id.etdatesale);
        EditText salevalue = findViewById(R.id.etsalevalue);
        Button btnregistersale = findViewById(R.id.btnregistersale);
        Button back = findViewById(R.id.btnback);

        // Recibir y mostrar los datos enviados desde el main activity (midseller y mfullname)





    }
}