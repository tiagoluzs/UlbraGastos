package br.com.tiagoluzs.ulbragastos;

import androidx.appcompat.app.AppCompatActivity;

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

    EditText txtDescricao;
    EditText txtValor;
    EditText txtData;
    RadioButton radEntrada;
    RadioButton radSaida;
    RadioGroup radioGroup;

    Gasto gasto;

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

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(R.id.radEntrada == i) {
                    gasto.setTipo(Gasto.ENTRADA);
                } else if(R.id.radSaida == i) {
                    gasto.setTipo(Gasto.SAIDA);
                }
            }
        });

        this.btnCancelar = findViewById(R.id.btnCancelar);
        this.btnSalvar = findViewById(R.id.btnSalvar);

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

        this.gasto = new Gasto();
    }

    void salvar() {
        GastoDao dao = new GastoDao(getBaseContext());


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
