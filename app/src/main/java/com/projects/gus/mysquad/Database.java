package com.projects.gus.mysquad;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.support.v4.graphics.BitmapCompat;

public class Database extends SQLiteOpenHelper {

    Context c;

    //NOME DO BANCO
    private static final String DB_NAME = "mysquad.db";

    //NOME DAS TABELAS
    public static final String TB_TIME = "time";
    public static final String TB_DADOS_JOGADOR = "jogador";

    //NOME DOS CAMPOS DA TABELA "DADOS JOGADOR"
    public static final String ID = "_id";
    public static final String ID_TIME = "_id_time";
    public static final String NOME_TIME = "nome_time";
    public static final String PHOTO = "foto";
    public static final String NOME = "nome";
    public static final String DATA_NASCIMENTO = "data_nascimento";
    public static final String PE_DOMINANTE = "pd";
    public static final String POSICAO = "posicao";
    public static final String CARACTERISTICAS = "caracteristicas";

    //VERSAO DO BANCO
    private static final int VERSAO = 1;


    public Database(Context context){
        super(context,DB_NAME,null,VERSAO);
        c = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sqlTime = "CREATE TABLE " + TB_TIME + "("
                    + ID + " integer primary key autoincrement,"
                    + NOME_TIME + " text"
                    + ")";

            String sqlJogador = "CREATE TABLE " + TB_DADOS_JOGADOR + "("
                    + ID + " integer primary key autoincrement,"
                    + ID_TIME + " integer,"
                    + PHOTO + " blob,"
                    + NOME + " text,"
                    + DATA_NASCIMENTO + " text,"
                    + PE_DOMINANTE + " text,"
                    + POSICAO + " text,"
                    + CARACTERISTICAS + " text,"
                    + "FOREIGN KEY("+ID_TIME+")"+" REFERENCES "+TB_TIME+"("+ID+"))";

            db.execSQL(sqlTime);
            db.execSQL(sqlJogador);
        }catch (Exception ex) {
            final AlertDialog alertDialog = new AlertDialog.Builder(c).create();
            alertDialog.setTitle("Erro");
            alertDialog.setMessage(ex.getMessage());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.dismiss();
                        }
                    });
            alertDialog.show();
        }}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_DADOS_JOGADOR);
        db.execSQL("DROP TABLE IF EXISTS " + TB_TIME);
        onCreate(db);
    }
}
