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
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Guide> data;
    private RecyclerViewItemClickListener listener;

    /**
     * Constructeur
     * @param listener
     */
    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Recupère le layout choisi lors de la création de l'adapter
     * @param parent
     * @param viewType
     * @return ViewHolder avec le layout choisi. Le ViewHolder contient tous les composants du layout (TextView, EditText, ...)
     */
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

    /**
     * Gère l'assignation de l'adapter. On va modifier les composants graphiques pour leur donner le texte de l'objet recupéré.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        Guide item = data.get(position);

        holder.tvNameDetail.setText(item.toString());
        holder.info1.setText(String.valueOf(((Guide) item).getPhoneNumber()));
        holder.info2.setText(((Guide) item).getEmail());

        new ImageLoadTask(((Guide) item).getPicPath(), holder.imageItem).execute();
    }

    /**
     * Comtpe le nombre d'items dans la RecycleView
     * @return Nombre d'items dans la RecycleView
     */
    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }

    /**
     * Modifie la variable data avec la liste d'excursions recupérée de la database.
     * Si la variable data n'est pas instanciée on l'écrase, sinon on vérifie que les données sont différentes avant d'écraser les vieilles données
     * @param data
     */
    public void setData(final List<Guide> data) {
        if (this.data == null) {
            this.data = data;
            notifyItemRangeInserted(0, this.data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                /**
                 * Retourne la taille de la variable data de cette classe
                 * @return Taille de l'ancienne variable data
                 */
                @Override
                public int getOldListSize() {
                    return RecyclerAdapter.this.data.size();
                }

                /**
                 * Retourne la taille de la variable data passée en paramètre
                 * @return Taille de la variable data passée en paramètre
                 */
                @Override
                public int getNewListSize() {
                    return data.size();
                }

                /**
                 * Vérifie un item à la fois que les items dans les variables data sont différents en fonction de leur id. Un changement d'ordre fera que cette fonction retourne false.
                 * @param oldItemPosition Position à vérifier dans l'ancienne variable data
                 * @param newItemPosition Position à vérifier dans la nouvelle variable data
                 * @return true si les items sont identiques (même ID), false si les items sont différents
                 */
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                        return RecyclerAdapter.this.data.get(oldItemPosition).getId() == data.get(newItemPosition).getId();
                }

                /**
                 * Vérifie un item à la fois que le contenu des items dans les variables data sont différents en fonction de leur id. Un changement d'ordre fera que cette fonction retourne false.
                 * Cette méthode sert à vérifier si les items ont été mis à jour, même si l'ordre reste le même.
                 * @param oldItemPosition Position à vérifier dans l'ancienne variable data
                 * @param newItemPosition Position à vérifier dans la nouvelle variable data
                 * @return true si le contenu des items sont identiques, false si le contenu des items sont différents
                 */
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

        /**
         * Constructeur
         * @param viewItem
         */
        ViewHolder(View viewItem) {
            super(viewItem);

            this.tvNameDetail = viewItem.findViewById(R.id.tvNameDetail);
            this.info1 = viewItem.findViewById(R.id.info1);
            this.info2 = viewItem.findViewById(R.id.info2);
            this.imageItem = viewItem.findViewById(R.id.imageItem);
        }
    }
}
