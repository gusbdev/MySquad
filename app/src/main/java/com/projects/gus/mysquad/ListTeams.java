package com.projects.gus.mysquad;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListTeams extends Activity {
    private ListView list;
    private Cursor cursor;
    private DBController dbController;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_time);
    }

    public void onResume(){
        super.onResume();

        dbController = new DBController(getBaseContext());
        cursor = dbController.loadTime();

        String[]fieldsNames = new String[]{Database.ID,Database.NOME_TIME};
        int[] idViews = new int[]{R.id.idTime,R.id.nomeTime};

        final SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(),
                R.layout.activity_stylish_time,cursor,fieldsNames,idViews, 0);

        list = findViewById(R.id.listView);
        list.setAdapter(adaptador);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idTime;
                cursor.moveToPosition(position);
                idTime = cursor.getString(cursor.getColumnIndexOrThrow(Database.ID));
                Intent intent = new Intent(ListTeams.this, ListPlayersByTeam.class);
                intent.putExtra("idTime", idTime);
                Toast.makeText(getApplicationContext(),idTime,Toast.LENGTH_LONG);
                startActivity(intent);

                finish();
            }
        });

//        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
//                builder = new AlertDialog.Builder(ListTeams.this);
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

    }
}
