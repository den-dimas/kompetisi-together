package com.kompor.ui.fragment.Auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kompor.controller.AuthController;
import com.kompor.databinding.FragmentLoginParticipantBinding;
import com.kompor.ui.Utils;

public class LoginParticipantFragment extends Fragment {
    FragmentLoginParticipantBinding binding;
    AuthController controller;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginParticipantBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = new ViewModelProvider(requireActivity()).get(AuthController.class);

        binding.btnGoToRegister.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        binding.tilEmail.getEditText().addTextChangedListener(Utils.clearError((binding.tilEmail)));
        binding.tilPassword.getEditText().addTextChangedListener(Utils.clearError(binding.tilPassword));

        controller.getParticipantInfo().observe(getViewLifecycleOwner(), participant -> {
            if (participant.getResult() == null) {
                if (participant.getRes_msg().contains("Account"))
                    Utils.setInputError(binding.tilEmail, participant.getRes_msg());

                if (participant.getRes_msg().contains("password"))
                    Utils.setInputError(binding.tilPassword, participant.getRes_msg());
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            String EMAIL = binding.tilEmail.getEditText().getText().toString();
            String PASSWORD = binding.tilPassword.getEditText().getText().toString();

            if (!Utils.checkEmail(binding.tilEmail, EMAIL)) {
                return;
            }

            if (PASSWORD.length() < 8) {
                Utils.setInputError(binding.tilPassword, "Password minimal 8 karakter!");
                return;
            }

            controller.loginParticipant(EMAIL, PASSWORD);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}