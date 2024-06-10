package com.kompor.ui.fragment.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kompor.api.model.Participant;
import com.kompor.controller.AuthController;
import com.kompor.databinding.FragmentParticipantProfileBinding;
import com.kompor.ui.Utils;
import com.kompor.ui.activity.MainActivity;
import com.kompor.ui.activity.StartingActivity;

import java.util.Objects;

public class ParticipantProfileFragment extends Fragment {
    FragmentParticipantProfileBinding binding;
    AuthController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentParticipantProfileBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();

        assert bundle != null;
        String id = bundle.getString("id");

        controller = new ViewModelProvider(this).get(AuthController.class);

        controller.getParticipantById(id);

        controller.getParticipantInfo().observe(getViewLifecycleOwner(), p -> {
            Participant data = p.getResult();

            if (data == null)
                return;

            binding.etNama.setText(data.getNama());
            binding.etEmail.setText(data.getEmail());
            binding.etSekolah.setText(data.getSekolah());
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupListener();

        binding.btnLogout.setOnClickListener(v -> {
            MainActivity.clearPrefs();
            Intent intent = new Intent(requireActivity(), StartingActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        binding.btnSaveChanges.setOnClickListener(handleSaveChanges(binding, controller));
    }

    @NonNull
    private static View.OnClickListener handleSaveChanges(FragmentParticipantProfileBinding binding, AuthController controller) {
        return v -> {
            String nama = Objects.requireNonNull(binding.etNama.getText()).toString();
            String email = Objects.requireNonNull(binding.etEmail.getText()).toString();
            String sekolah = Objects.requireNonNull(binding.etSekolah.getText()).toString();

            if (nama.isEmpty()) Utils.setInputError(binding.tilEditNama, "Nama cannot be empty!");
            if (email.isEmpty())
                Utils.setInputError(binding.tilEditEmail, "Email cannot be empty!");
            if (sekolah.isEmpty())
                Utils.setInputError(binding.tilEditDeskripsi, "Sekolah cannot be empty!");

            boolean valid = Utils.checkEmail(binding.tilEditEmail, email);

            if (nama.isEmpty() || email.isEmpty() || sekolah.isEmpty() || !valid) return;

            controller.changeParticipant(Objects.requireNonNull(AuthController.getLoginInfo(binding.getRoot().getContext())).getId(), nama, email, sekolah);
        };
    }

    private void setupListener() {
        binding.etNama.addTextChangedListener(Utils.clearError(binding.tilEditNama));
        binding.etEmail.addTextChangedListener(Utils.clearError(binding.tilEditEmail));
        binding.etSekolah.addTextChangedListener(Utils.clearError(binding.tilEditDeskripsi));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}