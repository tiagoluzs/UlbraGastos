package br.com.tiagoluzs.ulbragastos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelpder extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DBNAME = "UlbraGastos.db";

    public DbHelpder(Context context) {
        super(context,DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table gastos (" +
                " id integer primary key," +
                " data text," +
                " descricao text," +
                " valor float" +
                ")";

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

