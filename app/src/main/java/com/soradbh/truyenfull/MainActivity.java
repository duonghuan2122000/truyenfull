package com.soradbh.truyenfull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.updatestory_dest, R.id.category_dest, R.id.search_dest).build();
        final BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()){
                    case R.id.updatestory_dest:
                    case R.id.category_dest:
                    case R.id.search_dest:
                        navigationView.setVisibility(View.VISIBLE);
                        break;
                    default:
                        navigationView.setVisibility(View.GONE);
                        break;

                }
                switch (destination.getId()){
                    case R.id.search_dest:
                        getSupportActionBar().hide();
                        break;
                    default:
                        getSupportActionBar().show();
                        break;
                }
            }
        });
    }
}
