package com.usjt.beehealthy.Activities.Nutritionist.ui.consult;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.usjt.beehealthy.Model.Consult;
import com.usjt.beehealthy.Model.Nutritionist;

import com.usjt.beehealthy.R;
import com.usjt.beehealthy.Utilities.Util;

import java.util.ArrayList;
import java.util.List;

public class ConsultFragment extends Fragment {

    private ConsultViewModel consultViewModel;
    private RequestQueue requestQueue;
    private List<Consult> consults;
    public ListView consultList;
    public RecyclerView consultRecycler;
    TextView textView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_consult, container, false);
        try {
            consults = new ArrayList<Consult>();
            Nutritionist nutritionist = (Nutritionist) getActivity().getIntent().getExtras().get("Nutritionist");
            getConsultsByNutritionist(nutritionist.getIduser());
            consultRecycler = root.findViewById(R.id.consult_list_recycler);

        } catch (Exception e) {
            throw e;
        }
//        ConsultAdapter adapter = new ConsultAdapter(consults,getActivity() );
//
//        consultList.setAdapter(adapter);


        return root;
    }

    public void setList(){
        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        consultRecycler.setLayoutManager(linear);
        ConsultAdapter adapter = new ConsultAdapter(consults);
        adapter.notifyDataSetChanged();
        consultRecycler.setAdapter(adapter);
    }


    public void getConsultsByNutritionist(Long id) {

        requestQueue = Volley.newRequestQueue(getActivity());
        String url = getString(R.string.web_service_url) + "/consult/nutritionist/" + id;
        JsonArrayRequest request = new JsonArrayRequest(Method.GET, url, null,
                (response) -> {

                    try {
                        System.out.println(response);
                        consults = Util.populateConsultList(response);
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

}