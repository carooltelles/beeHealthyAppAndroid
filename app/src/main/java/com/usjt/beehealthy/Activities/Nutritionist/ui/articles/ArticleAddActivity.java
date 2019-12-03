package com.usjt.beehealthy.Activities.Nutritionist.ui.articles;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.usjt.beehealthy.Model.Articles;
import com.usjt.beehealthy.Model.Nutritionist;
import com.usjt.beehealthy.R;
import com.usjt.beehealthy.Utilities.Util;

import org.json.JSONObject;

public class ArticleAddActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    public Articles article;
    public Nutritionist nutritionist;
    public EditText title, text;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_add);
        bundle = getIntent().getExtras();
        nutritionist = (Nutritionist) bundle.getSerializable("nutritionist");
        initComponents();


        FloatingActionButton fab = findViewById(R.id.article_save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addArticle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void initComponents(){
        title = findViewById(R.id.article_title_text_add);
        text = findViewById(R.id.article_text_text_add);
    }



    public void addArticle() throws Exception{
        JSONObject articleObj = Util.articleObj(title.getText().toString(), text.getText().toString(), nutritionist.getIduser());
        requestQueue = Volley.newRequestQueue(this);
        String url = getString(R.string.web_service_url) + "/articles/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, articleObj,
                (response) -> {

                    try {
                        article = Util.getArticle(response);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                },
                (exception) -> {
                    Toast.makeText(
                            this,
                            getString(R.string.connect_error),
                            Toast.LENGTH_SHORT
                    ).show();
                    exception.printStackTrace();
                });

        requestQueue.add(request);
    }

}
