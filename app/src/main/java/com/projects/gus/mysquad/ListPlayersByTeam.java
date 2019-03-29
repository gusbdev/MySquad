package com.projects.gus.mysquad;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListPlayersByTeam extends Activity {
    private ListView list;
    private Cursor cursor;
    private DBController dbController;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    String idTime;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_jogador);
    }

    public void onResume(){
        super.onResume();
        idTime = this.getIntent().getStringExtra("idTime");
        dbController = new DBController(getBaseContext());
        cursor = dbController.loadJogadorByIdTime(Integer.parseInt(idTime));

        String[]fieldsNames = new String[]{Database.ID,Database.NOME};
        int[] idViews = new int[]{R.id.idJogador,R.id.nomeJogador};

        final SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                R.layout.activity_stylish,cursor,fieldsNames,idViews, 0);

        list = findViewById(R.id.listView);
        list.setAdapter(adaptador);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idJogador;
                cursor.moveToPosition(position);
                idJogador = cursor.getString(cursor.getColumnIndexOrThrow(Database.ID));
                Intent intent = new Intent(ListPlayersByTeam.this, UpdatePlayer.class);
                intent.putExtra("idJogador", idJogador);
                startActivity(intent);
                Toast.makeText(ListPlayersByTeam.this,idJogador,Toast.LENGTH_LONG);

                finish();
            }
        });

//        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//           @Override
//           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
//                builder = new AlertDialog.Builder(ListPlayersByTeam.this);
//                builder.setTitle("Deletar");
//                builder.setMessage("Deseja apagar o registro?");
//                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dbController.deletaRegistro(id);
//                        Toast.makeText(getApplicationContext(), "Registro excluído com sucesso!", Toast.LENGTH_LONG).show();
//                    }
//                });
//                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                alertDialog = builder.create();
//                alertDialog.show();
//
//                return true;
//            }
//        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListPlayersByTeam.this, InsertPlayer.class);
                intent.putExtra("idTime", idTime);
                Toast.makeText(getApplicationContext(),idTime,Toast.LENGTH_LONG);
                startActivity(intent);
            }
        });

    }
}
