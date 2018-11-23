package br.com.lucas.feira.juliaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.lucas.feira.juliaapp.Entidade.Card;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewCard extends AppCompatActivity {

    @BindView(R.id.editTextTitulo)
    EditText titulo;
    @BindView(R.id.btnSalvar)
    Button btnSalvar;
    CardSql cardSql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        ButterKnife.bind(this);
        cardSql = CardSql.getInstance(this);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card newCard = salvar();
                cardSql.novoCard(newCard);

                Intent intent = new Intent();
                intent.putExtra("new_card", newCard);

                setResult(RESULT_OK, intent);
                finish();

                Toast.makeText(NewCard.this, "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public Card salvar(){
        Card c = new Card();
        c.setTitulo(titulo.getText().toString());
        return c;
    }
}
