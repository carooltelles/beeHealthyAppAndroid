package com.usjt.beehealthy.Activities.Nutritionist.ui.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.usjt.beehealthy.Model.NutritionalPlan;
import com.usjt.beehealthy.Model.Nutritionist;
import com.usjt.beehealthy.Model.NutritionistClient;
import com.usjt.beehealthy.Model.Patient;
import com.usjt.beehealthy.R;
import com.usjt.beehealthy.Utilities.Util;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class NutritionalPlanActivity extends AppCompatActivity {

    public Bundle bundle = new Bundle();
    public RequestQueue requestQueue;
    public List<NutritionalPlan> plans;
    public NutritionistClient client;
    public RecyclerView planRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritional_plan);

        try{
            plans = new ArrayList<NutritionalPlan>();
            bundle = getIntent().getExtras();
            client = (NutritionistClient) bundle.getSerializable("client");
            getPlans(client.getNutritionist().getIduser(), client.getPatient().getIduser());
            planRecycler = findViewById(R.id.plan_list_recycler);


            FloatingActionButton fab = findViewById(R.id.plan_add);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(view.getContext(), NutritionalPlanAddActivity.class);
                        intent.putExtra("client", client);
                        startActivity(intent);
                    } catch (Exception e) {
                        throw e;
                    }

                }
            });
        }catch(Exception e){
            throw e;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPlans(client.getNutritionist().getIduser(), client.getPatient().getIduser());
    }

    public void getPlans(Long idnutritionist, Long idpatient) {

        requestQueue = Volley.newRequestQueue(this);
        String url = getString(R.string.web_service_url) + "/plan/nutritionist/" + idnutritionist + "/patient/" + idpatient;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                (response) -> {

                    try {

                        plans = Util.populatePlan(response);
                        setList();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                },
                (exception) -> {
                    Toast.makeText(
                            this,
                            getString(R.string.connect_error),
                            Toast.LENGTH_SHORT
                    ).show();
                    exception.printStackTrace();
                });

        requestQueue.add(request);
    }


    public void setList(){
        LinearLayoutManager linear = new LinearLayoutManager(this);
        planRecycler.setLayoutManager(linear);
        NutritionalPlanAdapter adapter = new NutritionalPlanAdapter(plans);
        adapter.notifyDataSetChanged();
        planRecycler.setAdapter(adapter);
    }
}
