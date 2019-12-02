package com.usjt.beehealthy.Activities.Nutritionist.ui.articles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.usjt.beehealthy.Model.Articles;
import com.usjt.beehealthy.R;

public class ArticleDetails extends AppCompatActivity {

    public TextView articleTitle;
    public TextView articleText;
    public TextView articleNutritionist;
    Bundle bundle = new Bundle();
    public Articles article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        bundle = getIntent().getExtras();
        article = (Articles) bundle.getSerializable("article");

        initComponents();
        fillFields();
    }



    public void initComponents(){
        articleTitle = findViewById(R.id.article_title_text_detail);
        articleText = findViewById(R.id.article_text_text_detail);
        articleNutritionist = findViewById(R.id.article_nutritionist_fullname_text_detail);
    }

    public void fillFields(){
        articleTitle.setText(article.getTitle());
        articleText.setText(article.getText());
        articleNutritionist.setText(article.getNutritionist().getFullname());
    }
}
