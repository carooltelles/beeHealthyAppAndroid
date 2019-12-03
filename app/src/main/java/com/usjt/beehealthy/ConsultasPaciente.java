package com.usjt.beehealthy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.usjt.beehealthy.Model.ConsultaMarcada;

import java.util.Objects;

public class ConsultasPaciente extends AppCompatActivity {

    private ConsultaMarcada consultasMarcadas;
    private Button btnCancelrConsulta;
    private TextView textNomeNutriConsulta, textEspConsulta, textEndConsulta, textDataConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_paciente);
        consultasMarcadas = (ConsultaMarcada) getIntent().getSerializableExtra("Consulta");
        iniciarComponentes();
        setarValores();
        btnCancelrConsulta.setOnClickListener(v -> enviaApi(consultasMarcadas.getId()));
    }

    private void setarValores() {
        textNomeNutriConsulta.setText(getText(R.string.Nome) + ": " + consultasMarcadas.getNutricionist().getNome());
        textEspConsulta.setText(getText(R.string.Esp) + ": " + consultasMarcadas.getNutricionist().getSpecialization());
        textEndConsulta.setText(getText(R.string.End) + ": " + consultasMarcadas.getLocal());
        textDataConsulta.setText(getText(R.string.Data) + " " + consultasMarcadas.getData());
    }

    private void iniciarComponentes() {
        textDataConsulta = findViewById(R.id.textDataConsulta);
        textEndConsulta = findViewById(R.id.textEnderecoConsulta);
        textEspConsulta = findViewById(R.id.textEspConsulta);
        textNomeNutriConsulta = findViewById(R.id.textNomeNutriConsulta);
        btnCancelrConsulta = findViewById(R.id.CancelarConsulta);
    }

    private void enviaApi(int idCons) {
        String url = getString(R.string.web_service_url) + "/consult/" + idCons;
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(this));
        JsonArrayRequest req = new JsonArrayRequest(
                Request.Method.DELETE,
                url,
                null,
                (resultado) -> {
                    Toast.makeText(this, getText(R.string.ConsultCancelada), Toast.LENGTH_SHORT).show();
                },
                (excecao) -> {
                    Toast.makeText(
                            this,
                            "Consulta Cancelada",
                            Toast.LENGTH_SHORT
                    ).show();
                    excecao.printStackTrace();
                    finish();
                }
        );
        requestQueue.add(req);
    }
}
