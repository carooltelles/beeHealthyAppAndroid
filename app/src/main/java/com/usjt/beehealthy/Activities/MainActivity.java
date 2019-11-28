package com.usjt.beehealthy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.usjt.beehealthy.Activities.Nutritionist.NutritionistMenu;
import com.usjt.beehealthy.Activities.Register.RegisterActivity;
import com.usjt.beehealthy.Model.Nutritionist;
import com.usjt.beehealthy.R;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.usjt.beehealthy.Utilities.Util;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText email;
    private EditText password;
    private RadioButton patientRButton;
    private RadioButton nutritionistRButton;
    private SignInButton btnSingIn;
    private FirebaseAuth mFirebaseAuth;
    private GoogleApiClient mGoogleApiClient;
    private RequestQueue requestQueue;
    private String type = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectaGoogleApi();
    }

    @Override
    protected void onStart() {
        super.onStart();
        iniciaComponentes();
        iniciaFirebase();
        clickButton();

    }

    private void clickButton() {
        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singGoogle();
            }
        });
    }

    private void conectaGoogleApi() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void iniciaFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private void iniciaComponentes() {
        email = findViewById(R.id.loginUsuario);
        password = findViewById(R.id.loginSenha);
        btnSingIn = findViewById(R.id.btnSingIn);
        patientRButton = findViewById(R.id.checkPacient);
        nutritionistRButton = findViewById(R.id.checkNutritionist);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void authenticate(View view) throws JSONException {
        validateLoginFields(email, password, patientRButton, nutritionistRButton);
        type = Util.getType(patientRButton, nutritionistRButton);
        JSONObject user = Util.userObj(email, password, type);
        requestQueue = Volley.newRequestQueue(this);

        String url = getString(R.string.web_service_url) + "/user/login";

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.POST, url, user, (response) -> {
            try {
                if (type == "nutritionist") {

                    Nutritionist nutritionist = (Nutritionist) Util.gettingLoginAttributes(type, response);
                    loginNutritionist(nutritionist);

                }else if (type == "patient"){

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
                (excecao) -> {
                    NetworkResponse response = excecao.networkResponse;
                    if (response.statusCode == 404) {
                        Toast.makeText(
                                this,
                                "Login Inválido",
                                Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        Toast.makeText(
                                this,
                                getString(R.string.connect_error),
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                    excecao.printStackTrace();
                } );
        requestQueue.add(req);
    }

//    private void entrar(User user) {
//        Intent intent = new Intent(this, Menu.class);
//        intent.putExtra("User",user);
//        startActivity(intent);
//    }

    public void loginNutritionist(Nutritionist nutritionist){
        Intent intent = new Intent(this, NutritionistMenu.class);
        intent.putExtra("Nutritionist", nutritionist);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        alert("Falha na conexão");
    }

    private void alert(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void singGoogle() {
        Intent i = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                //Alterar para mandar para o banco
                //firebaseLogin(account);
            }
        }
    }

//    private void firebaseLogin(GoogleSignInAccount account) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//        mFirebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Paciente paciente = new Paciente(account.getDisplayName(),
//                                account.getEmail(),account.getId(),"Pacient",null);
//                        entrar(paciente);
//                    } else {
//                        alert("Falha na autenticação");
//                    }
//                });
//    }


    public void validateLoginFields(EditText email, EditText password,
                                    RadioButton patient, RadioButton nutritionist) {
        if (email.getText().toString().equals("")) {
            alert("Login em branco");
            email.requestFocus();
        } else if (password.getText().toString().equals("")) {
            alert("Senha em branco");
            password.requestFocus();
        } else if (!patient.isChecked() && !nutritionist.isChecked()) {
            alert("Tipo de usuário vazio");
        }
    }
}
