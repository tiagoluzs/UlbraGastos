package br.com.tiagoluzs.ulbragastos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DBNAME = "UlbraGastos.db";
    private SQLiteDatabase db;

    public DbHelper(Context context) {
        super(context,DBNAME, null, VERSION);
        Log.d("DbHelper()","DbHelper() Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("DbHelper()","onCreate()");
        String sql = "create table gastos (" +
                " id integer primary key autoincrement," +
                " data text," +
                " descricao text," +
                " valor float," +
                " tipo integer" +
                ")";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("DbHelper()","onUpdate()");
        String sql = "drop table if exists gastos";
        db.execSQL(sql);
    }
}

