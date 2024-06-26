package com.kompor.ui.fragment.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.kompor.R;
import com.kompor.api.model.LoginInfo;
import com.kompor.controller.AuthController;
import com.kompor.databinding.FragmentWelcomeBinding;
import com.kompor.ui.activity.MainActivity;

import java.util.ArrayList;

public class WelcomeFragment extends Fragment {
    FragmentWelcomeBinding binding;
    AuthController controller;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);

        controller = new ViewModelProvider(requireActivity()).get(AuthController.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoginInfo loginInfo = AuthController.getLoginInfo(requireActivity());

        if (loginInfo != null && loginInfo.getId() != null && loginInfo.getToken() != null) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);

            if (getActivity() != null)
                getActivity().finish();
        }

        binding.btnContinueToParticipant.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_welcomeFragment_to_registerParticipantFragment));
        binding.btnContinueToPenyelenggara.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_welcomeFragment_to_registerPenyelenggaraFragment));
    }
}