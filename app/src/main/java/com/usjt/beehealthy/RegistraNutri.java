package com.usjt.beehealthy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.usjt.beehealthy.Model.Nutricionista;
import com.usjt.beehealthy.R;

import br.com.gabriel.firebase.model.Nutricionista;

public class RegistraNutri extends AppCompatActivity {

    private EditText txtCRN,txtEsp,txtEnd;
    private Button buttonRegisterNutri;
    private Nutricionista nutricionista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_nutri);

        Bundle bundle = getIntent().getExtras();
        nutricionista =(Nutricionista) bundle.getSerializable("Nutri");
        iniciarComponentes();
        buttonRegisterNutri.setOnClickListener(v -> registra(v));

    }

    private void iniciarComponentes() {
        txtCRN = findViewById(R.id.registerCRN);
        txtEsp = findViewById(R.id.registerEsp);
        txtEnd = findViewById(R.id.registerEnd);
        buttonRegisterNutri = findViewById(R.id.buttonRegisterNutri);
    }

    public void registra(View view) {
        nutricionista.setCrn(Integer.parseInt(txtCRN.getText().toString()));
        nutricionista.setSpecialization(txtEsp.getText().toString());
        nutricionista.setLocal(txtEnd.getText().toString());
        enviaApi(nutricionista);
    }


    public void enviaApi(Nutricionista nutricionista){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = getString(
                R.string.web_service_url
        )+"/user/register/";
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.POST,
                url,
                nutricionista.array(),
                (resultado) ->{
                    Toast.makeText(this,"Sucesso\n"+resultado,Toast.LENGTH_LONG).show();
                },
                (excecao) ->{
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
}
