package br.com.tiagoluzs.ulbragastos.bean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.Objects;

public class Gasto {
    public static final int ENTRADA = 1;
    public static final int SAIDA = -1;

    public int id;
    public String descricao;
    public float valor;
    public int tipo; // entrada ou saída
    public Date data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gasto gasto = (Gasto) o;
        return Float.compare(gasto.valor, valor) == 0 &&
                tipo == gasto.tipo &&
                descricao.equals(gasto.descricao) &&
                data.equals(gasto.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao, valor, tipo, data);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
