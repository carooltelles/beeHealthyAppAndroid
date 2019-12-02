package com.usjt.beehealthy.Activities.Nutritionist.ui.articles;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import com.usjt.beehealthy.Model.Articles;
import com.usjt.beehealthy.R;
import com.usjt.beehealthy.Utilities.Util;

public class ArticleAddActivity extends AppCompatActivity {

    private RequestQueue requestQueue;]
    private Articles article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_add);

        FloatingActionButton fab = findViewById(R.id.article_save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }





    public void addArticle() {

        requestQueue = Volley.newRequestQueue(this);
        String url = getString(R.string.web_service_url) + "/articles/";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                (response) -> {

                    try {
                        article = Util.fillArticles(response);
                        setList();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                },
                (exception) -> {
                    Toast.makeText(
                            getContext(),
                            getString(R.string.connect_error),
                            Toast.LENGTH_SHORT
                    ).show();
                    exception.printStackTrace();
                });

        requestQueue.add(request);
    }

}
