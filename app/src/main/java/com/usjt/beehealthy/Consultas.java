package com.usjt.beehealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.usjt.beehealthy.Model.Consulta;
import com.usjt.beehealthy.Model.Horario;
import com.usjt.beehealthy.Model.Nutricionista;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import br.com.gabriel.firebase.Adapter.ConsutaAdapter;

public class Consultas extends AppCompatActivity {

    private RecyclerView lstDados;
    private Nutricionista n;
    private ConsutaAdapter consutaAdapter;
    private ImageView consFoto;
    private TextView consNome, consEsp, consEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        Intent intent = getIntent();
        n = (Nutricionista) intent.getSerializableExtra("Nutri");
        iniciarComponentes();
        setInfo();
        enviaApi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        enviaApi();
    }

    private void iniciaRecyclerView(ArrayList h) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstDados.setLayoutManager(linearLayoutManager);
        consutaAdapter = new ConsutaAdapter(h);
        lstDados.setAdapter(consutaAdapter);
    }

    private void setInfo() {
        consFoto.setImageResource(n.getFoto());
        consNome.setText(n.getNome());
        consEsp.setText(n.getSpecialization());
        consEnd.setText(n.getLocal());
    }


    private void enviaApi() {
        String url = getString(R.string.web_service_url) + "/consult/nutritionist/" + n.getId();
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this));
        JsonArrayRequest req = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                (resultado) -> {
                    ArrayList<String> dados = new ArrayList<>();
                    try {
                        JSONObject iesimo;
                        for (int i = 0; i < resultado.length(); i++) {
                            iesimo = resultado.getJSONObject(i);
                            String date = iesimo.getString("date");
                            dados.add(date);
                        }
                        ArrayList h =  comparar(dados);
                        iniciaRecyclerView(h);
                    } catch (JSONException e) {
                        Toast.makeText(this, "Erro na resposta", Toast.LENGTH_SHORT).show();
                    }

                },
                (excecao) -> {
                    Toast.makeText(
                            this,
                            getString(R.string.connect_error),
                            Toast.LENGTH_SHORT
                    ).show();
                    excecao.printStackTrace();
                }
        );
        requestQueue.add(req);
    }

    private ArrayList comparar(ArrayList<String> dados) {
        ArrayList consulta = (ArrayList<Consulta>) todasConsultas();
        for(String s:dados) {
            for (int i = 0; i < consulta.size(); i++) {
                Consulta a = (Consulta) consulta.get(i);
                for (int j = 0; j < a.getHorarios().size();j++) {
                    Horario h = a.getHorarios().get(j);
                    String DH = a.getData() + " " + h.getHorario();
                    if (s.equals(DH))
                        a.getHorarios().remove(h);
                }
                if(a.getHorarios().isEmpty()){
                    consulta.remove(a);
                }
            }
        }

        return consulta;
    }

    private void iniciarComponentes() {
        consFoto = findViewById(R.id.consFoto);
        consEsp = findViewById(R.id.consEsp);
        consNome = findViewById(R.id.consNome);
        lstDados = findViewById(R.id.consList);
        consEnd = findViewById(R.id.consEnd);
    }

    private List<Consulta> todasConsultas() {
        ArrayList<Consulta> a =  new ArrayList<>();
        for(int i = 0; i < 6;i++){
            Calendar hoje = Calendar.getInstance();
            hoje.add(Calendar.DATE,i);
            String data = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(hoje.getTime());
            a.add(new Consulta(todosHorarios(), data, n));
            hoje.clear();
        }
        return a;
    }


    private List<Horario> todosHorarios() {
        return new ArrayList<>(Arrays.asList(
                new Horario("08:00:00"),
                new Horario("09:00:00"),
                new Horario("10:00:00"),
                new Horario("11:00:00"),
                new Horario("13:00:00"),
                new Horario("14:00:00"),
                new Horario("15:00:00")
        ));
    }

}
