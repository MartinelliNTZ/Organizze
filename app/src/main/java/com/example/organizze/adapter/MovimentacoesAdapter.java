package com.example.organizze.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizze.R;
import com.example.organizze.models.Movimentacao;


import java.util.List;

public class MovimentacoesAdapter extends RecyclerView.Adapter<MovimentacoesAdapter.MyViewHolder> {

    private List<Movimentacao> lista;
    private Context context;

    public MovimentacoesAdapter(Context context,List<Movimentacao> lista) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.lista_movimentacao,parent,false);
        return new MyViewHolder(itemList);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Movimentacao  movimentacao = lista.get(position);
        if(movimentacao.getTipo().equals(Movimentacao.TYPE_RECEITA)){
            holder.txtValMov.setTextColor(context.getResources().getColor(R.color.normalGreen));
            holder.txtValMov.setText(movimentacao.getValor().toString());
        }else{
            holder.txtValMov.setTextColor(context.getResources().getColor(R.color.colorAcent));
            holder.txtValMov.setText("-"+movimentacao.getValor().toString());
        }

        holder.txtCatMov.setText(movimentacao.getCategoria());
        holder.txtDescMov.setText(movimentacao.getDescricao());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView txtDescMov, txtCatMov, txtValMov;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtValMov=itemView.findViewById(R.id.txtValMov);
            txtCatMov=itemView.findViewById(R.id.txtCatMov);
            txtDescMov=itemView.findViewById(R.id.txtDescMov);
        }
    }
}
