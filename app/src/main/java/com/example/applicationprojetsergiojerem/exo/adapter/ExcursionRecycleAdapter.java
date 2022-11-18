package com.example.applicationprojetsergiojerem.exo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.database.async.ImageLoadTask;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class ExcursionRecycleAdapter extends RecyclerView.Adapter<ExcursionRecycleAdapter.ViewHolder>{

    private List<Excursion> data;
    private RecyclerViewItemClickListener listener;
    private Context context;

    public ExcursionRecycleAdapter(Context context, RecyclerViewItemClickListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.excursion_recycle_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(v -> listener.onItemClick(v, viewHolder.getAdapterPosition()));
        view.setOnLongClickListener(v -> {
            listener.onItemLongClick(v, viewHolder.getAdapterPosition());
            return true;
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Excursion item = data.get(position);

        new ImageLoadTask(item.getPicPath(), holder.ivImage).execute();

        holder.tvTitle.setText(item.getName());
        holder.tvLocations.setText(item.getLocations());
        holder.tvDistance.setText(String.valueOf(item.getDistance()) + " km");
        holder.tvDifficulty.setText(item.getDifficulty());
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setData(final List<Excursion> data) {
        if (this.data == null) {
            this.data = data;
            notifyItemRangeInserted(0, this.data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ExcursionRecycleAdapter.this.data.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ExcursionRecycleAdapter.this.data.get(oldItemPosition).getId() == data.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Excursion newExcursion = (Excursion) data.get(newItemPosition);
                    Excursion oldExcursion = (Excursion) ExcursionRecycleAdapter.this.data.get(newItemPosition);

                    return newExcursion.getId() == (oldExcursion.getId())
                            && Objects.equals(newExcursion.getName(), oldExcursion.getName())
                            && Objects.equals(newExcursion.getDistance(), oldExcursion.getDistance())
                            && newExcursion.getLocations().equals(oldExcursion.getLocations());
                }
            });

            this.data = data;
            result.dispatchUpdatesTo(this);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle, tvLocations, tvDistance, tvDifficulty;
        private final ImageView ivImage;

        ViewHolder(View viewItem) {
            super(viewItem);

            this.tvTitle = viewItem.findViewById(R.id.tvTitle);
            this.tvLocations = viewItem.findViewById(R.id.tvLocations);
            this.tvDistance = viewItem.findViewById(R.id.tvDistance);
            this.tvDifficulty = viewItem.findViewById(R.id.tvDifficulty);

            this.ivImage = viewItem.findViewById(R.id.ivImage);
        }
    }
}
