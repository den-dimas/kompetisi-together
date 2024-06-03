package com.kompor.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.kompor.R;
import com.kompor.databinding.ActivityStartingBinding;

public class StartingActivity extends AppCompatActivity {

    public NavController authNavController;

    private ActivityStartingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashScreen.installSplashScreen(this);

        this.binding = ActivityStartingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment authNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_auth_host);

        authNavController = authNavHostFragment.getNavController();
    }
}