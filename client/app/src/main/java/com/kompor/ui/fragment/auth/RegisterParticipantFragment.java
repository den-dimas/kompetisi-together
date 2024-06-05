package com.kompor.ui.fragment.auth;

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

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.kompor.R;
import com.kompor.controller.AuthController;
import com.kompor.databinding.FragmentRegisterParticipantBinding;
import com.kompor.ui.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterParticipantFragment extends Fragment {
    FragmentRegisterParticipantBinding binding;
    AuthController controller;

    String selectedDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterParticipantBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = new ViewModelProvider(requireActivity()).get(AuthController.class);

        binding.tilEmailParticipant.getEditText().addTextChangedListener(Utils.clearError(binding.tilEmailParticipant));
        binding.tilNameParticipant.getEditText().addTextChangedListener(Utils.clearError(binding.tilNameParticipant));
        binding.tilPasswordParticipant.getEditText().addTextChangedListener(Utils.clearError(binding.tilPasswordParticipant));
        binding.tilSekolahParticipant.getEditText().addTextChangedListener(Utils.clearError(binding.tilSekolahParticipant));

        binding.btnTanggalLahir.setOnClickListener(v -> {
            binding.btnTanggalLahir.setText("Tanggal Lahir");
            binding.btnTanggalLahir.setError(null);
            showDatePicker();
        });

        controller.getParticipantInfo().observe(getViewLifecycleOwner(), participant -> {
            if (participant.getResult() == null) {
                String msg = participant.getRes_msg().toLowerCase();
                if (msg.contains("email") || msg.contains("account"))
                    Utils.setInputError(binding.tilEmailParticipant, participant.getRes_msg());

                return;
            }

            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_registerParticipantFragment_to_loginParticipantFragment);
        });

        binding.btnRegisterParticipant.setOnClickListener(v -> {
            String email = binding.tilEmailParticipant.getEditText().getText().toString();
            String password = binding.tilPasswordParticipant.getEditText().getText().toString();
            String nama = binding.tilNameParticipant.getEditText().getText().toString();
            String sekolah = binding.tilSekolahParticipant.getEditText().getText().toString();

//            String email = "kayla@gmail.com";
//            String password = "kayla123";
//            String nama = "Cantik";
//            String sekolah = "Hogwarts";
//            selectedDate = "2000-01-01";

            if (!Utils.isValidEmail(email)) {
                Utils.setInputError(binding.tilEmailParticipant, "Email tidak valid");
                return;
            }

            if (password.length() < 8) {
                Utils.setInputError(binding.tilPasswordParticipant, "Password minimal 8 karakter");
                return;
            }

            if (nama.length() < 3) {
                Utils.setInputError(binding.tilNameParticipant, "Nama minimal 3 karakter");
                return;
            }

            if (selectedDate == null) {
                binding.btnTanggalLahir.setText("Tanggal lahir tidak boleh kosong");
                binding.btnTanggalLahir.setError("Tanggal lahir tidak boleh kosong");
                return;
            }

            if (sekolah.length() < 6) {
                Utils.setInputError(binding.tilSekolahParticipant, "Sekolah minimal 3 karakter");
                return;
            }

            Log.d("Auth", "Email : " + email + "\n" + "Password : " + password + "\n" + "Nama : " + nama + "\n" + "Tanggal Lahir : " + selectedDate + "\n" + "Sekolah : " + sekolah);
            controller.registerParticipant(email, password, nama, selectedDate, sekolah);
        });

        binding.btnGoToLoginParticipant.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_registerParticipantFragment_to_loginParticipantFragment));
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setEnd(calendar.getTimeInMillis());

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Tanggal Lahir")
                .setCalendarConstraints(constraintBuilder.build())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            selectedDate = sdf.format(selection);
            binding.btnTanggalLahir.setText(selectedDate);
        });

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }
}