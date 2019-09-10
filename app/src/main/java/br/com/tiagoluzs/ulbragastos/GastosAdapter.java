package br.com.tiagoluzs.ulbragastos;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.tiagoluzs.ulbragastos.bean.Gasto;

public class GastosAdapter extends RecyclerView.Adapter {

    public ArrayList<Gasto> lista;

    void setLista(ArrayList<Gasto> lista){
        this.lista = lista;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
