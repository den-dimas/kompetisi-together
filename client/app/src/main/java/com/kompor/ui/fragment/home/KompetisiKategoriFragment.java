package com.kompor.ui.fragment.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kompor.R;
import com.kompor.api.model.Kompetisi;
import com.kompor.controller.KompetisiController;
import com.kompor.databinding.FragmentKompetisiKategoriBinding;
import com.kompor.ui.adapter.KompetisiListAdapter;

import java.util.ArrayList;

public class KompetisiKategoriFragment extends Fragment {
    KompetisiController controller;
    FragmentKompetisiKategoriBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        controller = new ViewModelProvider(this).get(KompetisiController.class);

        binding = FragmentKompetisiKategoriBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        String kategori = bundle.getString("kategori");

        Log.d("KATEGORI", kategori + "");

        ArrayList<Kompetisi> kompetisiList = new ArrayList<>();

        controller.getListKompetisi().observe(getViewLifecycleOwner(), res -> {
            ArrayList<Kompetisi> data = res.getResult();

            Log.d("DATA", data.get(0).getFoto() + "");

            if (data != null) {
                setupAdapter(data);
                kompetisiList.addAll(data);
            }
        });

        controller.getKompetisiByKategori(kategori);

        return binding.getRoot();
    }


    private void setupAdapter(ArrayList<Kompetisi> kompetisiList) {
        RecyclerView rv = binding.rvKompetisiKategori;

        KompetisiListAdapter adapter = new KompetisiListAdapter(requireActivity(), kompetisiList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());

        layoutManager.setOrientation(RecyclerView.VERTICAL);

        rv.setLayoutManager(layoutManager);

        adapter.setOnItemClickListener((view, source) -> {
            Bundle bundle = new Bundle();
            bundle.putString("source", source.getFoto());
            bundle.putString("id", source.getId_kompetisi());

            Navigation.findNavController(view).navigate(R.id.action_navigation_kompetisi_kategori_to_frKompetisiDetails, bundle, null);
        });

        rv.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}