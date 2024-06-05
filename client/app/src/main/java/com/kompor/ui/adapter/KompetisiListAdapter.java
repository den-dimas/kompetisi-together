package com.kompor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.kompor.R;
import com.kompor.api.model.Kompetisi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class KompetisiListAdapter extends RecyclerView.Adapter<KompetisiListAdapter.ViewHolder> {
    private final ArrayList<Kompetisi> kompetisiList;
    OnItemClickListener onItemClickListener;
    Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView mcv;
        ImageView fotoKompetisi;
        TextView namaKompetisi;
        TextView jadwalKompetisi;
        TextView tingkatKompetisi;
        TextView kategoriKompetisi;
        TextView anggotaKompetisi;
        Button btnPendaftaran;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mcv = itemView.findViewById(R.id.mcvKompetisiListItem);
            fotoKompetisi = itemView.findViewById(R.id.ivKompetisiList);
            namaKompetisi = itemView.findViewById(R.id.tvNamaKompetisiList);
            jadwalKompetisi = itemView.findViewById(R.id.tvTanggalPendaftaranList);
            tingkatKompetisi = itemView.findViewById(R.id.tvTingkatList);
            kategoriKompetisi = itemView.findViewById(R.id.tvKategoriList);
            anggotaKompetisi = itemView.findViewById(R.id.tvAnggotaList);
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param context   Context for Glide to use
     * @param kompetisiList ArrayList<String> containing the data to populate views to be used by RecyclerView
     */
    public KompetisiListAdapter(Context context, ArrayList<Kompetisi> kompetisiList) {
        this.context = context;
        this.kompetisiList = kompetisiList;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_kompetisi_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        Kompetisi data = kompetisiList.get(position);

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

        if (data.getFoto() == null || data.getFoto().isEmpty())
            Glide.with(context).load("https://images.unsplash.com/photo-1590764258299-0f91fa7f95e8?q=80&w=2340&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D").into(viewHolder.fotoKompetisi);
        else
            Glide.with(context).load(data.getFoto()).into(viewHolder.fotoKompetisi);

        viewHolder.namaKompetisi.setText(data.getNama_kompetisi());
        viewHolder.jadwalKompetisi.setText(String.format("%s - %s", dari, sampai));

        if (data.getAnggota_per_tim() == 1)
            viewHolder.anggotaKompetisi.setText("Individu");
        else
            viewHolder.anggotaKompetisi.setText(String.format("%d Anggota", data.getAnggota_per_tim()));

        if (Objects.equals(data.getTingkat(), "ALL"))
            viewHolder.tingkatKompetisi.setText("Universitas, SMA, SMK");
        else
            viewHolder.tingkatKompetisi.setText(data.getTingkat());

        viewHolder.kategoriKompetisi.setText(data.getKategori().toUpperCase(Locale.ROOT));

        viewHolder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(viewHolder.itemView, data));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Kompetisi source);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return kompetisiList.size();
    }
}
