package com.kompor.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.kompor.R;
import com.kompor.controller.AuthController;
import com.kompor.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

//        clearPrefs();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public static String getToken() {
        SharedPreferences sp = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE);

        return sp.getString("token", null);
    }

    public static void clearPrefs() {
        SharedPreferences.Editor sp = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE).edit();

        sp.clear().apply();
    }
}