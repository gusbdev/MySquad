package com.projects.gus.mysquad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LineUp extends AppCompatActivity {
    private Button btnGol, btnFixo, btnAlaD, btnAlaE, btnPivo;
    private String goleiro,fixo,alaD,alaE,pivo,idJogador;
    private ViewGroup lineUpLayout;
    private ImageView imgBall;

    private int xDelta;
    private int yDelta;

    Intent intent;
    Cursor cursor;
    DBController dbController;

    @Override
    public void onCreate(Bundle savedStateBundle) {
        super.onCreate(savedStateBundle);
        setContentView(R.layout.actitivy_lineup);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        imgBall = findViewById(R.id.imgBall);
        btnGol = findViewById(R.id.btnGoleiro);
        btnFixo = findViewById(R.id.btnFixo);
        btnAlaD = findViewById(R.id.btnAlaD);
        btnAlaE = findViewById(R.id.btnAlaE);
        btnPivo = findViewById(R.id.btnPivo);
        lineUpLayout = findViewById(R.id.lineUpLayout);

        imgBall.setOnTouchListener(onTouchListener());
        btnGol.setOnTouchListener(onTouchListener());
        btnFixo.setOnTouchListener(onTouchListener());
        btnAlaD.setOnTouchListener(onTouchListener());
        btnAlaE.setOnTouchListener(onTouchListener());
        btnPivo.setOnTouchListener(onTouchListener());

        btnGol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LineUp.this,"Goleiro",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lineup, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_share:
                Toast.makeText(LineUp.this,"Por enquanto nada.",Toast.LENGTH_LONG).show();
                return true;
        }
        return onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbController = new DBController(getApplicationContext());
        idJogador = this.getIntent().getStringExtra("idJogador");

        //cursor = dbController.loadJogadorById(Integer.parseInt(idJogador));
        //goleiro = cursor.getString(cursor.getColumnIndexOrThrow(Database.NOME));
        //btnGol.setText(goleiro);
    }

    private View.OnTouchListener onTouchListener () {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }
                lineUpLayout.invalidate();
                return true;
            }
        };
    }
}