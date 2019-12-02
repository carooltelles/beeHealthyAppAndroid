package com.usjt.beehealthy.Activities.Nutritionist.ui.articles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.usjt.beehealthy.Model.Articles;
import com.usjt.beehealthy.Model.Nutritionist;
import com.usjt.beehealthy.Model.NutritionistClient;
import com.usjt.beehealthy.R;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>{

    public Bundle bundle = new Bundle();
    public List<Articles> articles;

    public ArticleAdapter (List<Articles> consults){ this.articles = consults;  }


    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.articles_layout_list, parent, false);
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

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder{
        TextView nutritionistFullname;
        TextView subject;

        public ArticleViewHolder(@NonNull View itemView, final Context context){
            super(itemView);
            nutritionistFullname = itemView.findViewById(R.id.article_nutritionist_fullname);
            subject =  itemView.findViewById(R.id.article_subject);

            itemView.setOnClickListener(v -> {
                Articles article = articles.get(getLayoutPosition());
                Intent intent = new Intent(context, ArticleDetails.class);
                intent.putExtra("article", article);
                context.startActivity(intent);
            });
        }
    }
}
