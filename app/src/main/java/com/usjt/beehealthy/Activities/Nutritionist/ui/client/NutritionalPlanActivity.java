package com.usjt.beehealthy.Activities.Nutritionist.ui.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.usjt.beehealthy.Model.NutritionalPlan;
import com.usjt.beehealthy.Model.NutritionistClient;
import com.usjt.beehealthy.Model.Patient;
import com.usjt.beehealthy.R;
import com.usjt.beehealthy.Utilities.Util;

import java.util.List;

public class NutritionalPlanActivity extends AppCompatActivity {

    public Bundle bundle = new Bundle();
    public RequestQueue requestQueue;
    public List<NutritionalPlan> plans;
    public Long idnutritionist;
    public Patient patient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritional_plan);

        bundle = getIntent().getExtras();



    }



    public void getPlans(Long id) {

        requestQueue = Volley.newRequestQueue(this);
        String url = getString(R.string.web_service_url) + "/client/" + id;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                (response) -> {

                    try {
                        plans = Util.populatePlan(response);
                        // setList();
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
}
