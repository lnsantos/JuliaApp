package br.com.lucas.feira.juliaapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    @BindView(R.id.novo_item)
    Button novo_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        cardSql = CardSql.getInstance(this);
        rView.setLayoutManager(new LinearLayoutManager(this));

        novo_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria o AlertDialog do item
                final AlertDialog.Builder dialogItem = new AlertDialog.Builder(MainActivity.this);
                // Titulo do item
                dialogItem.setTitle("Novo Item");
                // Colocando EditText para o usuario
                final EditText campoItem = new EditText(getApplicationContext());
                // view do dialog
                dialogItem.setView(campoItem);
                // btn novo item
                dialogItem.setPositiveButton("Criar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Card c = new Card();
                        c.setTitulo(campoItem.getText().toString());
                        CardSql cSql = CardSql.getInstance(getBaseContext());

                        if(cSql.novoCard(c)){
                            Toast.makeText(MainActivity.this, c.getTitulo() +" Adicionado com sucesso!", Toast.LENGTH_LONG);
                            doList();
                            dialog.cancel();
                        }else{
                            Toast.makeText(MainActivity.this, c.getTitulo() +" Problema ao Adicionar item!", Toast.LENGTH_LONG);
                            dialog.cancel();
                        }
                    }
                });
                // btn cancelar processo
                dialogItem.setNegativeButton("Fecha", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                //mostra o dialog
                dialogItem.show();
            }
        });

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
