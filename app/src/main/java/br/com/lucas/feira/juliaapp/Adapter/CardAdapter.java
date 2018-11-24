package br.com.lucas.feira.juliaapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import br.com.lucas.feira.juliaapp.MateriasActivity;
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
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final Card c = cards.get(position);
        holder.titulo.setText(c.getTitulo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MateriasActivity.class);
                intent.putExtra("id_dia", c.getCodigo());
                context.startActivity(intent);
            }
        });

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        public viewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
