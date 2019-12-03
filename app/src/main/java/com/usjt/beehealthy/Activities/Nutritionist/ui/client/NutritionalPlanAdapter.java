package com.usjt.beehealthy.Activities.Nutritionist.ui.client;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.usjt.beehealthy.Model.NutritionalPlan;
import com.usjt.beehealthy.R;

import java.util.List;

public class NutritionalPlanAdapter extends RecyclerView.Adapter<NutritionalPlanAdapter.PlanViewHolder>{

    public List<NutritionalPlan> plans;
    public NutritionalPlan plan;
    public RequestQueue requestQueue;

    public NutritionalPlanAdapter (List<NutritionalPlan> plans){ this.plans = plans;  }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.plan_layout_list, parent, false);
        NutritionalPlanAdapter.PlanViewHolder planHolder = new NutritionalPlanAdapter.PlanViewHolder(view, parent.getContext());
        return planHolder;
    }


    public NutritionalPlan getItem(int position) { return plans.get(position); }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {

        if (plans != null && plans.size() > 0) {
            NutritionalPlan plan = plans.get(position);
            holder.weekday.setText(plan.getWeekDay());
        }
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }


    public class PlanViewHolder extends RecyclerView.ViewHolder{
        TextView weekday;
        CardView cardPlan;

        public PlanViewHolder(@NonNull View itemView, final Context context){
            super(itemView);
            weekday = itemView.findViewById(R.id.planWeekday);
            cardPlan = itemView.findViewById(R.id.plan_layout_list);


            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, NutritionistPlanDetail.class);
                plan = plans.get(getLayoutPosition());
                intent.putExtra("plan", plan);
                context.startActivity(intent);
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Você quer apagar?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deletePlan(getItem(getLayoutPosition()).getIdplan(), context);
                                }
                            }).setNegativeButton("Cancelar", null);

                    AlertDialog alert = builder.create();
                    alert.show();
                    return false;
                }
            });
        }
    }


    public void deletePlan(Long id, Context context){
        requestQueue = Volley.newRequestQueue(context);
        String url = context.getString(R.string.web_service_url) + "/plan/"+ id;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.DELETE, url, null,
                (response) -> {
                    try {
                        Toast.makeText(
                                context,
                                "Registrado com sucesso.",
                                Toast.LENGTH_SHORT
                        ).show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                },
                (exception) -> {
                    Toast.makeText(
                            context,
                            "Registrado com sucesso.",
                            Toast.LENGTH_SHORT
                    ).show();
                    exception.printStackTrace();
                });

        requestQueue.add(request);
    }
}
