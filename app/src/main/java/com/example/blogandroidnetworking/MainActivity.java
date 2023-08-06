package com.example.blogandroidnetworking;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.blogandroidnetworking.data.remote.newfeed.service.NewFeedApiImpl;
import com.example.blogandroidnetworking.data.remote.newfeed.service.NewFeedsApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.blogandroidnetworking.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";

    private ActivityMainBinding binding;
    private NewFeedsApi newFeedsApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Main");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        newFeedsApi = new NewFeedApiImpl(this);
        newFeedsApi.getNewPosts(response -> {
            Log.d(TAG, "onCreate: " + response.toString());
        }, error -> {
            Log.d(TAG, "onCreate: " + error.toString());
        });
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_main);

        NavigationUI.setupActionBarWithNavController(this,
                navController,
                appBarConfiguration);

        NavigationUI.setupWithNavController(binding.navView,
                navController);
    }

}