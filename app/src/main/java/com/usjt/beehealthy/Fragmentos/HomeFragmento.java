package br.com.gabriel.firebase.Fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import br.com.gabriel.firebase.R;
import br.com.gabriel.firebase.model.Paciente;
import br.com.gabriel.firebase.model.PlanoNutricionista;

public class HomeFragmento extends Fragment {

    private TextView homeNomeNutri, homeData, homeAlm, homeCafe, homeLanche, homeTitulo;
    private View view;
    private Paciente user;
    private RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmento_home, container, false);
        iniciaComponentes();
        try {
            Bundle bundle = getArguments();
            assert bundle != null;
            user = (Paciente) bundle.getSerializable("Paciente");
            enviaApi(user.getId());
        } catch (Exception e) {
        }

        return view;
    }

    private void exibirDados(PlanoNutricionista plano) {
        homeTitulo.setText(getText(R.string.Plano));
        homeNomeNutri.setText(getText(R.string.Nome) + "\n" + plano.getNutricionista());
        homeLanche.setText(getText(R.string.Lanche) + "\n" + plano.getLanche());
        homeData.setText(getText(R.string.Data) + "\n" + plano.getDiaSem());
        homeCafe.setText(getText(R.string.Cafe) + "\n" + plano.getCafe());
        homeAlm.setText(getText(R.string.Alm) + "\n" + plano.getAlmoco());
    }

    private void iniciaComponentes() {
        homeAlm = view.findViewById(R.id.homeAlm);
        homeCafe = view.findViewById(R.id.homeCafe);
        homeData = view.findViewById(R.id.homeData);
        homeLanche = view.findViewById(R.id.homeLanche);
        homeNomeNutri = view.findViewById(R.id.homeNomeNutri);
        homeTitulo = view.findViewById(R.id.homeTitulo);

    }

    private void enviaApi(int palavra) {
        String url = getString(R.string.web_service_url) + "/plan/patient/" + palavra;
        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        JsonArrayRequest req = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                (resultado) -> {
                    try {
                        JSONObject iesimo;
                        for (int i = 0; i < resultado.length(); i++) {
                            iesimo = resultado.getJSONObject(i);
                            int id = iesimo.getInt("idplan");
                            String dia = iesimo.getString("weekDay");
                            String cafe = iesimo.getString("breakfast");
                            String lanche = iesimo.getString("lunch");
                            String dinner = iesimo.getString("dinner");
                            String nutri = iesimo.getJSONObject("nutritionist").getString("fullname");
                            PlanoNutricionista plano = new PlanoNutricionista(id, dia, cafe, dinner, lanche, nutri);
                            exibirDados(plano);
                        }
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

}