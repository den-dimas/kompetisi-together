package com.kompor.ui.fragment.kompetisi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.kompor.R;
import com.kompor.api.model.Kompetisi;
import com.kompor.api.model.response.ApiResponse;
import com.kompor.controller.AuthController;
import com.kompor.controller.KompetisiController;
import com.kompor.databinding.FragmentCreateKompetisiBinding;
import com.kompor.ui.Utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CreateKompetisiFragment extends Fragment {
    FragmentCreateKompetisiBinding binding;
    KompetisiController controller;

    ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentCreateKompetisiBinding.inflate(inflater, container, false);
        controller = new ViewModelProvider(this).get(KompetisiController.class);

        setupSpinners();
        setupDatePickers();

        changePickedImage();
        binding.ivFotoKompetisi.setOnClickListener(v -> pickImage());

        Bundle bundle = getArguments();
        String idKompetisi = null;

        if (bundle != null)
             idKompetisi = bundle.getString("idKompetisi");

        if (idKompetisi == null) {
            createKompetisi();
            binding.btnCreateKompetisi.setText("Create Kompetisi");
        } else {
            editKompetisi(idKompetisi);
            binding.btnCreateKompetisi.setText("Update Kompetisi");
            controller.getKompetisiDetails(idKompetisi);
        }

        controller.getKompetisi().observe(getViewLifecycleOwner(), this::observeDataKompetisi);

        return binding.getRoot();
    }

    private void observeDataKompetisi(ApiResponse<Kompetisi> res) {
        Kompetisi data = res.getResult();

        if (data == null) {
            Toast.makeText(requireActivity(), "Cannot get data", Toast.LENGTH_SHORT).show();
            return;
        }

        if (data.getFoto() != null && !data.getFoto().isEmpty()) {
            controller.setImage(data.getFoto());
            Glide.with(requireActivity()).load(data.getFoto()).into(binding.ivFotoKompetisi);
        }

        binding.etNamaKompetisi.setText(data.getNama_kompetisi());
        binding.etDeskripsi.setText(data.getDeskripsi());
        binding.etPendaftaranDari.setText(data.getPendaftaran_dari());
        binding.etPendaftaranSampai.setText(data.getPendaftaran_sampai());
        binding.etAnggotaPerTim.setText(String.valueOf(data.getAnggota_per_tim()));

        List<String> kategoriArray = Arrays.asList(getResources().getStringArray(R.array.kategori_array));
        List<String> tingkatArray = Arrays.asList(getResources().getStringArray(R.array.tingkat_array));

        binding.spinnerKategori.setSelection(kategoriArray.indexOf(data.getKategori()));
        binding.spinnerTingkat.setSelection(tingkatArray.indexOf(data.getTingkat()));
    }

    private void setupSpinners() {
        ArrayAdapter<CharSequence> tingkatAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.tingkat_array, android.R.layout.simple_spinner_item);
        tingkatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTingkat.setAdapter(tingkatAdapter);

        ArrayAdapter<CharSequence> kategoriAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.kategori_array, android.R.layout.simple_spinner_item);
        kategoriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerKategori.setAdapter(kategoriAdapter);
    }

    private void setupDatePickers() {
        binding.etPendaftaranDari.setInputType(InputType.TYPE_NULL);
        binding.etPendaftaranDari.setOnClickListener(v -> showDatePicker(binding.etPendaftaranDari));

        binding.etPendaftaranSampai.setInputType(InputType.TYPE_NULL);
        binding.etPendaftaranSampai.setOnClickListener(v -> showDatePicker(binding.etPendaftaranSampai));
    }

    private void showDatePicker(final TextInputEditText editText) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            editText.setText(sdf.format(selection));
        });

        datePicker.show(getParentFragmentManager(), datePicker.toString());
    }

    private void editKompetisi(String idKompetisi) {
        binding.btnCreateKompetisi.setOnClickListener(v -> {
            String namaKompetisi = binding.etNamaKompetisi.getText().toString().trim();
            String pendaftaranDari = binding.etPendaftaranDari.getText().toString().trim();
            String pendaftaranSampai = binding.etPendaftaranSampai.getText().toString().trim();
            String deskripsi = binding.etDeskripsi.getText().toString().trim();
            String tingkat = binding.spinnerTingkat.getSelectedItem().toString();
            String kategori = binding.spinnerKategori.getSelectedItem().toString();
            String anggotaPerTimStr = binding.etAnggotaPerTim.getText().toString().trim();
            String foto = controller.getImage().getValue();

            if (namaKompetisi.isEmpty() || pendaftaranDari.isEmpty() || pendaftaranSampai.isEmpty() || deskripsi.isEmpty() || anggotaPerTimStr.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Integer anggotaPerTim = Integer.parseInt(anggotaPerTimStr);

            controller.changeKompetisiDetails(idKompetisi,
                    namaKompetisi,
                    pendaftaranDari,
                    pendaftaranSampai,
                    deskripsi,
                    false,
                    tingkat,
                    anggotaPerTim,
                    kategori,
                    foto);
        });
    }

    private void createKompetisi() {
        binding.btnCreateKompetisi.setOnClickListener(v -> {
            String namaKompetisi = binding.etNamaKompetisi.getText().toString().trim();
            String pendaftaranDari = binding.etPendaftaranDari.getText().toString().trim();
            String pendaftaranSampai = binding.etPendaftaranSampai.getText().toString().trim();
            String deskripsi = binding.etDeskripsi.getText().toString().trim();
            String tingkat = binding.spinnerTingkat.getSelectedItem().toString();
            String kategori = binding.spinnerKategori.getSelectedItem().toString();
            String anggotaPerTimStr = binding.etAnggotaPerTim.getText().toString().trim();
            String foto = controller.getImage().getValue();

            if (namaKompetisi.isEmpty() || pendaftaranDari.isEmpty() || pendaftaranSampai.isEmpty() || deskripsi.isEmpty() || anggotaPerTimStr.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Integer anggotaPerTim = Integer.parseInt(anggotaPerTimStr);

            controller.createKompetisi(Objects.requireNonNull(AuthController.getLoginInfo(requireActivity())).getId(),
                    namaKompetisi,
                    pendaftaranDari,
                    pendaftaranSampai,
                    deskripsi,
                    false,
                    tingkat,
                    anggotaPerTim,
                    kategori,
                    foto);
        });
    }

    private void pickImage() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        galleryLauncher.launch(intent);
    }

    private void changePickedImage() {
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {
            try {
                Uri uri = o.getData().getData();
                binding.ivFotoKompetisi.setImageURI(uri);

                String img = Utils.uriToBase64(requireActivity(), uri);

                controller.setImage(img);
            } catch (Exception e) {
                Toast.makeText(requireActivity(), "No Image Selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}