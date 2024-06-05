package com.kompor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kompor.R;
import com.kompor.api.model.Kompetisi;

import java.util.ArrayList;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {
    private final ArrayList<Kompetisi> kompetisiList;
    OnItemClickListener onItemClickListener;
    Context context;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivCarouselItem);
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param context   Context for Glide to use
     * @param kompetisiList ArrayList<String> containing the data to populate views to be used by RecyclerView
     */
    public CarouselAdapter(Context context, ArrayList<Kompetisi> kompetisiList) {
        this.context = context;
        this.kompetisiList = kompetisiList;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.carousel_container, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Glide.with(context).load(kompetisiList.get(position).getFoto()).into(viewHolder.imageView);
        viewHolder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(viewHolder.imageView, kompetisiList.get(position)));
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

