package com.usjt.beehealthy.Activities.Nutritionist.ui.client;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.usjt.beehealthy.Model.NutritionalPlan;
import com.usjt.beehealthy.Model.NutritionistClient;
import com.usjt.beehealthy.R;

import java.util.List;

public class NutritionalPlanAdapter extends RecyclerView.Adapter<NutritionalPlanAdapter.PlanViewHolder>{

    public List<NutritionalPlan> plans;

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.plan_layout_list, parent, false);
        NutritionalPlanAdapter.PlanViewHolder planHolder = new NutritionalPlanAdapter.PlanViewHolder(view, parent.getContext());
        return planHolder;
    }

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

        public PlanViewHolder(@NonNull View itemView, final Context context){
            super(itemView);
            weekday = itemView.findViewById(R.id.planWeekday);

            itemView.setOnClickListener(v -> {
                NutritionalPlan plan = plans.get(getLayoutPosition());
                Intent intent = new Intent(context, ClientDetails.class);
                intent.putExtra("plan", plan);
                context.startActivity(intent);
            });
        }
    }
}
