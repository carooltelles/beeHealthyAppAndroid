package com.usjt.beehealthy.Activities.Nutritionist.ui.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.usjt.beehealthy.Model.Nutritionist;
import com.usjt.beehealthy.Model.NutritionistClient;
import com.usjt.beehealthy.R;
import com.usjt.beehealthy.Utilities.Util;

import java.util.ArrayList;
import java.util.List;

public class ClientsFragment extends Fragment {

    public List<NutritionistClient> clients;
    private RequestQueue requestQueue;
    public RecyclerView clientRecycler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_clients, container, false);

        try{
            clients = new ArrayList<>();
            Nutritionist nutritionist = (Nutritionist) getActivity().getIntent().getExtras().get("Nutritionist");
            getClients(nutritionist.getIduser());
            clientRecycler = root.findViewById(R.id.clients_list_recycler);
        }catch(Exception e){
            throw e;
        }

        return root;
    }



    public void getClients(Long id) {

        requestQueue = Volley.newRequestQueue(getActivity());
        String url = getString(R.string.web_service_url) + "/client/" + id;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                (response) -> {

                    try {
                        System.out.println("Entrou aqui");
                        clients = Util.clientsObj(response);
                        setList();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                },
                (exception) -> {
                    Toast.makeText(
                            getContext(),
                            getString(R.string.connect_error),
                            Toast.LENGTH_SHORT
                    ).show();
                    exception.printStackTrace();
                });

        requestQueue.add(request);
    }

    public void setList(){
        System.out.println("entrou no set list");
        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        clientRecycler.setLayoutManager(linear);
        ClientAdapter adapter = new ClientAdapter(clients);
        adapter.notifyDataSetChanged();
        clientRecycler.setAdapter(adapter);
    }
}