package com.kompor.ui.fragment.kompetisi;

import android.graphics.drawable.Drawable;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kompor.R;
import com.kompor.api.model.Kompetisi;
import com.kompor.api.model.response.ApiResponse;
import com.kompor.controller.AuthController;
import com.kompor.controller.KompetisiController;
import com.kompor.databinding.FragmentKompetisiDetailsBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class KompetisiDetailsFragment extends Fragment {
    KompetisiController controller;
    FragmentKompetisiDetailsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        controller = new ViewModelProvider(this).get(KompetisiController.class);
        binding = FragmentKompetisiDetailsBinding.inflate(inflater, container, false);

        String role = Objects.requireNonNull(AuthController.getLoginInfo(requireActivity())).getRole().toLowerCase();

        Bundle bundle = getArguments();
        assert bundle != null;
        String source = bundle.getString("source");
        String id = bundle.getString("id");

        binding.fabEditKompetisi.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("idKompetisi", id);

            Navigation.findNavController(v).navigate(R.id.action_frKompetisiDetails_to_createKompetisiFragment, args, null);
        });

        binding.btnCariKelompok.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("idKompetisi", id);

            Navigation.findNavController(v).navigate(R.id.action_frKompetisiDetails_to_kelompokListFragment, args, null);
        });

        setupFotoKompetisi(source);

        controller.getKompetisiDetails(id);
        controller.getKompetisi().observe(getViewLifecycleOwner(), res -> setupKompetisiDetails(res, role));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupFotoKompetisi(String source) {
        postponeEnterTransition();

        Glide.with(this)
                .load(source)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull Target<Drawable> target, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(binding.ivKompetisiDetails);
    }

    private void setupKompetisiDetails(ApiResponse<Kompetisi> res, String role) {
        Kompetisi data = res.getResult();

        if (data == null)
            return;

        if (role.equals("penyelenggara") && Objects.requireNonNull(AuthController.getLoginInfo(requireActivity())).getId().equals(data.getId_penyelenggara())) {
            binding.btnCariKelompok.setVisibility(View.GONE);
            binding.fabEditKompetisi.setVisibility(View.VISIBLE);
        } else {
            binding.btnCariKelompok.setVisibility(View.VISIBLE);
            binding.fabEditKompetisi.setVisibility(View.GONE);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat fds = new SimpleDateFormat("dd, MMMM", new Locale("id", "ID"));

        String dari;
        String sampai;
        try {
            dari = fds.format(sdf.parse(data.getPendaftaran_dari()));
            sampai = fds.format(sdf.parse(data.getPendaftaran_sampai()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (data.getAnggota_per_tim() == 1)
            binding.btnCariKelompok.setText("Daftar Kompetisi");

        binding.tvNamaKompetisi.setText(data.getNama_kompetisi());
        binding.tvTanggalPendaftaran.setText(String.format("%s - %s", dari, sampai));
        binding.tvDeskripsi.setText(data.getDeskripsi());
        binding.tvAnggota.setText(String.format("Minimum Anggota : %d", data.getAnggota_per_tim()));
        if (Objects.equals(data.getTingkat(), "ALL"))
            binding.tvTingkat.setText("Universitas, SMA, SMK");
        else
            binding.tvTingkat.setText(data.getTingkat());
        binding.tvKategoriDetails.setText(data.getKategori().toUpperCase(Locale.ROOT));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}