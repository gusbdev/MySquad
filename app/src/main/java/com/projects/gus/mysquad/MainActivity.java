package com.projects.gus.mysquad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    CardView cardCadastrar,cardListar,cardLineUp,cardCalendar;
    private Intent intent;
    private String nomeTime, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardCadastrar = findViewById(R.id.cardCadastrar);
        cardListar = findViewById(R.id.cardListar);
        cardLineUp = findViewById(R.id.cardLineUp);
        cardCalendar = findViewById(R.id.cardCalendar);

        cardCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                alert.setTitle("Cadastar time");
                alert.setMessage("Insira o nome do time");

                // Set an EditText view to get user input
                final EditText input = new EditText(MainActivity.this);
                alert.setView(input);

                alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        nomeTime = input.getText().toString();
                        DBController dbController = new DBController(getBaseContext());
                        result = dbController.insertDataTime(nomeTime);
                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });

        cardListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, ListTeams.class);
                startActivity(intent);
            }
        });

        cardLineUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, LineUp.class);
                startActivity(intent);
            }
        });

        cardCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, EventCalendar.class);
                startActivity(intent);
            }
        });
    }


}
