package com.kompor.ui.fragment.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kompor.R;
import com.kompor.api.model.Kompetisi;
import com.kompor.controller.AuthController;
import com.kompor.databinding.FragmentDashboardBinding;
import com.kompor.ui.adapter.KompetisiListAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;
    AuthController controller;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        controller = new ViewModelProvider(this).get(AuthController.class);

        String role = Objects.requireNonNull(AuthController.getLoginInfo(requireActivity())).getRole().toLowerCase();

        if (role.equals("penyelenggara")) {
            binding.fabCreateKompetisi.setVisibility(View.VISIBLE);

            controller.getPenyelenggaraKompetisi(Objects.requireNonNull(AuthController.getLoginInfo(requireActivity())).getId());
        } else {
            binding.fabCreateKompetisi.setVisibility(View.GONE);

            controller.getParticipantKompetisi(Objects.requireNonNull(AuthController.getLoginInfo(requireActivity())).getId());
        }

        binding.fabCreateKompetisi.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_dashboard_to_createKompetisiFragment);
        });

        controller.getKompetisiList().observe(getViewLifecycleOwner(), res -> {
            ArrayList<Kompetisi> data = res.getResult();

            if (data == null) {
                Toast.makeText(requireActivity(), res.getRes_msg(), Toast.LENGTH_SHORT).show();
                return;
            }

            if (data.isEmpty()) {
                binding.tvIsKompetisiAvailable.setVisibility(View.VISIBLE);
                binding.rvMyKompetisi.setVisibility(View.GONE);
                return;
            }

            setupAdapter(data);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupAdapter(ArrayList<Kompetisi> kompetisiList) {
        RecyclerView rv = binding.rvMyKompetisi;

        KompetisiListAdapter adapter = new KompetisiListAdapter(requireActivity(), kompetisiList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(), 2);

        layoutManager.setOrientation(RecyclerView.VERTICAL);

        rv.setLayoutManager(gridLayoutManager);

        adapter.setOnItemClickListener((view, source) -> {
            Bundle bundle = new Bundle();
            bundle.putString("source", source.getFoto());
            bundle.putString("id", source.getId_kompetisi());

            Navigation.findNavController(view).navigate(R.id.action_navigation_dashboard_to_frKompetisiDetails, bundle, null);
        });

        rv.setAdapter(adapter);
    }

}