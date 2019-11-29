package com.usjt.beehealthy.Activities.Nutritionist.ui.client;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.usjt.beehealthy.Model.Nutritionist;
import com.usjt.beehealthy.Model.NutritionistClient;
import com.usjt.beehealthy.R;

public class ClientDetails extends AppCompatActivity {

    private NutritionistClient client;
    private Nutritionist nutritionist;
    private TextInputEditText fullname, email, birthday, weight, height, description ;
    private Bundle bundle = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle = getIntent().getExtras();
        client = (NutritionistClient) bundle.getSerializable("client");
        nutritionist = (Nutritionist) bundle.getSerializable("nutritionist");

        initComponents();
        fillFields();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), NutritionalPlanActivity.class);
            intent.putExtra("client", client);
            intent.putExtra("idnutritionist", nutritionist.getIduser());
            startActivity(intent);
        });
    }



    public void initComponents(){
        fullname = findViewById(R.id.client_fullname_details);
        email = findViewById(R.id.client_email_details);
        birthday = findViewById(R.id.client_birthday_details);
        weight = findViewById(R.id.client_weight_details);
        height = findViewById(R.id.client_height_details);
        description = findViewById(R.id.client_description_details);
    }


    public void fillFields(){
        fullname.setText(client.getPatient().getFullname());
        email.setText(client.getPatient().getEmail());
        birthday.setText(client.getPatient().getBirthday());
        weight.setText(client.getPatient().getWeight().toString());
        height.setText(client.getPatient().getHeight().toString());
        description.setText(client.getPatient().getDescription());
    }

}
