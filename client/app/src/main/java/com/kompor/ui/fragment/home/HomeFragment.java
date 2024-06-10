package com.kompor.ui.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.android.material.carousel.CarouselSnapHelper;
import com.google.android.material.carousel.HeroCarouselStrategy;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kompor.R;
import com.kompor.api.model.Kompetisi;
import com.kompor.api.model.LoginInfo;
import com.kompor.controller.AuthController;
import com.kompor.controller.KompetisiController;
import com.kompor.databinding.FragmentHomeBinding;
import com.kompor.ui.activity.MainActivity;
import com.kompor.ui.adapter.CarouselAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment {
    KompetisiController controller;
    private FragmentHomeBinding binding;

    String selectedDate;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        controller = new ViewModelProvider(this).get(KompetisiController.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        controller.getPaidKompetisi();

        ArrayList<Kompetisi> kompetisiList = new ArrayList<>();

        controller.getListKompetisi().observe(getViewLifecycleOwner(), res -> {
            ArrayList<Kompetisi> data = res.getResult();

            if (data != null) {
                kompetisiList.addAll(data);
            }
        });

        setupCarousel(kompetisiList);

        kategoriClick(binding.btnKategoriGame, "GAME");
        kategoriClick(binding.btnKategoriArt, "KESENIAN");
        kategoriClick(binding.btnKategoriIoT, "INTERNET OF THINGS");
        kategoriClick(binding.btnKategoriDebat, "DEBAT");
        kategoriClick(binding.btnKategoriUIUX, "UI/UX");
        kategoriClick(binding.btnKategoriRobotik, "ROBOTIK");
        kategoriClick(binding.btnKategoriProgramming, "SOFTWARE");
        kategoriClick(binding.btnKategoriPaper, "ESAI");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.searchBar.setOnMenuItemClickListener(openProfile());
        binding.searchBar.setOnClickListener(v -> showSearchDialog());
    }

    @NonNull
    private Toolbar.OnMenuItemClickListener openProfile() {
        return item -> {
            LoginInfo loginInfo = AuthController.getLoginInfo(requireActivity());
            Bundle bundle = new Bundle();
            bundle.putString("id", loginInfo.getId());

            if (Objects.equals(Objects.requireNonNull(loginInfo).getRole().toLowerCase(), "penyelenggara"))
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_penyelenggaraProfileFragment, bundle);
            else
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_participantProfileFragment, bundle);

            return true;
        };
    }

    private void kategoriClick(Button button, String kategori) {
        button.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("kategori", kategori.toUpperCase());

            Navigation.findNavController(v).navigate(R.id.action_navigation_home_to_navigation_kompetisi_kategori, bundle, null);
        });
    }

    public void showSearchDialog() {
        // Inflate the dialog layout
        LayoutInflater inflater = LayoutInflater.from(requireActivity());
        View dialogView = inflater.inflate(R.layout.search_dialog, null);

        // Create the dialog
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity(), com.google.android.material.R.style.ThemeOverlay_Material3_MaterialAlertDialog_Centered);
        builder.setView(dialogView);

        // Get references to the views
        ImageButton btnClose = dialogView.findViewById(R.id.btnCloseDialog);
        Spinner spinnerKategori = dialogView.findViewById(R.id.spinnerKategori);
        Spinner spinnerTingkat = dialogView.findViewById(R.id.spinnerTingkat);
        TextInputLayout etAnggota = dialogView.findViewById(R.id.etAnggota);
        Button btnJadwalPendaftaran = dialogView.findViewById(R.id.btnSearchJadwalPendaftaran);
        MaterialButton btnSearch = dialogView.findViewById(R.id.btnSearchDialog);

        // Setup spinners (assuming you have arrays defined in your resources)
        ArrayAdapter<CharSequence> adapterKategori = ArrayAdapter.createFromResource(requireActivity(), R.array.kategori_array, android.R.layout.simple_spinner_item);
        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapterKategori);

        ArrayAdapter<CharSequence> adapterTingkat = ArrayAdapter.createFromResource(requireActivity(), R.array.tingkat_array, android.R.layout.simple_spinner_item);
        adapterTingkat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTingkat.setAdapter(adapterTingkat);

        // Setup the DateRangePicker
        btnJadwalPendaftaran.setOnClickListener(v -> {
            showDatePicker(btnJadwalPendaftaran);
        });


        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Setup close button
        btnClose.setOnClickListener(v -> dialog.dismiss());

        // Setup the search button
        btnSearch.setOnClickListener(v -> {
            // Implement your search logic here
            Bundle bundle = new Bundle();

            String k = spinnerKategori.getSelectedItem().toString();
            if (!Objects.equals(k, "ALL"))
                bundle.putString("kategori", spinnerKategori.getSelectedItem().toString());
            bundle.putString("tingkat", spinnerTingkat.getSelectedItem().toString());
            bundle.putInt("anggota", etAnggota.getEditText().getText().toString().isEmpty() ? 0 : Integer.parseInt(etAnggota.getEditText().getText().toString()));
            bundle.putString("tanggal", selectedDate);

            dialog.dismiss();
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_kompetisi_kategori, bundle, null);
        });
    }

    private void showDatePicker(Button button) {
        Calendar calendar = Calendar.getInstance();
        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setEnd(calendar.getTimeInMillis());

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Jadwal Pendaftaran")
                .setCalendarConstraints(constraintBuilder.build())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            selectedDate = sdf.format(selection);
            button.setText(selectedDate);
        });

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }


    private void setupCarousel(ArrayList<Kompetisi> kompetisiList) {
        RecyclerView rv = binding.rvCarousel;

        CarouselAdapter adapter = new CarouselAdapter(requireActivity(), kompetisiList);
        CarouselSnapHelper snapHelper = new CarouselSnapHelper();
        CarouselLayoutManager carouselLayoutManager = new CarouselLayoutManager();

        carouselLayoutManager.setCarouselStrategy(new HeroCarouselStrategy());
        carouselLayoutManager.setCarouselAlignment(CarouselLayoutManager.ALIGNMENT_CENTER);

        adapter.setOnItemClickListener((view, source) -> {
            Bundle bundle = new Bundle();
            bundle.putString("source", source.getFoto());
            bundle.putString("id", source.getId_kompetisi());

            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_frKompetisiDetails, bundle, null);
        });

        rv.setLayoutManager(carouselLayoutManager);
        rv.setAdapter(adapter);

        snapHelper.attachToRecyclerView(rv);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}