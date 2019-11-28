package com.usjt.beehealthy.Activities.Nutritionist.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.usjt.beehealthy.Model.Consult;
import com.usjt.beehealthy.Model.Nutritionist;
import com.usjt.beehealthy.R;
import com.usjt.beehealthy.Utilities.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private TextInputEditText fullname, email, password, birthday, crn, specialization;
    private RequestQueue requestQueue;
    private Button saveButton;
    private Long idnutritionist;
    private Nutritionist nutritionist;
    private final String type = "nutritionist";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        loadComponents(root);

        nutritionist = (Nutritionist) getActivity().getIntent().getExtras().get("Nutritionist");

        idnutritionist = nutritionist.getIduser();
        saveButton.setOnClickListener(v -> {
            try {
                saveChanges();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        putInfoScreen();
    }


    private void putInfoScreen() {
        fullname.setText(nutritionist.getFullname());
        email.setText(nutritionist.getEmail());
        password.setText(nutritionist.getPassword());
        birthday.setText(nutritionist.getBirthday());
        crn.setText(nutritionist.getCrn());
        specialization.setText(nutritionist.getSpecialization());
    }

    public void loadComponents(View view) {
        fullname = view.findViewById(R.id.nutritionist_fullname);
        email = view.findViewById(R.id.nutritionist_email);
        birthday = view.findViewById(R.id.nutritionist_birthday);
        password = view.findViewById(R.id.nutritionist_password);
        crn = view.findViewById(R.id.nutritionist_crn);
        specialization = view.findViewById(R.id.nutritionist_specialization);
        saveButton = view.findViewById(R.id.nutritionist_save);
    }


    public void saveChanges() throws JSONException {
        nutritionist = new Nutritionist(
                idnutritionist, email.getText().toString(), fullname.getText().toString(),
                password.getText().toString(),
                birthday.getText().toString(), specialization.getText().toString(), crn.getText().toString());

        JSONObject nutritionistObj = Util.nutritionistObj(nutritionist);
        System.out.println(nutritionistObj);
        requestQueue = Volley.newRequestQueue(getActivity());
        String url = getString(R.string.web_service_url) + "/nutritionist/" + idnutritionist;
        JsonObjectRequest req = new JsonObjectRequest(
                Method.PUT, url, nutritionistObj,
                (response) -> {
                    try {
                        nutritionist = (Nutritionist) Util.gettingLoginAttributes(type,response);
                        putInfoScreen();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                (excecao) ->

                {
                    NetworkResponse response = excecao.networkResponse;
                    excecao.printStackTrace();
                });
        requestQueue.add(req);
    }
}