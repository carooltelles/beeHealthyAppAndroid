package com.usjt.beehealthy.Activities.Nutritionist.ui.consult;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.usjt.beehealthy.Model.Consult;
import com.usjt.beehealthy.R;
import java.util.List;

public class ConsultAdapter extends RecyclerView.Adapter<ConsultAdapter.ConsultViewHolder> {

    private List<Consult> consults;

    public ConsultAdapter (List<Consult> consults){ this.consults = consults;  }


    @NonNull
    @Override
    public ConsultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.consult_layout_list, parent, false);
        ConsultAdapter.ConsultViewHolder consultHolder = new ConsultViewHolder(view, parent.getContext());
        return consultHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultViewHolder holder, int position) {


        if (consults != null && consults.size() > 0) {
            Consult consult = consults.get(position);
            holder.date.setText(consult.getDate());
            holder.patientName.setText(consult.getPatient().getFullname());
            holder.place.setText(consult.getPlace());
        }
    }

    @Override public long getItemId(int position) { return consults.get(position).getIdconsult(); }

    public int getItemCount() { return consults.size(); }

    public Consult getItem(int position) { return consults.get(position); }


    public class ConsultViewHolder extends RecyclerView.ViewHolder {

        public TextView place;
        public TextView patientName;
        public TextView date;

        public ConsultViewHolder(@NonNull View itemView, final Context context){
            super(itemView);
            place = itemView.findViewById(R.id.consult_place);
            patientName =  itemView.findViewById(R.id.patient_name);
            date =  itemView.findViewById(R.id.consult_date);

        }
    }
}
