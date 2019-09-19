package br.com.tiagoluzs.ulbragastos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.tiagoluzs.ulbragastos.DbHelper;
import br.com.tiagoluzs.ulbragastos.bean.Gasto;

public class GastoDao {

    private SQLiteDatabase db;
    private DbHelper banco;

    public GastoDao(Context context) {
        banco = new DbHelper(context);
    }

    public long save(Gasto gasto) {
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put("tipo",gasto.getTipo());
        valores.put("descricao",gasto.getDescricao());
        SimpleDateFormat sdf = new SimpleDateFormat(("yyyy-MM-dd"));
        try {
            valores.put("data", sdf.format(gasto.getData()));
        } catch(Exception e) {
            valores.put("data", sdf.format(new Date()));
        }
        valores.put("valor",gasto.getValor());

        if(gasto.getId() == 0) {
            Log.d("GastoDao()","Insert gasto: " + valores.toString());
            resultado = db.insert("gastos",null, valores);
            Log.d("GastoDao()","Insert result: " + resultado);
        } else {
            Log.d("GastoDao()","Update gasto: " + valores.toString());
            String args[] = new String[]{String.valueOf(gasto.getId())};
            resultado = db.update("gastos",valores," id = ?" , args);
            Log.d("GastoDao()","Update result: " + resultado);
        }
        db.close();
        return resultado;
    }

    public long delete(Gasto gasto) {
        db = banco.getWritableDatabase();
        String args[] = new String[]{String.valueOf(gasto.getId())};
        long resultado = db.delete("gastos","id = ?",args);
        db.close();
        return resultado;
    }

    public ArrayList<Gasto> getAll(String mes, String ano) {
        ArrayList<Gasto> lista = new ArrayList<Gasto>();

        db = banco.getReadableDatabase();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,Integer.valueOf(ano));
        cal.set(Calendar.MONTH,Integer.valueOf(mes));
        cal.set(Calendar.DAY_OF_MONTH,1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dt_ini = sdf.format(cal.getTime());

        cal.set(Calendar.DAY_OF_MONTH,cal.getLeastMaximum(Calendar.DAY_OF_MONTH));
        String dt_fim = sdf.format(cal.getTime());

        String selectArgs[] = new String[]{dt_ini,dt_fim};
        String sql = "select id,data,valor,descricao,tipo from gastos where date(data) >= ? and date(data) <= ? ";
        sql = "select id,data,valor,descricao,tipo from gastos ";

        Log.d("GastoDao()","select: " + sql);
        Log.d("GastoDao()","args: " + selectArgs.toString());

        Cursor cursor = db.rawQuery(sql,null);
        if(cursor != null) {
            cursor.moveToFirst();
            while(cursor.moveToNext()) {
                    Gasto gasto = new Gasto();
                    gasto.setId(cursor.getInt(0));
                    try {
                        gasto.setData(sdf.parse(cursor.getString(1)));
                    } catch(Exception e) {
                        gasto.setData(new Date());
                        //e.printStackTrace();
                    }
                    gasto.setValor(cursor.getFloat(2));
                    gasto.setDescricao(cursor.getString(3));
                    gasto.setTipo(cursor.getInt(4));
                    lista.add(gasto);
            }
        }
        db.close();
        return lista;
    }


}
