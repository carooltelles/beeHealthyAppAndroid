package com.usjt.beehealthy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.usjt.beehealthy.Model.Consulta;
import com.usjt.beehealthy.Model.ConsultaMarcada;
import com.usjt.beehealthy.Model.Horario;

import java.text.DateFormat;
import java.util.Date;

public class MarcarConsulta extends AppCompatActivity {

    private TextView txtMcData, txtMcEsp, txtMcNutri;
    private ImageView McFoto;
    private Consulta consulta;
    private ListView listView;
    private String result;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("id", Context.MODE_PRIVATE);
        result = sharedPreferences.getString("id", "");
        setContentView(R.layout.activity_marcar_consulta);
        consulta = (Consulta) getIntent().getSerializableExtra("Consultas");
        assert consulta != null;
        iniciarComponentes();
        setarValores();
    }

    public String dataF(String data) {
        String[] a = data.split("/");
        return a[1] + "/" + a[0] + "/" + a[2];
    }

    public void setarValores() {
        McFoto.setImageResource(consulta.getNutricionista().getFoto());
        txtMcNutri.setText(txtMcNutri.getText() + ":" + consulta.getNutricionista().getNome());
        txtMcEsp.setText(txtMcEsp.getText() + ":" + consulta.getNutricionista().getSpecialization());
        Date a = new Date(dataF(consulta.getData()));
        String b = DateFormat.getDateInstance(DateFormat.FULL).format(a);
        txtMcData.setText(b);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            //Seu codigo aqui
            Horario h = consulta.getHorarios().get(position);
            ConsultaMarcada c = new ConsultaMarcada(consulta.getData(), consulta.getNutricionista(),
                    consulta.getNutricionista().getLocal(), h.getHorario(), Integer.parseInt(result));
            Intent i = new Intent(this, ConsultaM.class);
            i.putExtra("Consulta", c);
            startActivityForResult(i, 1);
        });
        ArrayAdapter<Horario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, consulta.getHorarios());
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    public void iniciarComponentes() {
        McFoto = findViewById(R.id.McFoto);
        txtMcNutri = findViewById(R.id.txtMcNutri);
        txtMcEsp = findViewById(R.id.txtMcEsp);
        txtMcData = findViewById(R.id.txtMcData);
        listView = findViewById(R.id.marcarconsulta);
    }
}


