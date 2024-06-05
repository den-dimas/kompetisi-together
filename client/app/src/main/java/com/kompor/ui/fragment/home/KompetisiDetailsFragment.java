package com.kompor.ui.fragment.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.format.DateFormat;
import android.transition.Transition;
import android.transition.TransitionInflater;
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

        Bundle bundle = getArguments();
        String source = bundle.getString("source");
        String id = bundle.getString("id");

        Log.d("BUNDLE", "ID : " + id);

        controller.getKompetisi().observe(getViewLifecycleOwner(), res -> {
            Kompetisi data = res.getResult();

            if (data == null)
                return;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat fds = new SimpleDateFormat("dd, MMMM", new Locale("id", "ID"));

            String dari = null;
            String sampai = null;
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
        });

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

        controller.getKompetisiDetails(id);

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
}