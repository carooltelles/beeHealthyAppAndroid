package com.usjt.beehealthy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.usjt.beehealthy.Configuracoes;
import com.usjt.beehealthy.Model.Paciente;
import com.usjt.beehealthy.R;

import br.com.gabriel.firebase.Fragmentos.FavFragmento;
import br.com.gabriel.firebase.Fragmentos.HomeFragmento;
import br.com.gabriel.firebase.Fragmentos.ProcFragmento;



public class Menu extends AppCompatActivity {

    private Fragment fragSelecionado = null;
    private Bundle bundle = new Bundle();
    private BottomNavigationView.OnNavigationItemSelectedListener navListener;
    private Paciente user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        bundle = getIntent().getExtras();
        user =(Paciente) bundle.getSerializable("Paciente");
        alert(user.getSenha());
        docPreferences(user);
        iniciarComponentes();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void iniciarComponentes() {
        navListener = menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.Home:
                    fragSelecionado = new HomeFragmento();
                    break;
                case R.id.favorito:
                    fragSelecionado = new FavFragmento();
                    break;
                case R.id.pesquisar:
                    fragSelecionado = new ProcFragmento();
                    break;
            }
            fragSelecionado.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fadein, R.anim.fadeout)
                    .replace(R.id.container_fragmento, fragSelecionado).commit();

            return true;
        };

        BottomNavigationView bottomNavigationView = findViewById(R.id.botoes_navegacao);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        fragSelecionado = new HomeFragmento();
        fragSelecionado.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragmento,
                fragSelecionado).addToBackStack("").commit();
    }

    private void docPreferences(Paciente user) {
        SharedPreferences sharedPreferences = getSharedPreferences("id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", user.getId()+"");
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(this, Configuracoes.class);
        boolean g = bundle.getBoolean("Google");
        i.putExtra("Paciente",user);
        i.putExtra("Google",g);
        startActivityForResult(i,1);
        return true;
    }

    private void alert(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1){
            finish();
        }
    }
}
