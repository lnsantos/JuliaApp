package br.com.lucas.feira.juliaapp.Activity;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.lucas.feira.juliaapp.Adapter.MateriaAdapter;
import br.com.lucas.feira.juliaapp.Interfaces.CardAdapterInterface;
import br.com.lucas.feira.juliaapp.Interfaces.MateriaAdapterInterface;
import br.com.lucas.feira.juliaapp.SQLite.CardSql;
import br.com.lucas.feira.juliaapp.Entidade.Aula;
import br.com.lucas.feira.juliaapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MateriasActivity extends AppCompatActivity implements MateriaAdapterInterface {

    @BindView(R.id.recyclerMateria)
    RecyclerView rView;

    private CardSql cardSql;
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

        rView.setLayoutManager(new LinearLayoutManager(this));

        doList();

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
                            doList();
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

    @Override
    public void alterar(final Aula aula) {
        // Criando o AlertDialog
        final AlertDialog.Builder dialogConstruido = new AlertDialog.Builder(MateriasActivity.this);
        // Titulo do Dialog
        dialogConstruido.setTitle("Alterar Matéria");
        // Colocando EditText para usuario escrever

        final EditText campoMateria = new EditText(MateriasActivity.this);
        // Incluir view no Dialog
        dialogConstruido.setView(campoMateria);

        dialogConstruido.setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                aula.setTitulo(campoMateria.getText().toString().trim());
                if (cardSql.updateMaterias(aula) > -1) {
                    mens("Sucesso ao alterar");
                    doList();
                } else {
                    mens("Problema ao alterar !");
                }
            }
        });

        dialogConstruido.show();
    }

    @Override
    public void deletar(final Aula aula) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MateriasActivity.this);

        builder.setTitle("Deseja realmente excluir ?");

        builder.setPositiveButton("Sim !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (cardSql.deletaMateria(aula.getCodigo().toString())) {
                    mens("Removido com sucesso !");
                    doList();
                } else {
                    mens("Falha ao remover");
                }
            }
        });

        builder.setNegativeButton("Não !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mens("Okay ...");
            }
        });

        builder.show();
    }

    private void doList() {
        List<Aula> lista = cardSql.listaMaterias(idDia);
        rView.setAdapter(new MateriaAdapter(lista, MateriasActivity.this, MateriasActivity.this));
    }

    private void mens(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
