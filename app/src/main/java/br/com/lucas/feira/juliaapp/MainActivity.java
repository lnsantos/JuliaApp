package br.com.lucas.feira.juliaapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.lucas.feira.juliaapp.Adapter.CardAdapter;
import br.com.lucas.feira.juliaapp.Entidade.Card;
import br.com.lucas.feira.juliaapp.Interfaces.CardAdapterInterface;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CardAdapterInterface{

    @BindView(R.id.recycler)
    RecyclerView rView;
    CardSql cardSql;
    private CardAdapter cardAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cardSql = CardSql.getInstance(this);

        List<Card> cars = cardSql.listarCards();
        cardAdapter = new CardAdapter(cars,this,this);

        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(cardAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Card newCard = (Card) data.getSerializableExtra("new_card");
                cardAdapter.update(newCard);
            }
        }
    }
}
