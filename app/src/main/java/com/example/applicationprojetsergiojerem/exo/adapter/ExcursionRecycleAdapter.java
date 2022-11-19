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

    /**
     * Constructeur
     * @param context
     * @param listener
     */
    public ExcursionRecycleAdapter(Context context, RecyclerViewItemClickListener listener) {
        this.listener = listener;
        this.context = context;
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
        View view = inflater.inflate(R.layout.excursion_recycle_view, parent, false);

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Excursion item = data.get(position);

        new ImageLoadTask(item.getPicPath(), holder.ivImage).execute();

        holder.tvTitle.setText(item.getName());
        holder.tvLocations.setText(item.getLocations());
        holder.tvDistance.setText(String.valueOf(item.getDistance()) + " km");
        holder.tvDifficulty.setText(item.getDifficulty());
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
     * @param data Liste d'excursions tirée de la db
     */
    public void setData(final List<Excursion> data) {
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
                    return ExcursionRecycleAdapter.this.data.size();
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
                    return ExcursionRecycleAdapter.this.data.get(oldItemPosition).getId() == data.get(newItemPosition).getId();
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

        /**
         * Constructeur
         * @param viewItem
         */
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
