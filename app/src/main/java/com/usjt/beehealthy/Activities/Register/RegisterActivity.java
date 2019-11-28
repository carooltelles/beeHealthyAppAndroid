package com.usjt.beehealthy.Activities.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.usjt.beehealthy.Activities.MainActivity;
import com.usjt.beehealthy.Activities.Nutritionist.NutritionistMenu;
import com.usjt.beehealthy.Model.Nutritionist;
import com.usjt.beehealthy.R;
import com.usjt.beehealthy.Utilities.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullname, email, password;
    private RadioButton patientRButton, nutritionistRButton;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        requestQueue = Volley.newRequestQueue(this);
        loadComponents();
    }

       private void loadComponents(){
        fullname = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        patientRButton = findViewById(R.id.checkPacient);
        nutritionistRButton = findViewById(R.id.checkDoctor);
    }


    private void validateRegisterFields(EditText fullname ,EditText email, EditText password){

        if (email.getText().toString().equals("")) {
            alert("Email em branco");
            email.requestFocus();
        } else if (password.getText().toString().equals("")) {
            alert("Senha em branco");
            password.requestFocus();
        } else if (fullname.toString().equals("")) {
            alert("Nome em branco");
        }
    }

    public void register(View view) throws JSONException{
        System.out.println("register");
        validateRegisterFields(fullname ,email, password);
        String url = getString(R.string.web_service_url) + "/user/register/";
        String type = Util.getType(patientRButton, nutritionistRButton);
        JSONObject user = Util.userObj(email, password,type, fullname );
        MainActivity main = new MainActivity();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,url,user,
                (response) -> {
                    try {
                        if (type == "nutritionist") {

                            Nutritionist nutritionist = (Nutritionist) Util.gettingLoginAttributes(type, response);
                            loginNutritionist(nutritionist);

                        }else if (type == "patient"){

                        }
                    } catch (JSONException e) {
                        Toast.makeText(this, "Erro na resposta", Toast.LENGTH_SHORT).show();
                    }

                },
                (excecao) -> {
                    Toast.makeText(
                            this,
                            getString(R.string.connect_error),
                            Toast.LENGTH_SHORT
                    ).show();
                    excecao.printStackTrace();
                }
        );
        requestQueue.add(req);
    }

    private void alert(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }


    public void loginNutritionist(Nutritionist nutritionist){
        Intent intent = new Intent(this, NutritionistMenu.class);
        intent.putExtra("Nutritionist", nutritionist);
        startActivity(intent);
    }
}
