package com.kompor.ui.fragment.kelompok;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kompor.api.model.kelompok.KelompokDetails;
import com.kompor.controller.AuthController;
import com.kompor.controller.KelompokController;
import com.kompor.databinding.FragmentKelompokDetailsBinding;
import com.kompor.ui.adapter.AnggotaKelompokListAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class KelompokDetailsFragment extends Fragment {
    FragmentKelompokDetailsBinding binding;
    KelompokController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentKelompokDetailsBinding.inflate(inflater, container, false);

        controller = new ViewModelProvider(this).get(KelompokController.class);

        Bundle bundle = getArguments();

        assert bundle != null;
        String idKelompok = bundle.getString("idKelompok");

        binding.fabDaftarKelompok.setOnClickListener(v -> {
            controller.daftarKelompok(idKelompok, Objects.requireNonNull(AuthController.getLoginInfo(requireActivity())).getId());
        });

        controller.getAnggotaKelompok(idKelompok);

        controller.getListAnggota().observe(getViewLifecycleOwner(), res -> {
            ArrayList<KelompokDetails> data = res.getResult();

            if (data == null) {
                Toast.makeText(requireActivity(), res.getRes_msg(), Toast.LENGTH_SHORT).show();
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

    private void setupAdapter(ArrayList<KelompokDetails> data) {
        RecyclerView rv = binding.rvAnggotaKelompokList;

        AnggotaKelompokListAdapter adapter = new AnggotaKelompokListAdapter(requireActivity(), data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());

        layoutManager.setOrientation(RecyclerView.VERTICAL);

        rv.setLayoutManager(layoutManager);

        rv.setAdapter(adapter);
    }
}