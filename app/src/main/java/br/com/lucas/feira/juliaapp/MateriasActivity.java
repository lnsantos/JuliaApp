package br.com.lucas.feira.juliaapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.lucas.feira.juliaapp.Adapter.CardAdapter;
import br.com.lucas.feira.juliaapp.Adapter.MateriaAdapter;
import br.com.lucas.feira.juliaapp.Entidade.Aula;
import br.com.lucas.feira.juliaapp.Entidade.Card;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MateriasActivity extends AppCompatActivity {

    @BindView(R.id.recyclerMateria)
    RecyclerView rView;
    CardSql cardSql;
    List<Aula> aulas;
    private MateriaAdapter cardAdapter;
    int idDia;

    @BindView(R.id.ok)
    FloatingActionButton ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cardSql = CardSql.getInstance(this);

        idDia = getIntent().getIntExtra("id_dia", -1);

        if (idDia > -1) {
            aulas = cardSql.listaMaterias(idDia);
        }
        cardAdapter = new MateriaAdapter(aulas, this);

        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(cardAdapter);



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criando o AlertDialog
                final AlertDialog.Builder dialogConstruido = new AlertDialog.Builder(MateriasActivity.this);
                // Titulo do Dialog
                dialogConstruido.setTitle("Adicionar Matéria");
                // Colocando EditText para usuario escrever

                final EditText campoMateria = new EditText(getApplicationContext());
                // Incluir view no Dialog
                dialogConstruido.setView(campoMateria);
                // Botão para adicionar um cartão
                dialogConstruido.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Aula a = new Aula();
                        a.setTitulo(campoMateria.getText().toString());
                        a.setId_card(idDia);
                        CardSql cardSql = CardSql.getInstance(getBaseContext());
                        if (cardSql.novaMateria(a)) {
                            Toast.makeText(MateriasActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();
                            cardAdapter.update(a);
                            dialogInterface.dismiss();
                        } else
                            Toast.makeText(MateriasActivity.this, "Falha", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogConstruido.setNegativeButton("Fecha", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialogConstruido.show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
