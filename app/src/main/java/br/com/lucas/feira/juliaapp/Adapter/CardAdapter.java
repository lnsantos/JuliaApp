package br.com.lucas.feira.juliaapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
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
import br.com.lucas.feira.juliaapp.Entidade.Card;
import br.com.lucas.feira.juliaapp.Interfaces.CardAdapterInterface;
import br.com.lucas.feira.juliaapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.viewHolder> {

    List<Card> cards;
    Context context;
    CardAdapterInterface cardAdapterInterface;

    public CardAdapter(List<Card> cards, Context context, CardAdapterInterface cardAdapterInterface) {
        this.cards = cards;
        this.context = context;
        this.cardAdapterInterface = cardAdapterInterface;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        final Card c = cards.get(position);
        holder.titulo.setText(c.getTitulo());

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Criando o AlertDialog
                final AlertDialog.Builder dialogConstruido = new AlertDialog.Builder(context);
                // Titulo do Dialog
                dialogConstruido.setTitle("Adicionar Matéria");
                // Colocando EditText para usuario escrever

                final EditText campoMateria = new EditText(context);
                // Incluir view no Dialog
                dialogConstruido.setView(campoMateria);
                // Botão para adicionar um cartão
                dialogConstruido.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Aula a = new Aula();
                        a.setTitulo(campoMateria.getText().toString());
                        CardSql cardSql = CardSql.getInstance(context);
                        if(cardSql.novaMateria(a,c.getCodigo()) != false){
                            Toast.makeText(context, "Sucesso", Toast.LENGTH_SHORT).show();
                        }else Toast.makeText(context, "Falha", Toast.LENGTH_SHORT).show();
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

        holder.cView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards != null ? cards.size() : 0;
    }

    public void update(Card card) {
        cards.add(card);
        if(cards == null) Toast.makeText(context, "Problema com cards", Toast.LENGTH_SHORT).show();
        else notifyItemChanged(cards.size());
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTitulo)
        TextView titulo;
        @BindView(R.id.button)
        Button btn;
        @BindView(R.id.cardID)
        CardView cView;
        String codigo;
        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
