package com.usjt.beehealthy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.usjt.beehealthy.Model.Nutricionista;

public class PerfilNutricionista extends AppCompatActivity {
    private ImageView foto;
    private TextView nome, espec, ender;
    private Nutricionista n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_nutricionista);
        foto = findViewById(R.id.fotoP);
        nome = findViewById(R.id.textNomeP);
        espec = findViewById(R.id.textEspP);
        ender = findViewById(R.id.textEnderecoP);
        setarNutri();
    }

    @SuppressLint("SetTextI18n")
    private void setarNutri() {
        n = (Nutricionista) getIntent().getSerializableExtra("Nutri");
        assert n != null;
        foto.setImageResource(n.getFoto());
        nome.setText(getText(R.string.NomeCompleto) + ": " + n.getNome());
        espec.setText(getText(R.string.Esp) + ": " + n.getSpecialization());
        ender.setText(getText(R.string.End) + ": " + n.getLocal());
    }

    public void marcarConsulta(View view) {
        Intent i = new Intent(this, Consultas.class);
        i.putExtra("Nutri", n);
        startActivityForResult(i,1);
    }
}
