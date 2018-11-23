package br.com.lucas.feira.juliaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.lucas.feira.juliaapp.Entidade.Aula;
import br.com.lucas.feira.juliaapp.Entidade.Card;

public class CardSql extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "cardDB";
    private static final int VERSAO_BANCO = 1;

    private static CardSql cardSql;

    private SQLiteDatabase db;
    Context c;
    public synchronized static CardSql getInstance(Context context){
        if(cardSql==null) {
            cardSql = new CardSql(context);
        }
        return cardSql;
    }

    private CardSql(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
        db = getWritableDatabase();
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS card(" +
                "codigo INTEGER PRIMARY KEY NOT NULL, " +
                "titulo TEXT" +
                ");" +

                "CREATE TABLE IF NOT EXISTS aula(" +
                "codigo INTEGER PRIMARY KEY NOT NULL," +
                "titulo TEXT" +
                "id_card INTEGER" +
                "FOREIGN KEY(id_card) REFERENCES card(codigo)" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean novaMateria(Aula a,Integer id_card) {
        ContentValues cv = new ContentValues();
        cv.put("titulo", a.getTitulo());
        cv.put("id_card",a.getId_card());

        return db.insert("aula","",cv) > 0;
    }

    public boolean novoCard(Card cs){
        ContentValues cv = new ContentValues();
        cv.put("titulo",cs.getTitulo());
        return db.insert("card","",cv) > 0;
    }

    private List<Card> toList(Cursor c){
        List<Card> cards = null;
        if(c.moveToFirst()){
            cards = new ArrayList<>();
            do{
                cards.add(new Card(c.getInt(c.getColumnIndex("codigo")),c.getString(c.getColumnIndex("titulo"))));
            }while (c.moveToNext());
        }
        return cards;
    }



    public List<Card> listarCards(){
        Cursor c = db.query("card",null,null,null,null, null,null);
        return toList(c);
    }
}
