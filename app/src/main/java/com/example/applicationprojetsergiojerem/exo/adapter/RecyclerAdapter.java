package com.example.applicationprojetsergiojerem.exo.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
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
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Guide> data;
    private RecyclerViewItemClickListener listener;
    private Context context;

    public RecyclerAdapter(Context context, RecyclerViewItemClickListener listener) {
        this.listener = listener;
        this.context = context;
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
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        Guide item = data.get(position);

        holder.tvNameDetail.setText(item.toString());
        holder.info1.setText(String.valueOf(((Guide) item).getPhoneNumber()));
        holder.info2.setText(((Guide) item).getEmail());

        new ImageLoadTask(((Guide) item).getPicPath(), holder.imageItem).execute();
    }

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    public void setData(final List<Guide> data) {
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
                        return RecyclerAdapter.this.data.get(oldItemPosition).getId() == data.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Guide newGuide = (Guide) data.get(newItemPosition);
                    Guide oldGuide = (Guide) RecyclerAdapter.this.data.get(newItemPosition);

                    return Objects.equals(newGuide.getEmail(), oldGuide.getEmail())
                            && Objects.equals(newGuide.getName(), oldGuide.getName())
                            && Objects.equals(newGuide.getLastName(), oldGuide.getLastName())
                            && (newGuide.getId() == oldGuide.getId());
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
