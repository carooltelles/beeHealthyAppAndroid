package com.usjt.beehealthy.Activities.Nutritionist.ui.articles;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.usjt.beehealthy.Activities.Nutritionist.ui.client.ClientAdapter;
import com.usjt.beehealthy.Activities.Nutritionist.ui.client.NutritionalPlanAddActivity;
import com.usjt.beehealthy.Model.Articles;
import com.usjt.beehealthy.Model.Nutritionist;
import com.usjt.beehealthy.R;
import com.usjt.beehealthy.Utilities.Util;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ArticleFragment extends Fragment {

    public List<Articles> articles;
    private RequestQueue requestQueue;
    public RecyclerView articleRecycler;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_article, container, false);

        try{
            articles = new ArrayList<>();
            Nutritionist nutritionist = (Nutritionist) getActivity().getIntent().getExtras().get("Nutritionist");
            articleRecycler = root.findViewById(R.id.articles_list_recycler);
            getArticles();


            FloatingActionButton fab = root.findViewById(R.id.article_add);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(view.getContext(), ArticleAddActivity.class);
                        intent.putExtra("nutritionist", nutritionist);
                        startActivity(intent);
                    } catch (Exception e) {
                        throw e;
                    }

                }
            });


            return root;
        }catch(Exception e){
            throw e;
        }


    }



    public void getArticles() {

        requestQueue = Volley.newRequestQueue(getActivity());
        String url = getString(R.string.web_service_url) + "/articles/";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                (response) -> {

                    try {
                        articles = Util.fillArticles(response);
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


    public void setList(){
        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        articleRecycler.setLayoutManager(linear);
        ArticleAdapter adapter = new ArticleAdapter(articles);
        adapter.notifyDataSetChanged();
        articleRecycler.setAdapter(adapter);
    }

}