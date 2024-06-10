package com.kompor.ui.fragment.kelompok;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kompor.R;
import com.kompor.api.model.Kompetisi;
import com.kompor.api.model.kelompok.Kelompok;
import com.kompor.controller.AuthController;
import com.kompor.controller.KelompokController;
import com.kompor.databinding.FragmentKelompokListBinding;
import com.kompor.ui.adapter.KelompokListAdapter;
import com.kompor.ui.adapter.KompetisiListAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class KelompokListFragment extends Fragment {
    FragmentKelompokListBinding binding;
    KelompokController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentKelompokListBinding.inflate(inflater, container, false);

        controller = new ViewModelProvider(this).get(KelompokController.class);

        Bundle bundle = getArguments();

        assert bundle != null;
        String idKompetisi = bundle.getString("idKompetisi");

        binding.fabCreateKelompok.setOnClickListener(v -> {
            controller.createKelompok(idKompetisi, Objects.requireNonNull(AuthController.getLoginInfo(requireActivity())).getId());
        });

        controller.getListKelompok(idKompetisi);

        controller.getListKelompok().observe(getViewLifecycleOwner(), res -> {
            ArrayList<Kelompok> data = res.getResult();

            if (data == null) {
                Toast.makeText(requireActivity(), res.getRes_msg() + "", Toast.LENGTH_SHORT).show();
                return;
            }

            if (data.isEmpty()) {
                binding.tvIsKelompokListAvailable.setVisibility(View.VISIBLE);
                binding.rvKelompokList.setVisibility(View.GONE);
                return;
            }

            setupAdapter(data);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupAdapter(ArrayList<Kelompok> kelompokList) {
        RecyclerView rv = binding.rvKelompokList;

        KelompokListAdapter adapter = new KelompokListAdapter(requireActivity(), kelompokList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());

        layoutManager.setOrientation(RecyclerView.VERTICAL);

        rv.setLayoutManager(layoutManager);

        adapter.setOnItemClickListener((view, data) -> {
            Bundle bundle = new Bundle();
            bundle.putString("idKelompok", data.getidKelompok());

            Navigation.findNavController(view).navigate(R.id.action_kelompokListFragment_to_kelompokDetailsFragment, bundle, null);
        });

        rv.setAdapter(adapter);
    }
}