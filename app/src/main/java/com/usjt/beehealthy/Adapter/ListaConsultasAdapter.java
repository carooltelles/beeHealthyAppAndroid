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

import br.com.gabriel.firebase.ConsultasPaciente;
import br.com.gabriel.firebase.PerfilNutricionista;
import br.com.gabriel.firebase.R;
import br.com.gabriel.firebase.model.ConsultaMarcada;

public class ListaConsultasAdapter extends RecyclerView.Adapter<ListaConsultasAdapter.ViewHolderCliente> {

    private List<ConsultaMarcada> dados;

    public ListaConsultasAdapter(List<ConsultaMarcada> dados) {
        this.dados = dados;
    }

    @NonNull
    @Override
    public ListaConsultasAdapter.ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Inflando a View e passando o context
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.lista_consultas, parent, false);

        ListaConsultasAdapter.ViewHolderCliente holderCliente = new ViewHolderCliente(view, parent.getContext());

        return holderCliente;
    }

    public String dataF(String data){
        String[] a = data.split("/");
        return a[1]+"/"+a[0]+"/"+a[2];
    }

    @Override
    public void onBindViewHolder(@NonNull ListaConsultasAdapter.ViewHolderCliente holder, int position) {

        if (dados != null && dados.size() > 0) {
            ConsultaMarcada consultaMarcada = dados.get(position);
            Date a = new Date(dataF(consultaMarcada.getData()));
            String b = DateFormat.getDateInstance(DateFormat.FULL).format(a);
            //Passando os dados para a View
            holder.LC_Nutricionista.setText(consultaMarcada.getNutricionist().getNome());
            holder.LC_Data.setText(b);
            holder.LC_Local.setText(consultaMarcada.getLocal());

    }
    }

    @Override
    public int getItemCount() {
        //Tamanho do List
        return dados.size();
    }

    public class ViewHolderCliente extends RecyclerView.ViewHolder {

        public TextView LC_Nutricionista;
        public TextView LC_Data;
        public TextView LC_Local;

        public ViewHolderCliente(@NonNull View itemView, final Context context) {
            super(itemView);

            //Passando o TextView de referencia
            LC_Nutricionista = itemView.findViewById(R.id.LC_Nutriciosta);
            LC_Data =  itemView.findViewById(R.id.LC_Data);
            LC_Local =  itemView.findViewById(R.id.LC_Local);

            itemView.setOnClickListener(v -> {
                if (dados.size() > 0) {

                    ConsultaMarcada consultaMarcada= dados.get(getLayoutPosition());
                    Intent intent = new Intent(context, ConsultasPaciente.class);
                    intent.putExtra("Consulta", consultaMarcada);
                    context.startActivity(intent);

                }
            });
        }
    }
}
