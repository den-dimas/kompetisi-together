package com.kompor.ui.fragment.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresExtension;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kompor.api.model.response.AuthApiResponse;
import com.kompor.api.model.Penyelenggara;
import com.kompor.controller.AuthController;
import com.kompor.databinding.FragmentPenyelenggaraProfileBinding;
import com.kompor.ui.Utils;
import com.kompor.ui.activity.MainActivity;
import com.kompor.ui.activity.StartingActivity;

import java.util.Base64;
import java.util.Objects;

public class PenyelenggaraProfileFragment extends Fragment {

    FragmentPenyelenggaraProfileBinding binding;
    ActivityResultLauncher<Intent> galleryLauncher;

    AuthController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentPenyelenggaraProfileBinding.inflate(inflater, container, false);

        controller = new ViewModelProvider(this).get(AuthController.class);

        Bundle bundle = getArguments();
        String id = bundle.getString("id");

        changePickedImage();

        binding.ivLogo.setOnClickListener(v -> pickImage());
        binding.btnSaveChanges.setOnClickListener(v -> saveChanges());

        controller.getPenyelenggaraById(id);

        controller.getPenyelenggaraInfo().observe(getViewLifecycleOwner(), observePenyelenggaraData());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnLogout.setOnClickListener(v -> {
            MainActivity.clearPrefs();
            Intent intent = new Intent(requireActivity(), StartingActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }

    @NonNull
    private Observer<AuthApiResponse<Penyelenggara>> observePenyelenggaraData() {
        return penyelenggara -> {
            Penyelenggara data = penyelenggara.getResult();

            if (data == null) {
                return;
            }

            if (data.getLogo() != null && !data.getLogo().isEmpty()) {
                controller.setImage(data.getLogo());
                Glide.with(requireActivity()).load(controller.getImage()).into(binding.ivLogo);
            }

            binding.etEmail.setText(data.getEmail());
            binding.etNama.setText(data.getNama());
            binding.etDeskripsi.setText(data.getDeskripsi());
        };
    }

    private void pickImage() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        galleryLauncher.launch(intent);
    }

    private void changePickedImage() {
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {
           try {
               Uri uri = o.getData().getData();
               binding.ivLogo.setImageURI(uri);

               String img = Utils.uriToBase64(requireActivity(), uri);

               controller.setImage(img);
           } catch (Exception e) {
               Toast.makeText(requireActivity(), "No Image Selected", Toast.LENGTH_SHORT).show();
           }
        });
    }

    private void saveChanges() {
        String nama = Objects.requireNonNull(binding.etNama.getText()).toString();
        String email = Objects.requireNonNull(binding.etEmail.getText()).toString();
        String deskripsi = Objects.requireNonNull(binding.etDeskripsi.getText()).toString();
        String logo = controller.getImage().getValue();

        if (nama.isEmpty()) Utils.setInputError(binding.tilEditNama, "Nama cannot be empty!");
        if (email.isEmpty())
            Utils.setInputError(binding.tilEditEmail, "Email cannot be empty!");
        if (deskripsi.isEmpty())
            Utils.setInputError(binding.tilEditDeskripsi, "Sekolah cannot be empty!");

        boolean valid = Utils.checkEmail(binding.tilEditEmail, email);

        if (nama.isEmpty() || email.isEmpty() || deskripsi.isEmpty() || !valid) return;

        controller.changePenyelenggara(Objects.requireNonNull(AuthController.getLoginInfo(binding.getRoot().getContext())).getId(), email, logo, nama, deskripsi);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}