package com.projects.gus.mysquad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBController {
    private SQLiteDatabase dbSQL;
    private Database db;

    public DBController(Context context){
        db = new Database(context);
    }

    public String insertData(byte[] photo, String nome, int idTime, String dataNascimento, String peDominante, String posicao, String caracteristicas){
        ContentValues values;
        long result;

        dbSQL = db.getWritableDatabase();
        values = new ContentValues();

        values.put(Database.PHOTO, photo);
        values.put(Database.NOME,nome);
        values.put(Database.ID_TIME, idTime);
        values.put(Database.DATA_NASCIMENTO,dataNascimento);
        values.put(Database.PE_DOMINANTE,peDominante);
        values.put(Database.POSICAO,posicao);
        values.put(Database.CARACTERISTICAS,caracteristicas);

        result = dbSQL.insert(Database.TB_DADOS_JOGADOR,null,values);
        dbSQL.close();

        if(result == -1)
            return "Erro ao salvar!";
        else
            return "Dados salvos com sucesso!";
    }

    public Cursor loadJogador(){
        Cursor cursor;
        String[]fields = {db.ID,db.NOME};
        dbSQL = db.getReadableDatabase();
        cursor = dbSQL.query(db.TB_DADOS_JOGADOR,fields,null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        dbSQL.close();
        return cursor;
    }

    public Cursor loadJogadorById(int id){
        Cursor cursor;
        String[] campos =  {db.ID,db.PHOTO,db.NOME,db.DATA_NASCIMENTO,db.PE_DOMINANTE,db.POSICAO,db.CARACTERISTICAS};
        String where = Database.ID + "=" + id;
        dbSQL = db.getReadableDatabase();
        cursor = dbSQL.query(Database.TB_DADOS_JOGADOR,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void updateJogador(int idJogador,byte[] photo,String nome,String dataNascimento,String peDominante,String posicao,String caracteristicas){
        ContentValues values;
        String where;

        dbSQL = db.getWritableDatabase();

        where = Database.ID + "=" + idJogador;

        values = new ContentValues();
        values.put(Database.PHOTO, photo);
        values.put(Database.NOME, nome);
        values.put(Database.DATA_NASCIMENTO, dataNascimento);
        values.put(Database.PE_DOMINANTE, peDominante);
        values.put(Database.POSICAO, posicao);
        values.put(Database.CARACTERISTICAS, caracteristicas);

        dbSQL.update(Database.TB_DADOS_JOGADOR,values,where,null);
        db.close();
    }

    public void deleteJogador(int id){
        String where = db.ID + "=" + id;
        dbSQL = db.getReadableDatabase();
        dbSQL.delete(db.TB_DADOS_JOGADOR,where,null);
        dbSQL.close();
    }

        public String insertDataTime(String nomeTime){
        ContentValues values;
        long result;

        dbSQL = db.getWritableDatabase();
        values = new ContentValues();
        values.put(Database.NOME_TIME,nomeTime);

        result = dbSQL.insert(Database.TB_TIME,null,values);
        dbSQL.close();

        if(result == -1)
            return "Erro ao salvar!";
        else
            return "Dados salvos com sucesso!";
    }

        public Cursor loadTime(){
        Cursor cursor;
        String[]fields = {db.ID,db.NOME_TIME};
        dbSQL = db.getReadableDatabase();
        cursor = dbSQL.query(db.TB_TIME,fields,null, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        dbSQL.close();
        return cursor;
    }

        public Cursor loadJogadorByIdTime(int idTime){
        Cursor cursor;
        String[] campos =  {db.ID,db.ID_TIME,db.PHOTO,db.NOME,db.DATA_NASCIMENTO,db.PE_DOMINANTE,db.POSICAO,db.CARACTERISTICAS};
        String where = Database.ID_TIME + "=" + idTime;
        dbSQL = db.getReadableDatabase();
        cursor = dbSQL.query(Database.TB_DADOS_JOGADOR,campos,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
}

