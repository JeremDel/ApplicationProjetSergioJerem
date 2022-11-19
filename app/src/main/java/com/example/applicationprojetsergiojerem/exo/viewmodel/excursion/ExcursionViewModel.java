package com.example.applicationprojetsergiojerem.exo.viewmodel.excursion;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.applicationprojetsergiojerem.exo.BaseApp;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.repository.ExcursionRepository;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

public class ExcursionViewModel extends AndroidViewModel {

    private Application application;

    private ExcursionRepository repository;

    private final MediatorLiveData<Excursion> observableExcursion;

    /**
     * Constructeur
     * @param application
     * @param id Excursion à recupérer
     * @param excursionRepository
     */
    public ExcursionViewModel(@NonNull Application application,
                              final int id, ExcursionRepository excursionRepository )
    {

        super(application);

        this.application = application;

        repository = excursionRepository;

        observableExcursion = new MediatorLiveData<>();

        observableExcursion.setValue(null);

        LiveData<Excursion> excursion = repository.getExcursion(id, application);

        observableExcursion.addSource(excursion, observableExcursion::setValue);
    }

    public static class Factory implements ViewModelProvider.Factory {

        @NonNull
        private final Application application;

        private final int excursionId;
        private final ExcursionRepository repository;

        /**
         * Constructeur de la Factory
         * @param application
         * @param excursionId Excursion à recupérer
         */
        public Factory(@NonNull Application application, int excursionId) {
            this.application = application;
            this.excursionId = excursionId;
            repository = ((BaseApp) application).getExcursionRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ExcursionViewModel(application, excursionId, repository);
        }
    }

    /**
     * Recupère toutes les excursions
     * @return
     */
    public LiveData<Excursion> getExcursion() {
        return observableExcursion;
    }

    /**
     * Crée une excursion
     * @param excursion Excursion à insérer dans la db
     * @param callback
     */
    public void createExcursion(Excursion excursion, OnAsyncEventListener callback) {
        repository.insert(excursion, callback, application);
    }

    /**
     * Met une excursion à jour
     * @param excursion Excursion à mettre à jour (objet excursion avec les données à jour)
     * @param callback
     */
    public void updateAccount(Excursion excursion, OnAsyncEventListener callback) {
        repository.update(excursion, callback, application);
    }

}
