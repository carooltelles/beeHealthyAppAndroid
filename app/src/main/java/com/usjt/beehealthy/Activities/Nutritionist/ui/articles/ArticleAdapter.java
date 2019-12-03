package com.usjt.beehealthy.Activities.Nutritionist.ui.articles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.usjt.beehealthy.Model.Articles;
import com.usjt.beehealthy.Model.Nutritionist;
import com.usjt.beehealthy.Model.NutritionistClient;
import com.usjt.beehealthy.R;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>{

    public Bundle bundle = new Bundle();
    public List<Articles> articles;
    public RequestQueue requestQueue;
    public View view;

    public ArticleAdapter (List<Articles> consults){ this.articles = consults;  }


    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.articles_layout_list, parent, false);
        ArticleAdapter.ArticleViewHolder articleHolder = new ArticleViewHolder(view, parent.getContext());
        return articleHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {

        if (articles != null && articles.size() > 0) {
            Articles article = articles.get(position);
            holder.nutritionistFullname.setText(article.getNutritionist().getFullname());
            holder.subject.setText(article.getTitle());
        }
    }

    public Articles getItem(int position) { return articles.get(position); }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder{
        TextView nutritionistFullname;
        TextView subject;
        Context context;

        public ArticleViewHolder(@NonNull View itemView, final Context context){
            super(itemView);
            nutritionistFullname = itemView.findViewById(R.id.article_nutritionist_fullname);
            subject =  itemView.findViewById(R.id.article_subject);
            this.context = context;

            itemView.setOnClickListener(v -> {
                Articles article = articles.get(getLayoutPosition());
                Intent intent = new Intent(context, ArticleDetails.class);
                intent.putExtra("article", article);
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
                                    deleteArticle(getItem(getLayoutPosition()).getIdarticle(), context);
                                }
                            }).setNegativeButton("Cancelar", null);

                    AlertDialog alert = builder.create();
                    alert.show();
                    return false;
                }
            });
        }
    }



    public void deleteArticle(Long id, Context context){
        requestQueue = Volley.newRequestQueue(context);
        String url = context.getString(R.string.web_service_url) + "/articles/"+ id;
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
