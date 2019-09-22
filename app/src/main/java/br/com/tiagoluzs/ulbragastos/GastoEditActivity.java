package br.com.tiagoluzs.ulbragastos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import br.com.tiagoluzs.ulbragastos.bean.Gasto;
import br.com.tiagoluzs.ulbragastos.dao.GastoDao;

public class GastoEditActivity extends AppCompatActivity {

    Button btnCancelar;
    Button btnSalvar;
    Button btnExcluir;

    EditText txtDescricao;
    EditText txtValor;
    EditText txtData;
    RadioButton radEntrada;
    RadioButton radSaida;
    RadioGroup radioGroup;

    Gasto gasto;

    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasto_edit);

        txtValor = findViewById(R.id.txtValor);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtData = findViewById(R.id.txtData);
        radioGroup = findViewById(R.id.radioGroup);
        radEntrada = findViewById(R.id.radEntrada);
        radSaida = findViewById(R.id.radSaida);
        layout = findViewById(R.id.layout);

        this.btnCancelar = findViewById(R.id.btnCancelar);
        this.btnSalvar = findViewById(R.id.btnSalvar);
        this.btnExcluir = findViewById(R.id.btnExcluir);

        this.btnExcluir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                excluir();
            }
        });
        this.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        this.btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

        if(getIntent().getParcelableExtra("gasto") != null) {
            this.gasto = getIntent().getParcelableExtra("gasto");
        } else {
            this.gasto = new Gasto();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        this.txtData.setText(sdf.format(this.gasto.getData()));
        this.txtDescricao.setText(this.gasto.getDescricao());
        this.txtValor.setText(String.valueOf(this.gasto.getValor()));

        this.radEntrada.setChecked(this.gasto.getTipo() == Gasto.ENTRADA);
        this.radSaida.setChecked(this.gasto.getTipo() == Gasto.SAIDA);

        if(this.gasto.id == 0) {
            this.btnExcluir.setVisibility(View.INVISIBLE);
        } else {
            this.btnExcluir.setVisibility(View.VISIBLE);
        }

        layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MainActivity.hideKeyboardFrom(getBaseContext(),layout);
            }
        });
    }

    void excluir() {
        GastoDao dao = new GastoDao(getBaseContext());
        dao.delete(this.gasto);
        Toast.makeText(getBaseContext(),"Gasto excluído!",Toast.LENGTH_LONG).show();
        finish();
    }

    void salvar() {
        GastoDao dao = new GastoDao(getBaseContext());

        if(this.radSaida.isChecked()) {
            gasto.setTipo(Gasto.SAIDA);
        } else if(this.radEntrada.isChecked()) {
            gasto.setTipo(Gasto.ENTRADA);
        }
        if(gasto.getTipo() != Gasto.ENTRADA && gasto.getTipo() != Gasto.SAIDA) {
            Toast.makeText(getBaseContext(),"Selecione se é entrada ou saída.",Toast.LENGTH_LONG).show();
            return;
        }

        gasto.setDescricao(txtDescricao.getText().toString());
        if(gasto.getDescricao().length() <= 1) {
            Toast.makeText(getBaseContext(),"Informe uma descrição para o gasto ou entrada.",Toast.LENGTH_LONG).show();
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dtTxt = txtData.getText().toString();
        try {
            gasto.setData(sdf.parse(dtTxt));
        } catch(Exception e) {
            Toast.makeText(getBaseContext(),"Formato da data inválido. Deve ser dd/mm/aaaa.",Toast.LENGTH_LONG).show();
            return;
        }

        String valor = txtValor.getText().toString();
        try {
            float valorFloat = Float.parseFloat(valor);
            if(valorFloat <= 0) {
                Toast.makeText(getBaseContext(),"Informe o valor no formato ex. 343.43",Toast.LENGTH_LONG).show();
                return;
            }
            gasto.setValor(valorFloat);
        } catch(Exception e) {
            Toast.makeText(getBaseContext(),"Informe o valor no formato ex. 343.43",Toast.LENGTH_LONG).show();
            return;
        }

        long resultado = dao.save(gasto);
        if(resultado > 0) {
            Toast.makeText(getBaseContext(),"Gasto salvo com sucesso!",Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(getBaseContext(),"Não foi possível salvar gasto.",Toast.LENGTH_LONG).show();
        }
    }
}
