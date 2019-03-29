package com.projects.gus.mysquad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Temporario extends Activity {

    private Button btnCad, btnCon, btnEsc;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitivy_temporary);
        btnCad = findViewById(R.id.btnCad);
        btnCon = findViewById(R.id.btnCon);
        btnEsc = findViewById(R.id.btnEsc);

        btnCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Temporario.this, InsertPlayer.class);
                startActivity(intent);
            }
        });

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Temporario.this, ListPlayers.class);
                startActivity(intent);
            }
        });

        btnEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Temporario.this, LineUp.class);
                startActivity(intent);
            }
        });
    }
}
