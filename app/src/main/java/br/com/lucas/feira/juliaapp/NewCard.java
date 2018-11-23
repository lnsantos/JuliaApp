package br.com.lucas.feira.juliaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.lucas.feira.juliaapp.Adapter.CardAdapter;
import br.com.lucas.feira.juliaapp.Entidade.Aula;
import br.com.lucas.feira.juliaapp.Entidade.Card;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewCard extends AppCompatActivity {

    @BindView(R.id.recyclerMateria)
    RecyclerView rView;
    CardSql cardSql;
    List<Aula> aulas;
    private CardAdapter cardAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        ButterKnife.bind(this);
        aulas = new ArrayList();
        cardSql = CardSql.getInstance(this);

        cardAdapter = new CardAdapter(aulas,this);

        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(cardAdapter);
    }


}
