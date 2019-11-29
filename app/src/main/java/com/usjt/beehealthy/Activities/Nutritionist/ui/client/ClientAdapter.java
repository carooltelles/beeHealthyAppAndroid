package com.usjt.beehealthy.Activities.Nutritionist.ui.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.usjt.beehealthy.Model.Nutritionist;
import com.usjt.beehealthy.Model.NutritionistClient;
import com.usjt.beehealthy.R;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder>{

    public Bundle bundle = new Bundle();
    public List<NutritionistClient> clients;

    public ClientAdapter (List<NutritionistClient> consults){ this.clients = consults;  }


    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.client_layout_list, parent, false);
        ClientAdapter.ClientViewHolder clientHolder = new ClientViewHolder(view, parent.getContext());
        return clientHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {

        if (clients != null && clients.size() > 0) {
            NutritionistClient client = clients.get(position);
            holder.email.setText(client.getPatient().getEmail());
            holder.fullname.setText(client.getPatient().getFullname());
        }
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder{
        TextView fullname;
        TextView email;

        public ClientViewHolder(@NonNull View itemView, final Context context){
            super(itemView);
            fullname = itemView.findViewById(R.id.client_fullname);
            email =  itemView.findViewById(R.id.client_email);

            itemView.setOnClickListener(v -> {
                NutritionistClient client = clients.get(getLayoutPosition());
                Intent intent = new Intent(context, ClientDetails.class);
                intent.putExtra("client", client);
                context.startActivity(intent);
            });
        }
    }
}
