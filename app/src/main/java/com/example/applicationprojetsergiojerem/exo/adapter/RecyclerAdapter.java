package com.example.applicationprojetsergiojerem.exo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapter<G> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<G> data;
    private RecyclerViewItemClickListener listener;

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycler_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(v -> listener.onItemClick(v, viewHolder.getAdapterPosition()));
        view.setOnLongClickListener(v -> {
            listener.onItemLongClick(v, viewHolder.getAdapterPosition());
            return true;
        });

        return viewHolder;

        /*

        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> listener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            listener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;

         */
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        G item = data.get(position);

        if (item.getClass().equals(Excursion.class)) {
            holder.tvNameDetail.setText(((Excursion) item).getName());
            holder.info1.setText(((Excursion) item).getLocations());
            holder.info2.setText(((Excursion) item).getDifficulty());
        }


        if (item.getClass().equals(Guide.class)) {
            holder.tvNameDetail.setText(item.toString());
            holder.info1.setText(String.valueOf(((Guide) item).getPhoneNumber()));
            holder.info2.setText(((Guide) item).getEmail());
        }
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setData(final List<G> data) {
        if (this.data == null) {
            this.data = data;
            notifyItemRangeInserted(0, this.data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return RecyclerAdapter.this.data.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (RecyclerAdapter.this.data instanceof Excursion) {
                        return ((Excursion) RecyclerAdapter.this.data.get(oldItemPosition)).getId() == (((Excursion) data.get(newItemPosition)).getId());
                    }

                    if (RecyclerAdapter.this.data instanceof Guide) {
                        return ((Guide) RecyclerAdapter.this.data.get(oldItemPosition)).getId() == (((Guide) data.get(newItemPosition)).getId());
                    }

                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (RecyclerAdapter.this.data instanceof Excursion) {
                        Excursion newExcursion = (Excursion) data.get(newItemPosition);
                        Excursion oldExcursion = (Excursion) RecyclerAdapter.this.data.get(newItemPosition);

                        return newExcursion.getId() == (oldExcursion.getId())
                                && Objects.equals(newExcursion.getName(), oldExcursion.getName())
                                && Objects.equals(newExcursion.getDistance(), oldExcursion.getDistance())
                                && newExcursion.getLocations().equals(oldExcursion.getLocations());
                    }
                    if (RecyclerAdapter.this.data instanceof Guide) {
                        Guide newGuide = (Guide) data.get(newItemPosition);
                        Guide oldGuide = (Guide) RecyclerAdapter.this.data.get(newItemPosition);

                        return Objects.equals(newGuide.getEmail(), oldGuide.getEmail())
                                && Objects.equals(newGuide.getName(), oldGuide.getName())
                                && Objects.equals(newGuide.getLastName(), oldGuide.getLastName())
                                && (newGuide.getId() == oldGuide.getId());
                    }

                    return false;
                }
            });

            this.data = data;
            result.dispatchUpdatesTo(this);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvNameDetail, info1, info2;
        private final ImageView imageItem;

        ViewHolder(View viewItem) {
            super(viewItem);

            this.tvNameDetail = viewItem.findViewById(R.id.tvNameDetail);
            this.info1 = viewItem.findViewById(R.id.info1);
            this.info2 = viewItem.findViewById(R.id.info2);
            this.imageItem = viewItem.findViewById(R.id.imageItem);
        }
    }
}
