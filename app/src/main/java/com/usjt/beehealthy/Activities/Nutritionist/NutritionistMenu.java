package com.usjt.beehealthy.Activities.Nutritionist;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.usjt.beehealthy.Activities.Nutritionist.ui.consult.ConsultFragment;
import com.usjt.beehealthy.Activities.Nutritionist.ui.home.HomeFragment;
import com.usjt.beehealthy.Activities.Nutritionist.ui.notifications.NotificationsFragment;
import com.usjt.beehealthy.Activities.Nutritionist.ui.profile.ProfileFragment;
import com.usjt.beehealthy.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class NutritionistMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritionist_menu);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.navigation_consults:
                        selectedFragment = new ConsultFragment();
                        break;
                    case R.id.navigation_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navigation_notifications:
                        selectedFragment = new NotificationsFragment();
                        break;
                    case R.id.navigation_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                }

                getSupportFragmentManager().
                        beginTransaction().replace(R.id.nutritionist_container,
                        selectedFragment).commit();

                return true;
            };


}
