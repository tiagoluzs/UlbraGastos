package br.com.tiagoluzs.ulbragastos;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class GastosHolder extends RecyclerView.ViewHolder {

    public TextView txtDescricao;
    public TextView txtData;
    public TextView txtValor;



    public GastosHolder(View itemView) {
        super(itemView);
        txtDescricao = (TextView) itemView.findViewById(R.id.txtDescricao);
        txtData = (TextView) itemView.findViewById(R.id.txtData);
        txtValor = (TextView) itemView.findViewById(R.id.txtValor);

    }

}
