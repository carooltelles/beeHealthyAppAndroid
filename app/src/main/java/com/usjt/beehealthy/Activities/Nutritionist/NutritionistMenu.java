package com.usjt.beehealthy.Activities.Nutritionist;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.usjt.beehealthy.Activities.Nutritionist.ui.consult.ConsultFragment;
import com.usjt.beehealthy.Activities.Nutritionist.ui.articles.ArticleFragment;
import com.usjt.beehealthy.Activities.Nutritionist.ui.client.ClientsFragment;
import com.usjt.beehealthy.Activities.Nutritionist.ui.profile.ProfileFragment;
import com.usjt.beehealthy.Model.Nutritionist;
import com.usjt.beehealthy.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class NutritionistMenu extends AppCompatActivity {

    public FragmentManager fragmentManager;
    public Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritionist_menu);
        fragmentManager =  getSupportFragmentManager();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Bundle bundle = new Bundle();

        bundle = getIntent().getExtras();

        Nutritionist nutritionist = (Nutritionist) bundle.getSerializable("Nutritionist");

        System.out.println(nutritionist.getPassword());
        fragmentManager.beginTransaction().replace(R.id.nutritionist_container,
                new ArticleFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.navigation_consults:
                        selectedFragment = new ConsultFragment();
                        break;
                    case R.id.navigation_articles:
                        selectedFragment = new ArticleFragment();
                        break;
                    case R.id.navigation_client:
                        selectedFragment = new ClientsFragment();
                        break;
                    case R.id.navigation_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                }


                fragmentManager.beginTransaction().replace(R.id.nutritionist_container,
                        selectedFragment).commit();

                return true;
            };


}
