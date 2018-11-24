package br.com.lucas.feira.juliaapp.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                "codigo INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "titulo TEXT " +
                ");");

        db.execSQL(" CREATE TABLE IF NOT EXISTS aula(" +
                "codigo INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "titulo TEXT," +
                "id_card INTEGER," +
                "FOREIGN KEY(id_card) REFERENCES card(codigo)" +
                ");");

        db.execSQL("INSERT INTO card(titulo) values('Segunda-Feira'),('Terça-Feira'),('Quarta-Feira'),('Quinta-Feira'),('Sexta-Feira'),('Sábado'),('Domingo')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Aula> listaMaterias(int idDia){
        Cursor c = db.query("aula", null, "codigo = ?", new String[]{String.valueOf(idDia)}, null, null, null);
        return toListAulas(c);
    }
    private List<Aula> toListAulas(Cursor c){
        List<Aula> aulas = new ArrayList<>();
        if(c.moveToFirst()){
            aulas = new ArrayList<>();
            do{
                aulas.add(new Aula(c.getInt(c.getColumnIndex("codigo")),c.getString(c.getColumnIndex("titulo")),c.getInt(c.getColumnIndex("id_card"))) );
            }while(c.moveToNext());
        }
        return aulas;
    }

    public boolean novaMateria(Aula a) {
        ContentValues cv = new ContentValues();
        cv.put("titulo", a.getTitulo());
        cv.put("id_card",a.getId_card());

        return db.insert("aula",null ,cv) > 0;
    }

    public boolean novoCard(Card cs){
        ContentValues cv = new ContentValues();
        cv.put("titulo",cs.getTitulo());
        return db.insert("card",null,cv) > 0;
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

    public long updateMaterias(Aula aula){
        ContentValues cv = new ContentValues();
        cv.put("titulo",aula.getTitulo());
       return db.update("aula",cv,"codigo = ?", new String[]{aula.getCodigo().toString()});
    }

    public boolean deletaMateria(String codigo){
        return db.delete("aula", "codigo = ?", new String[]{codigo}) > 0;
    }

    public List<Card> listarCards(){
        Cursor c = db.query("card",null,null,null,null, null,null);
        return toList(c);
    }
}
