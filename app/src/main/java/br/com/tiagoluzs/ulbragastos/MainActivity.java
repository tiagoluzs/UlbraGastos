package br.com.tiagoluzs.ulbragastos;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.tiagoluzs.ulbragastos.bean.Gasto;
import br.com.tiagoluzs.ulbragastos.dao.GastoDao;

public class MainActivity extends AppCompatActivity {

    RecyclerView listView;
    public GastosAdapter adapter;
    public static String dia;
    public static String mes;
    public static String ano;
    public float saldo;
    public TextView txtSaldo;
    ConstraintLayout layout;
    FloatingActionButton btnNovo;
    Toolbar toolbar;
    ActionBar actionBar;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getActionBar();

        btnNovo = findViewById(R.id.btnNovo);

        layout = findViewById(R.id.layout);

        listView = findViewById(R.id.listView);
        txtSaldo = findViewById(R.id.txtSaldo);

        listView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(llm);

        adapter = new GastosAdapter(null, getBaseContext(),listView);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        this.ano = String.valueOf(cal.get(Calendar.YEAR));
        this.mes = String.valueOf(cal.get(Calendar.MONTH)+1);
        this.dia = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

        layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MainActivity.hideKeyboardFrom(getBaseContext(),layout);
            }
        });

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), GastoEditActivity.class);
                startActivity(intent);
            }
        });

        this.list();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        createDialogWithoutDateField(Integer.valueOf(ano), Integer.valueOf(mes)-1, Integer.valueOf(dia)).show();
        return true;

    }

    private DatePickerDialog createDialogWithoutDateField(int sano, int smes, int sdia) {
        datePickerDialog = new DatePickerDialog(this, null, sano, smes, sdia);
        try {
            java.lang.reflect.Field[] datePickerDialogFields = datePickerDialog.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(datePickerDialog);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                MainActivity.ano = String.valueOf(datePickerDialog.getDatePicker().getYear());
                MainActivity.mes = String.valueOf(datePickerDialog.getDatePicker().getMonth()+1);
                if(MainActivity.mes.length() != 2) {
                    MainActivity.mes = "0" + MainActivity.mes;
                }
                MainActivity.dia = String.valueOf(datePickerDialog.getDatePicker().getDayOfMonth());
                list();
            }
        });

        return datePickerDialog;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.list();
    }

    @Override
    public boolean onNavigateUp() {
        this.list();
        return super.onNavigateUp();
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    void list() {
        Log.d("MainActivity()","list() => " + this.mes + "/" + this.ano );
        GastoDao dao = new GastoDao(getApplicationContext());

        if(this.mes.length() != 2) {
            toolbar.setTitle("0"+this.mes+"/"+this.ano);
        } else {
            toolbar.setTitle(this.mes+"/"+this.ano);
        }

        ArrayList<Gasto> lista = dao.getAll(this.mes, this.ano);
        saldo = 0;
        for(int i = 0; i < lista.size(); i++) {
            Gasto g = lista.get(i);
            if(g.getTipo() == Gasto.ENTRADA) {
                saldo += g.valor;
            } else {
                saldo -= g.valor;
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("R$ #0.00");

        this.txtSaldo.setText(getResources().getString(R.string.saldo)+" : " + decimalFormat.format(saldo));

        adapter = new GastosAdapter(lista,getBaseContext(),listView);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        Log.d("MainActivity()","list() =======> " + lista.size());

    }
}
