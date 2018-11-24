package br.com.lucas.feira.juliaapp.Adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.lucas.feira.juliaapp.CardSql;
import br.com.lucas.feira.juliaapp.Entidade.Aula;
import br.com.lucas.feira.juliaapp.MateriasActivity;
import br.com.lucas.feira.juliaapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MateriaAdapter  extends RecyclerView.Adapter<MateriaAdapter.ViewHolder>{
    List<Aula> aulas;
    Context context;
    CardSql cardSql = CardSql.getInstance(context);
    public MateriaAdapter(List<Aula> aulas, Context context) {
        this.aulas = aulas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_materia,viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
         Aula a = aulas.get(i);
        viewHolder.titulo.setText(a.getTitulo());

        viewHolder.alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Criando o AlertDialog
                final AlertDialog.Builder dialogConstruido = new AlertDialog.Builder(context);
                // Titulo do Dialog
                dialogConstruido.setTitle("Alterar Matéria");
                // Colocando EditText para usuario escrever

                final EditText campoMateria = new EditText(context);
                // Incluir view no Dialog
                dialogConstruido.setView(campoMateria);

                dialogConstruido.setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        a.setTitulo(campoMateria.getText().toString());
                        if(cardSql.updateMaterias(a) > -1){
                            aulas.get(i);
                            notifyItemChanged(i);
                            Toast.makeText(context, "Alterado com sucesso!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Problema com alterar!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialogConstruido.show();

            }
        });

        viewHolder.deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cardSql.deletaMateria(a.getCodigo().toString())){
                    aulas.remove(a);
                    notifyItemChanged(aulas.size());
                    Toast.makeText(context, "Removido com sucesso!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Saia e volte, para excluir esse item", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return aulas != null ? aulas.size() : 0;
    }

    public void update(Aula a){
        aulas.add(a);
        notifyItemChanged(aulas.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btnAlterar)
        Button alterar;
        @BindView(R.id.btnDeleta)
        Button deletar;
        @BindView(R.id.txtTitulo)
        TextView titulo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
