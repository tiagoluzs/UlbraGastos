package br.com.tiagoluzs.ulbragastos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.tiagoluzs.ulbragastos.bean.Gasto;
import br.com.tiagoluzs.ulbragastos.dao.GastoDao;

public class MainActivity extends AppCompatActivity {
    Button btnNovo;
    RecyclerView listView;
    public GastosAdapter adapter;
    public String mes;
    public String ano;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNovo =  findViewById(R.id.btnNovo);
        listView = findViewById(R.id.listView);

        listView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(llm);

        adapter = new GastosAdapter(null, getBaseContext());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        this.ano = String.valueOf(cal.get(Calendar.YEAR));
        this.mes = String.valueOf(cal.get(Calendar.MONTH));

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), GastoEditActivity.class);
                startActivity(intent);
            }
        });

        this.list();
    }

    void list() {
        Log.d("MainActivity()","list()");
        GastoDao dao = new GastoDao(getApplicationContext());
        ArrayList<Gasto> lista = dao.getAll(this.mes, this.ano);
        adapter = new GastosAdapter(lista,getBaseContext());
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        Log.d("MainActivity()","list() =======> " + lista.size());

    }
}
