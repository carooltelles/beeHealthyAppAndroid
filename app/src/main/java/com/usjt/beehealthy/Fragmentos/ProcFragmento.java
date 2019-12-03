package br.com.gabriel.firebase.Fragmentos;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import br.com.gabriel.firebase.Adapter.ClienteAdapter;
import br.com.gabriel.firebase.R;
import br.com.gabriel.firebase.model.Nutricionista;
import br.com.gabriel.firebase.model.Usuario;

public class ProcFragmento extends Fragment {

    private RecyclerView lstDados;
    private TextView busca;
    private List<Nutricionista> dados;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_procura, container, false);
        lstDados = view.findViewById(R.id.lstDados);
        busca = view.findViewById(R.id.Busca);
        lstDados.setHasFixedSize(true);
        enviaApi("");
        eventoEdit();

        return view;
    }

    private void eventoEdit() {
        busca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String palavra = busca.getText().toString().trim();
                enviaApi(palavra);
            }
        });
    }



    private List<Nutricionista> enviaApi(String palavra) {
        String url = getString(R.string.web_service_url) + "/nutritionist/search?fullname=" + palavra;
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        JsonArrayRequest req = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                (resultado) -> {
                    dados = new ArrayList<>();
                    try {
                        JSONObject iesimo;
                        for (int i = 0; i < resultado.length(); i++) {
                            iesimo = resultado.getJSONObject(i);
                            int idUser = iesimo.getInt("iduser");
                            String email = iesimo.getString("email");
                            String pwd = iesimo.getString("password");
                            String name = iesimo.getString("fullname");
                            String niver = iesimo.getString("birthday");
                            String esp = iesimo.getString("specialization");
                            int crn = iesimo.getInt("crn");
                            String local = iesimo.getString("address");
                            Nutricionista n = new Nutricionista(idUser, name, email, pwd,niver,crn,esp,local);
                            dados.add(n);
                        }
                        iniciaRecyclerView(dados);
                    } catch (JSONException e) {
                        Toast.makeText(getContext(),"Erro na resposta",Toast.LENGTH_SHORT).show();
                    }

                },
                (excecao) -> {
                    Toast.makeText(
                            getContext(),
                            getString(R.string.connect_error),
                            Toast.LENGTH_SHORT
                    ).show();
                    excecao.printStackTrace();
                }
        );
        requestQueue.add(req);
        return dados;
    }

    private void iniciaRecyclerView(List<Nutricionista> dados) {
        //Criação do LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        lstDados.setLayoutManager(linearLayoutManager);

        //Passando para o adapter
        ClienteAdapter clienteAdapter = new ClienteAdapter(dados);

        //Setando na tela
        lstDados.setAdapter(clienteAdapter);
        clienteAdapter.notifyDataSetChanged();
    }
}