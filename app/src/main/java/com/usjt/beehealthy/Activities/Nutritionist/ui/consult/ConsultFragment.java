package com.usjt.beehealthy.Activities.Nutritionist.ui.consult;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
    ListView consultList;
    TextView textView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_consult, container, false);
        try {
            consults = new ArrayList<Consult>();
            Nutritionist nutritionist = (Nutritionist) getActivity().getIntent().getExtras().get("Nutritionist");
            //Nutritionist nutritionist = (Nutritionist) getActivity().getIntent().getSerializableExtra("Nutritionist");
            getConsultsByNutritionist(nutritionist.getIduser());

            consultList = (ListView) root.findViewById(R.id.consult_list);
            textView = root.findViewById(R.id.consult_text);

        } catch (Exception e) {

        }
//        ConsultAdapter adapter = new ConsultAdapter(consults,getActivity() );
//
//        consultList.setAdapter(adapter);


        return root;
    }


    public void getConsultsByNutritionist(Long id) {

        requestQueue = Volley.newRequestQueue(getActivity());
        String url = getString(R.string.web_service_url) + "/consult/nutritionist/" + id;
        JsonArrayRequest request = new JsonArrayRequest(Method.GET, url, null,
                (response) -> {

                    try {
                        Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                        System.out.println(response);
                        consults = Util.populateConsultList(response);

                        //colocar aki o resto
                        consultViewModel = ViewModelProviders.of(this).get(ConsultViewModel.class);
                        ArrayAdapter<Consult> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, consults);
                        //ConsultAdapter adapter = new ConsultAdapter(consults,getContext());
                        consultList.setAdapter(adapter);
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