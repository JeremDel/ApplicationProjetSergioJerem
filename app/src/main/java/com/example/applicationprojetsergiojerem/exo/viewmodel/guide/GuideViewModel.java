package com.example.applicationprojetsergiojerem.exo.viewmodel.guide;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.applicationprojetsergiojerem.exo.BaseApp;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.database.repository.GuideRepository;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

public class GuideViewModel extends AndroidViewModel {

    private GuideRepository repository;

    private Application application;

    private final MediatorLiveData<Guide> observableGuide;

    /**
     * Constructeur
     * @param application
     * @param id Guide à recupérer
     * @param guideRepository
     */
    public GuideViewModel(@NonNull Application application,
                            final String id, GuideRepository guideRepository){
        super(application);

        this.application = application;

        repository = guideRepository;

        observableGuide = new MediatorLiveData<>();

        observableGuide.setValue(null);

        LiveData<Guide> guide = repository.getGuideById(id);

        observableGuide.addSource(guide, observableGuide::setValue);
    }
    public static class Factory implements ViewModelProvider.Factory {

        @NonNull
        private Application applicationBase;

        private final String id;
        private Application application;

        private final GuideRepository repository;

        /**
         * Constructeur de la Factory
         * @param application
         * @param id Guide à recupérer
         */
        public Factory(@NonNull Application application, String id) {
            this.application = application;
            this.id = id;
            repository = ((BaseApp) application).getGuideRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new GuideViewModel(application, id, repository);
        }
    }

    /**
     * Recupère un gudie
     * @return
     */
    public LiveData<Guide> getGuide() {
        return observableGuide;
    }

    /**
     * Crée un guide
     * @param guide Guide à ajouter dans la db
     * @param callback
     */
    public void createGuide(Guide guide, OnAsyncEventListener callback) {
        repository.insert(guide, callback);
    }

    /**
     * Met un guide à jour dans la db
     * @param guide Guide à mettre à jour (object guide avec les données à jour)
     * @param callback
     */
    public void updateGuide(Guide guide, OnAsyncEventListener callback) {
        repository.update(guide, callback);
    }

    /**
     * Efface un gudie de la db
     * @param guide Guide à effacer
     * @param callback
     */
    public void deleteGuide(Guide guide, OnAsyncEventListener callback) {
        repository.delete(guide, callback);

    }
}
