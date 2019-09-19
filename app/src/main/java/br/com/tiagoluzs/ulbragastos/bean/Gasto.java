package br.com.tiagoluzs.ulbragastos.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Gasto implements Parcelable {
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

    public Gasto() {
        this.tipo = Gasto.ENTRADA;
        this.data = new Date();
        this.valor = 0;
        this.descricao = "";


    }

    public Gasto(Parcel parcel) {
        this.descricao = parcel.readString();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dt = null;
        try {
            dt = sdf.parse(parcel.readString());
        } catch(Exception e) {
            dt = new Date();
        }
        this.data = dt;
        this.tipo = parcel.readInt();
        this.valor = parcel.readFloat();
        this.id = parcel.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.descricao);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        parcel.writeString(sdf.format(this.getData()));
        parcel.writeInt(this.getTipo());
        parcel.writeFloat(this.getValor());
        parcel.writeInt(this.getId());
    }

    public static final Parcelable.Creator<Gasto> CREATOR
            = new Parcelable.Creator<Gasto>() {
        public Gasto createFromParcel(Parcel in) {
            return new Gasto(in);
        }

        public Gasto[] newArray(int size) {
            return new Gasto[size];
        }
    };
}
