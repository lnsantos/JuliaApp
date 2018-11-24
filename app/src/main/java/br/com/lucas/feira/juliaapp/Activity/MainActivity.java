package br.com.lucas.feira.juliaapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.lucas.feira.juliaapp.Adapter.CardAdapter;
import br.com.lucas.feira.juliaapp.SQLite.CardSql;
import br.com.lucas.feira.juliaapp.Entidade.Card;
import br.com.lucas.feira.juliaapp.Interfaces.CardAdapterInterface;
import br.com.lucas.feira.juliaapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CardAdapterInterface {

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

        rView.setLayoutManager(new LinearLayoutManager(this));

        doList();
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

    private void doList() {
        List<Card> cards = cardSql.listarCards();
        rView.setAdapter(new CardAdapter(cards, MainActivity.this, MainActivity.this));
    }


}
