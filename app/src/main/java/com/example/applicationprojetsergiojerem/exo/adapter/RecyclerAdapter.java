package com.example.applicationprojetsergiojerem.exo.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapter<G> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<G> mData;
    private RecyclerViewItemClickListener mListener;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ViewHolder(TextView textView) {
            super(textView);
            mTextView = textView;
        }
    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false); // TODO change recycler view
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getAdapterPosition()));
        v.setOnLongClickListener(view -> {
            mListener.onItemLongClick(view, viewHolder.getAdapterPosition());
            return true;
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        G item = mData.get(position);

        if (item.getClass().equals(Excursion.class))
            holder.mTextView.setText(((Excursion) item).getName());

        if (item.getClass().equals(Guide.class))
            holder.mTextView.setText(((Guide) item).getName() + " " + ((Guide) item).getLastName());
    }

    @Override
    public int getItemCount() {
        return (mData == null) ? 0 : mData.size();
    }

    public void setData(final List<G> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof Excursion) {
                        return ((Excursion) mData.get(oldItemPosition)).getId() == (((Excursion) data.get(newItemPosition)).getId());
                    }

                    if (mData instanceof Guide) {
                        return ((Guide) mData.get(oldItemPosition)).getId() == (((Guide) data.get(newItemPosition)).getId());
                    }

                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof Excursion) {
                        Excursion newExcursion = (Excursion) data.get(newItemPosition);
                        Excursion oldExcursion = (Excursion) mData.get(newItemPosition);

                        return newExcursion.getId() == (oldExcursion.getId())
                                && Objects.equals(newExcursion.getName(), oldExcursion.getName())
                                && Objects.equals(newExcursion.getDistance(), oldExcursion.getDistance())
                                && newExcursion.getLocations().equals(oldExcursion.getLocations());
                    }
                    if (mData instanceof Guide) {
                        Guide newGuide = (Guide) data.get(newItemPosition);
                        Guide oldGuide = (Guide) mData.get(newItemPosition);

                        return Objects.equals(newGuide.getEmail(), oldGuide.getEmail())
                                && Objects.equals(newGuide.getName(), oldGuide.getName())
                                && Objects.equals(newGuide.getLastName(), oldGuide.getLastName())
                                && (newGuide.getId() == oldGuide.getId());
                    }

                    return false;
                }
            });

            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}
