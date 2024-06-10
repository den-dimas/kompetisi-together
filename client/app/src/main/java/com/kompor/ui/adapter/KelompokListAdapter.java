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
import com.kompor.api.model.kelompok.Kelompok;

import java.util.ArrayList;

public class KelompokListAdapter extends RecyclerView.Adapter<KelompokListAdapter.ViewHolder> {
    private final ArrayList<Kelompok> kelompokList;
    OnItemClickListener onItemClickListener;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView mcv;
        TextView namaKetua;
        TextView sekolahKetua;
        TextView levelKetua;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mcv = itemView.findViewById(R.id.mcvKelompokListItem);
            namaKetua = itemView.findViewById(R.id.tvNamaKetua);
            sekolahKetua = itemView.findViewById(R.id.tvSekolahKetua);
            levelKetua = itemView.findViewById(R.id.tvLevelKetua);
        }
    }

    public KelompokListAdapter(Context context, ArrayList<Kelompok> kelompokList) {
        this.context = context;
        this.kelompokList = kelompokList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_kelompok_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        Kelompok data = kelompokList.get(position);

        if (data == null) return;

        viewHolder.namaKetua.setText(String.format("Kelompok %s", data.getNama()));
        viewHolder.sekolahKetua.setText(String.format("Sekolah : %s", data.getSekolah()));
        viewHolder.levelKetua.setText(String.format("Level : %d", data.getLevel()));

        viewHolder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(viewHolder.itemView, data));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void itemChanged() {
        notifyItemInserted(getItemCount() + 1);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Kelompok data);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return kelompokList.size();
    }
}