package br.com.tiagoluzs.ulbragastos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import br.com.tiagoluzs.ulbragastos.bean.Gasto;

public class GastosAdapter extends RecyclerView.Adapter {

    public ArrayList<Gasto> lista;
    private Context context;
    private RecyclerView recyclerView;

    public GastosAdapter(ArrayList<Gasto> lista,Context context, RecyclerView recyclerView) {
        this.lista = lista;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    void setLista(ArrayList<Gasto> lista){
        this.lista = lista;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_item, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = recyclerView.getChildLayoutPosition(view);
                Intent intent = new Intent(context, GastoEditActivity.class);
                intent.putExtra("gasto",lista.get(itemPosition));
                context.startActivity(intent);
            }
        });

        return new GastosHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        GastosHolder h = (GastosHolder)holder;

        final Gasto it = lista.get(position);
        if(it == null) {
            Log.d("GastosAdapter()","it nulo! => " + position);
            return;
        }

        if(h.txtDescricao != null) {
            h.txtDescricao.setText(it.getDescricao());
        }

        if(h.txtData != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            h.txtData.setText(sdf.format(it.getData()));
        }

        if(h.txtValor != null) {
            float val = it.getValor();
            h.txtValor.setText(NumberFormat.getCurrencyInstance().format(val));
            if(it.getTipo() == Gasto.ENTRADA) {
                h.txtValor.setTextColor(Color.BLACK);
            } else {
                h.txtValor.setTextColor(Color.RED);
            }
        }

    }

    @Override
    public int getItemCount() {
        if(this.lista == null || this.lista.size() == 0)
            return 0;
        else
            return this.lista.size();
    }


}
