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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.usjt.beehealthy.Activities.Nutritionist.NutritionistMenu;
import com.usjt.beehealthy.Activities.Register.RegisterActivity;
import com.usjt.beehealthy.Menu;
import com.usjt.beehealthy.Model.Nutritionist;
import com.usjt.beehealthy.Model.Paciente;
import com.usjt.beehealthy.Model.Patient;
import com.usjt.beehealthy.R;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.usjt.beehealthy.Register;
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
                if (patientRButton.isChecked() || nutritionistRButton.isChecked())
                    singGoogle();
                else
                    alert("Escolha um perfil");
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
        Intent intent = new Intent(this, Register.class);
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

                } else if (type == "patient") {
                    Paciente p = new Paciente();
                    p.setEmail(email.getText().toString());
                    p.setSenha(password.getText().toString());
                    p.setTipo("patient");
                    enviaApi(p, false);
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
                });
        requestQueue.add(req);


    }


    private void entrar(Paciente user, boolean google) {
        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("Paciente", user);
        intent.putExtra("Google", google);
        startActivity(intent);
        mFirebaseAuth.signOut();
    }

    private void entrar(Nutritionist nutritionist, boolean google) {
        Intent intent = new Intent(this, NutritionistMenu.class);
        intent.putExtra("Nutritionist", nutritionist);
        intent.putExtra("Google", google);
        startActivity(intent);
        mFirebaseAuth.signOut();
    }

    public void enviaApi(Paciente paciente, boolean google) {
        String url = getString(R.string.web_service_url) + "/user/login";
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.POST,
                url,
                paciente.json(),
                (resultado) -> {
                    try {
                        paciente.setId(resultado.getInt("iduser"));
                        paciente.setNome(resultado.getString("fullname"));
                        paciente.setNascimento(resultado.getString("birthday"));
                        entrar(paciente, google);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                (excecao) -> {
                    if (excecao.networkResponse.statusCode == 404) {
                        if (google) {
                            enviaApiRegistrerPatient(paciente, google);
                        } else {
                            alert("Usuario não encontrado");
                        }
                    } else {
                        alert(getString(R.string.connect_error));
                        excecao.printStackTrace();
                    }
                }
        );
        requestQueue.add(req);
    }

    public void enviaApiRegistrerPatient(Paciente paciente, boolean google) {
        String url = getString(
                R.string.web_service_url
        ) + "/user/register/";
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.POST,
                url,
                paciente.json(),
                (resultado) -> {
                    try {
                        int idUser = resultado.getInt("iduser");
                        paciente.setId(idUser);
                        entrar(paciente, google);
                        finish();
                    } catch (JSONException e) {
                        alert("Erro na resposta2");
                    }
                },
                (excecao) -> {
                    alert(getString(R.string.connect_error));
                    excecao.printStackTrace();
                }
        );
        requestQueue.add(req);
    }


    public void enviaApiRegistrerNutricionista(Nutritionist nutritionist, boolean google) throws JSONException {

        requestQueue = Volley.newRequestQueue(this);
        JSONObject user = Util.userObj(nutritionist.getEmail(), nutritionist.getPassword(), "nutritionist", nutritionist.getFullname());
        String url = getString(
                R.string.web_service_url
        ) + "/user/register/";
        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.POST,
                url,
                user,
                (resultado) -> {
                    try {
                        Long idUser = resultado.getLong("iduser");
                        nutritionist.setIduser(idUser);
                        entrar(nutritionist, google);
                        finish();
                    } catch (JSONException e) {
                        alert("Erro na resposta2");
                    }
                },
                (excecao) -> {
                    alert(getString(R.string.connect_error));
                    excecao.printStackTrace();
                }
        );
        requestQueue.add(req);
    }


    public void loginNutritionist(Nutritionist nutritionist) {
        Intent intent = new Intent(this, NutritionistMenu.class);
        intent.putExtra("Nutritionist", nutritionist);
        startActivity(intent);
    }

    private void loginPatient(Patient user, boolean google) {
        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("Paciente", user);
        intent.putExtra("Google", google);
        startActivity(intent);
        mFirebaseAuth.signOut();
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
                try {
                    firebaseLogin(account);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void firebaseLogin(GoogleSignInAccount account) throws Exception{
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        System.out.println("Entrou");
                        if (patientRButton.isChecked()) {
                            System.out.println("Paciente");
                            Paciente paciente = new Paciente(account.getDisplayName(),
                                    account.getEmail(), account.getId(), null);
                            entrar(paciente, true);
                        } else if (nutritionistRButton.isChecked()) {
                            Nutritionist nutritionist = new Nutritionist(account.getDisplayName(), account.getEmail(), account.getId());
                            try {
                                enviaApiRegistrerNutricionista(nutritionist, true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        alert("Falha na autenticação");
                    }
                });
    }


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
