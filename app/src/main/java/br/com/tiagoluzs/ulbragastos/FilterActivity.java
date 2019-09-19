package br.com.tiagoluzs.ulbragastos;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.Date;

public class FilterActivity extends AppCompatActivity {

    public CalendarView calendarView ;
    FloatingActionButton floatingClose;
    public static int ano;
    public static int mes;
    public static int dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        floatingClose = findViewById(R.id.floatingClose);

        Intent intent = getIntent();

        Date d = new Date();
        int defaultAno = d.getYear();
        int defaultMes = d.getMonth();

        ano = Integer.valueOf(intent.getStringExtra("ano"));
        mes = Integer.valueOf(intent.getStringExtra("mes"));

        Log.d("FilterActivity()","ano: "+ano);
        Log.d("FilterActivity()","mes: "+mes);

        final Calendar cal = Calendar.getInstance();
        cal.set(ano,mes,1);

        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int selAno, int selMes, int selDia) {
                ano = selAno;
                mes = selMes;
                dia = selDia;
                finish();
            }
        });

        floatingClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
