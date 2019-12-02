package com.usjt.beehealthy.Activities.Nutritionist.ui.client;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.usjt.beehealthy.Model.NutritionalPlan;
import com.usjt.beehealthy.Model.NutritionistClient;
import com.usjt.beehealthy.R;
import com.usjt.beehealthy.Utilities.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class NutritionalPlanAddActivity extends AppCompatActivity {


    public EditText weekday;
    public EditText breakfast;
    public EditText lunch;
    public EditText dinner;
    public RequestQueue requestQueue;
    public Bundle bundle = new Bundle();
    public NutritionistClient client;
    public NutritionalPlan plan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritional_plan_add);
        bundle = getIntent().getExtras();
        client = (NutritionistClient) bundle.getSerializable("client");
        initComponents();

        FloatingActionButton fab = findViewById(R.id.plan_fab_save_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addPlan();
                } catch (Exception e) {
                    try {
                        throw e;
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
    }


    public void initComponents() {
        weekday = findViewById(R.id.plan_detail_weekday_text_add);
        breakfast = findViewById(R.id.plan_detail_breakfast_text_add);
        lunch = findViewById(R.id.plan_detail_lunch_text_add);
        dinner = findViewById(R.id.plan_detail_dinner_text_add);
    }


    public void fillFields() {
        weekday.setText(plan.getWeekDay());
        breakfast.setText(plan.getBreakfast());
        lunch.setText(plan.getLunch());
        dinner.setText(plan.getDinner());
    }


    public void addPlan() throws JSONException {
        try {
            JSONObject planObject = Util.fillPlan
                    (weekday.getText().toString(), breakfast.getText().toString(),
                            lunch.getText().toString(), dinner.getText().toString(),
                            client.getNutritionist().iduser, client.getPatient().getIduser());

            requestQueue = Volley.newRequestQueue(this);
            String url = getString(R.string.web_service_url) + "/plan/";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, planObject,
                    (response) -> {
                        try {

                            plan = Util.getPlan(response);
                            fillFields();
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

        } catch (Exception e) {
            throw e;
        }
    }
}
