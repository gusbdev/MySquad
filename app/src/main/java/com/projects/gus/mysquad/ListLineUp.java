package com.projects.gus.mysquad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ListLineUp extends Activity {
//    private ListView list;
//    private Cursor cursor;
//    private DBController dbController;
//    private AlertDialog alertDialog;
//    private AlertDialog.Builder builder;
//
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_consult_jogador);
//    }
//
//    public void onResume(){
//        super.onResume();
//
//        dbController = new DBController(getBaseContext());
//        cursor = dbController.loadJogador();
//
//        String[]fieldsNames = new String[]{Database.ID_JOGADOR,Database.NOME};
//        int[] idViews = new int[]{R.id.idJogador,R.id.nomeJogador};
//
//        final SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
//                R.layout.activity_stylish,cursor,fieldsNames,idViews, 0);
//
//        list = findViewById(R.id.listView);
//        list.setAdapter(adaptador);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String idJogador;
//                cursor.moveToPosition(position);
//                idJogador = cursor.getString(cursor.getColumnIndexOrThrow(Database.ID_JOGADOR));
//                Intent intent = new Intent(ListLineUp.this, LineUp.class);
//                intent.putExtra("idJogador", idJogador);
//                Toast.makeText(getApplicationContext(),idJogador,Toast.LENGTH_LONG);
//                startActivity(intent);
//
//                finish();
//            }
//        });
//
////        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
////            @Override
////            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
////                builder = new AlertDialog.Builder(ListPlayers.this);
////                builder.setTitle("Deletar");
////                builder.setMessage("Deseja apagar o registro?");
////                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        dbController.deletaRegistro(id);
////                        Toast.makeText(getApplicationContext(), "Registro excluído com sucesso!", Toast.LENGTH_LONG).show();
////                    }
////                });
////                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        dialog.dismiss();
////                    }
////                });
////                alertDialog = builder.create();
////                alertDialog.show();
////
////                return true;
////            }
////        });
//    }
}
