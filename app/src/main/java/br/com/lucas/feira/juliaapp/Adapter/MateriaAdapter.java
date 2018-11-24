package br.com.lucas.feira.juliaapp.Adapter;

import android.app.AlertDialog;
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

import br.com.lucas.feira.juliaapp.Interfaces.CardAdapterInterface;
import br.com.lucas.feira.juliaapp.Interfaces.MateriaAdapterInterface;
import br.com.lucas.feira.juliaapp.SQLite.CardSql;
import br.com.lucas.feira.juliaapp.Entidade.Aula;
import br.com.lucas.feira.juliaapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MateriaAdapter extends RecyclerView.Adapter<MateriaAdapter.ViewHolder> {
    List<Aula> aulas;
    Context context;
    MateriaAdapterInterface materiaAdapterInterface;

    public MateriaAdapter(List<Aula> aulas, Context context, MateriaAdapterInterface materiaAdapterInterface) {
        this.aulas = aulas;
        this.context = context;
        this.materiaAdapterInterface = materiaAdapterInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_materia, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Aula a = aulas.get(i);
        viewHolder.titulo.setText(a.getTitulo());

        viewHolder.alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materiaAdapterInterface.alterar(a);
            }
        });

        viewHolder.deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materiaAdapterInterface.deletar(a);
            }
        });

    }

    @Override
    public int getItemCount() {
        return aulas != null ? aulas.size() : 0;
    }

    public void update(Aula a) {
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
            ButterKnife.bind(this, itemView);
        }
    }
}
