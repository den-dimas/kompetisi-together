package com.kompor.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.kompor.R;
import com.kompor.api.model.kelompok.KelompokDetails;

import java.util.ArrayList;

public class AnggotaKelompokListAdapter extends RecyclerView.Adapter<AnggotaKelompokListAdapter.ViewHolder> {
    private final ArrayList<KelompokDetails> anggotaKelompokList;
    AnggotaKelompokListAdapter.OnItemClickListener onItemClickListener;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView mcv;
        TextView namaAnggota;
        TextView sekolahAnggota;
        TextView levelAnggota;
        TextView isKetua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mcv = itemView.findViewById(R.id.mcvAnggotaKelompokListItem);
            namaAnggota = itemView.findViewById(R.id.tvNamaAnggota);
            sekolahAnggota = itemView.findViewById(R.id.tvSekolahAnggota);
            levelAnggota = itemView.findViewById(R.id.tvLevelAnggota);
            isKetua = itemView.findViewById(R.id.tvIsKetua);
        }
    }

    public AnggotaKelompokListAdapter(Context context, ArrayList<KelompokDetails> anggotaKelompokList) {
        this.context = context;
        this.anggotaKelompokList = anggotaKelompokList;
    }

    @NonNull
    @Override
    public AnggotaKelompokListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_anggota_kelompok_item, viewGroup, false);

        return new AnggotaKelompokListAdapter.ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull AnggotaKelompokListAdapter.ViewHolder viewHolder, final int position) {
        KelompokDetails data = anggotaKelompokList.get(position);

        if (data == null) return;

        if (data.getIsKetua())
            viewHolder.isKetua.setVisibility(View.VISIBLE);

        viewHolder.namaAnggota.setText(String.format("%s", data.getNama()));
        viewHolder.sekolahAnggota.setText(String.format("Sekolah : %s", data.getSekolah()));
        viewHolder.levelAnggota.setText(String.format("Level : %d", data.getLevel()));

        viewHolder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(viewHolder.itemView, data));
    }

    public void setOnItemClickListener(AnggotaKelompokListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, KelompokDetails data);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return anggotaKelompokList.size();
    }
}