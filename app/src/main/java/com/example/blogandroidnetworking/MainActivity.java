package com.example.blogandroidnetworking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.blogandroidnetworking.model.dto.UserDto;
import com.example.blogandroidnetworking.ui.viewmodel.UserLoggedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.blogandroidnetworking.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    private UserLoggedViewModel userLoggedViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userLoggedViewModel = new ViewModelProvider(this).get(UserLoggedViewModel.class);
        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("username");
            String id = intent.getStringExtra("id");
            String type = intent.getStringExtra("type");
            String avatar = intent.getStringExtra("avatar");
            UserDto userDto = new UserDto();
            userDto.setAvatar(avatar);
            userDto.setId(id);
            userDto.setType(type);
            userDto.setName(username);
            userLoggedViewModel.setData(userDto);
        }
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_new_post,
                R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_activity_main);

        NavigationUI.setupActionBarWithNavController(this,
                navController,
                appBarConfiguration);

        NavigationUI.setupWithNavController(binding.navView,
                navController);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

}