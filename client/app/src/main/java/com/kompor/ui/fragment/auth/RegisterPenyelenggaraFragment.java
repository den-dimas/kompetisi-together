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

import com.kompor.R;
import com.kompor.controller.AuthController;
import com.kompor.databinding.FragmentRegisterPenyelenggaraBinding;
import com.kompor.ui.Utils;

public class RegisterPenyelenggaraFragment extends Fragment {
    FragmentRegisterPenyelenggaraBinding binding;

    AuthController controller;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterPenyelenggaraBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = new ViewModelProvider(requireActivity()).get(AuthController.class);


        binding.tilEmailPenyelenggara.getEditText().addTextChangedListener(Utils.clearError(binding.tilEmailPenyelenggara));
        binding.tilNamePenyelenggara.getEditText().addTextChangedListener(Utils.clearError(binding.tilNamePenyelenggara));
        binding.tilPasswordPenyelenggara.getEditText().addTextChangedListener(Utils.clearError(binding.tilPasswordPenyelenggara));
        binding.tilDeskripsiPenyelenggara.getEditText().addTextChangedListener(Utils.clearError(binding.tilDeskripsiPenyelenggara));

        controller.getPenyelenggaraInfo().observe(getViewLifecycleOwner(), participant -> {
            if (participant.getResult() == null) {
                String msg = participant.getRes_msg().toLowerCase();
                if (msg.contains("email") || msg.contains("account"))
                    Utils.setInputError(binding.tilEmailPenyelenggara, participant.getRes_msg());
            }
        });

        binding.btnRegisterPenyelenggara.setOnClickListener(v -> {
            String email = binding.tilEmailPenyelenggara.getEditText().getText().toString();
            String password = binding.tilPasswordPenyelenggara.getEditText().getText().toString();
            String nama = binding.tilNamePenyelenggara.getEditText().getText().toString();
            String deskripsi = binding.tilDeskripsiPenyelenggara.getEditText().getText().toString();

            if (!Utils.isValidEmail(email)) {
                Utils.setInputError(binding.tilEmailPenyelenggara, "Email tidak valid");
                return;
            }

            if (password.length() < 8) {
                Utils.setInputError(binding.tilPasswordPenyelenggara, "Password minimal 8 karakter");
                return;
            }

            if (nama.length() < 3) {
                Utils.setInputError(binding.tilNamePenyelenggara, "Nama minimal 3 karakter");
                return;
            }

            if (deskripsi.length() < 8) {
                Utils.setInputError(binding.tilDeskripsiPenyelenggara, "Deskripsi minimal 8 karakter");
                return;
            }

            Log.d("Auth", "Email : " + email + "\n" + "Password : " + password + "\n" + "Nama : " + nama + "\n" + "Deskripsi : " + deskripsi);
            controller.registerPenyelenggara(email, password, nama, deskripsi);
        });

        binding.btnGoToLoginPenyelenggara.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_registerPenyelenggaraFragment_to_loginPenyelenggaraFragment));
    }
}