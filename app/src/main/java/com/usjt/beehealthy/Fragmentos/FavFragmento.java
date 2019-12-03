package br.com.gabriel.firebase.Fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import br.com.gabriel.firebase.Adapter.ListaConsultasAdapter;
import br.com.gabriel.firebase.R;
import br.com.gabriel.firebase.model.ConsultaMarcada;
import br.com.gabriel.firebase.model.Nutricionista;
import br.com.gabriel.firebase.model.Usuario;

public class FavFragmento extends Fragment {

    private RecyclerView favRecycler;
    private Usuario user;
    private List<ConsultaMarcada> dados;
    private RequestQueue requestQueue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_fav, container, false);
        favRecycler = view.findViewById(R.id.favRecicler);
        Bundle bundle = getArguments();
        user = (Usuario) bundle.getSerializable("Paciente");
        enviaApi(user.getId());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        enviaApi(user.getId());
    }

    private void enviaApi(int palavra) {
        String url = getString(R.string.web_service_url) + "/consult/patient/" + palavra;
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
                            int idCons = iesimo.getInt("idconsult");
                            String place = iesimo.getString("place");
                            String date = iesimo.getString("date");
                            JSONObject a = iesimo.getJSONObject("nutritionist");
                            int idUser = a.getInt("iduser");
                            String email = a.getString("email");
                            String pwd = a.getString("password");
                            String name = a.getString("fullname");
                            String niver = a.getString("birthday");
                            String esp = a.getString("specialization");
                            int crn = a.getInt("crn");
                            String local = a.getString("address");
                            Nutricionista n = new Nutricionista(idUser, name, email, pwd, niver, crn, esp,local);
                            dados.add(new ConsultaMarcada(idCons,date, n, place, null));
                        }
                        iniciaRecyclerView(dados);
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Erro na resposta", Toast.LENGTH_SHORT).show();
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
    }

    private void iniciaRecyclerView(List<ConsultaMarcada> dados) {

        //Criação do LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        favRecycler.setLayoutManager(linearLayoutManager);

        //Passando para o adapter
        ListaConsultasAdapter listaConsultasAdapter = new ListaConsultasAdapter(dados);

        //Setando na tela
        favRecycler.setAdapter(listaConsultasAdapter);
        listaConsultasAdapter.notifyDataSetChanged();
    }
}
