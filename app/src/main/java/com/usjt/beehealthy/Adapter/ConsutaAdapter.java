package br.com.gabriel.firebase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import br.com.gabriel.firebase.MarcarConsulta;
import br.com.gabriel.firebase.R;
import br.com.gabriel.firebase.model.Consulta;

public class ConsutaAdapter extends RecyclerView.Adapter<ConsutaAdapter.ViewHolderCliente> {

    private List<Consulta> dados;
    private View view;

    public ConsutaAdapter(List<Consulta> dados) {
        this.dados = dados;
    }

    @NonNull
    @Override
    public ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Inflando a View e passando o context
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        view = layoutInflater.inflate(R.layout.activity_consulta_recicle, parent, false);

        ViewHolderCliente holderCliente = new ViewHolderCliente(view, parent.getContext());

        return holderCliente;
    }

    public String dataF(String data){
        String[] a = data.split("/");
        return a[1]+"/"+a[0]+"/"+a[2];
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderCliente holder, int position) {
        if (dados != null && dados.size() > 0) {
            Consulta consulta = dados.get(position);

            //Passando os dados para a View
            Date a = new Date(dataF(consulta.getData()));
            String b = DateFormat.getDateInstance(DateFormat.DEFAULT).format(a);
            holder.txtDataConsulta.setText(b);
            holder.txtNConsulta.setText("vagas: "+consulta.getHorario() + " ");
        }
    }

    @Override
    public int getItemCount() {
        //Tamanho do List
        return dados.size();
    }

    public class ViewHolderCliente extends RecyclerView.ViewHolder {

        public TextView txtDataConsulta;
        public TextView txtNConsulta;

        public ViewHolderCliente(@NonNull View itemView, final Context context) {
            super(itemView);

            //Passando o TextView de referencia
            txtDataConsulta = itemView.findViewById(R.id.txtDataConsulta);
            txtNConsulta = itemView.findViewById(R.id.txtNConsulta);

            itemView.setOnClickListener(v -> {
                if (dados.size() > 0) {

                    Consulta consulta = dados.get(getLayoutPosition());
                    Intent intent = new Intent(context, MarcarConsulta.class);
                    intent.putExtra("Consultas", consulta);
                    context.startActivity(intent);
                }
            });

        }
    }

}

