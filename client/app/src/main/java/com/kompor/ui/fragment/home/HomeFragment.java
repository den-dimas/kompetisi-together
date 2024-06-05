package com.kompor.ui.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.google.android.material.carousel.HeroCarouselStrategy;
import com.kompor.R;
import com.kompor.api.model.Kompetisi;
import com.kompor.controller.KompetisiController;
import com.kompor.databinding.FragmentHomeBinding;
import com.kompor.ui.adapter.CarouselAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    KompetisiController controller;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        controller = new ViewModelProvider(this).get(KompetisiController.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        controller.getPaidKompetisi();

        ArrayList<Kompetisi> kompetisiList = new ArrayList<>();

        controller.getListKompetisi().observe(getViewLifecycleOwner(), res -> {
            ArrayList<Kompetisi> data = res.getResult();

            if (data != null) {
                kompetisiList.addAll(data);
            }
        });

        setupCarousel(kompetisiList);

        kategoriClick(binding.btnKategoriGame, "GAME");
        kategoriClick(binding.btnKategoriArt, "KESENIAN");
        kategoriClick(binding.btnKategoriIoT, "INTERNET OF THINGS");
        kategoriClick(binding.btnKategoriDebat, "DEBAT");
        kategoriClick(binding.btnKategoriUIUX, "UI/UX");
        kategoriClick(binding.btnKategoriRobotik, "ROBOTIK");
        kategoriClick(binding.btnKategoriProgramming, "SOFTWARE");
        kategoriClick(binding.btnKategoriPaper, "ESAI");

        return binding.getRoot();
    }

    private void kategoriClick(Button button, String kategori) {
        button.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("kategori", kategori.toUpperCase());

            Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_kompetisi_kategori, bundle, null);
        });
    }

    private void setupCarousel(ArrayList<Kompetisi> kompetisiList) {
        RecyclerView rv = binding.rvCarousel;

        CarouselAdapter adapter = new CarouselAdapter(requireActivity(), kompetisiList);
        CarouselSnapHelper snapHelper = new CarouselSnapHelper();
        CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager();

        carouselLayoutManager.setCarouselStrategy(new HeroCarouselStrategy());
        carouselLayoutManager.setCarouselAlignment(CarouselLayoutManager.ALIGNMENT_CENTER);

        adapter.setOnItemClickListener((view, source) -> {
            Bundle bundle = new Bundle();
            bundle.putString("source", source.getFoto());
            bundle.putString("id", source.getId_kompetisi());

            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_frKompetisiDetails, bundle, null);
        });

        rv.setLayoutManager(carouselLayoutManager);
        rv.setAdapter(adapter);

        snapHelper.attachToRecyclerView(rv);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}