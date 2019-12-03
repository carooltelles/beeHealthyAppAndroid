package com.usjt.beehealthy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.usjt.beehealthy.Model.Nutricionista;
import com.usjt.beehealthy.Model.Paciente;
import com.usjt.beehealthy.RegistraNutri;

import org.json.JSONException;

public class Register extends AppCompatActivity {

    private EditText nome, email, senha;
    private RadioButton paciente, nutricionista;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        requestQueue = Volley.newRequestQueue(this);
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        nome = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        senha = findViewById(R.id.registerPassword);
        paciente = findViewById(R.id.checkPacient);
        nutricionista = findViewById(R.id.checkDoctor);
    }

    public void entrar(Paciente paciente) {
        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("Paciente", paciente);
        startActivity(intent);
    }

    public void Autenticar(View view) {
        if (nome.length() > 0) {
            if (email.length() > 0) {
                if (senha.length() > 0) {
                    if (paciente.isChecked()) {
                        Paciente usuario = new Paciente(nome.getText().toString(), email.getText().toString()
                                , senha.getText().toString(), null);
                        enviaApi(usuario);

                    } else if (nutricionista.isChecked()) {
                        Nutricionista nutricionista = new Nutricionista(nome.getText().toString(), email.getText().toString()
                                , senha.getText().toString(), null, 0, null,null);
                        Intent i = new Intent(this, RegistraNutri.class);
                        i.putExtra("Nutri", nutricionista);
                        startActivity(i);

                    } else {
                        Toast.makeText(this, "Selecione o tipo de usuario", Toast.LENGTH_SHORT).show();
                        paciente.requestFocus();
                    }
                } else {
                    Toast.makeText(this, "Digite uma senha", Toast.LENGTH_SHORT).show();
                    senha.requestFocus();
                }
            } else {
                Toast.makeText(this, "Digite seu email", Toast.LENGTH_SHORT).show();
                email.requestFocus();
            }
        } else {
            Toast.makeText(this, "Digite seu nome", Toast.LENGTH_SHORT).show();
            nome.requestFocus();
        }

    }

    public void enviaApi(Paciente paciente) {
        String url = getString(
                R.string.web_service_url
        ) + "/user/register/";
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.POST,
                url,
                paciente.json(),
                (resultado) -> {
                    try {
                        int idUser = resultado.getInt("iduser");
                        paciente.setId(idUser);
                        entrar(paciente);
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
}