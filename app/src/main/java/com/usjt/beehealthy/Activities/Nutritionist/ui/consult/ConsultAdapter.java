package com.usjt.beehealthy.Activities.Nutritionist.ui.consult;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.usjt.beehealthy.Model.Consult;
import com.usjt.beehealthy.R;
import java.util.List;

public class ConsultAdapter extends ArrayAdapter<Consult> {

    private List<Consult> consults;
    private Context context;

    public ConsultAdapter (List<Consult> consults, Context context){
        super(context, R.layout.consult_layout_list, consults);
        this.consults = consults;
        this.context = context;
    }

    @Override public int getCount() { return consults.size(); }

    @Override public Consult getItem(int position) { return consults.get(position); }

    @Override public long getItemId(int position) { return consults.get(position).getIdconsult(); }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.consult_layout_list, parent, false);
        Consult consult = consults.get(position);

        TextView patientName = (TextView) view.findViewById(R.id.patient_name);
        TextView consultDate = (TextView) view.findViewById(R.id.consult_date);
        TextView consultPlace = (TextView) view.findViewById(R.id.consult_place);

        patientName.setText(consult.getPatient().getFullname());
        consultDate.setText(consult.getDate());
        consultPlace.setText(consult.getPlace());

        return view;
    }
}
