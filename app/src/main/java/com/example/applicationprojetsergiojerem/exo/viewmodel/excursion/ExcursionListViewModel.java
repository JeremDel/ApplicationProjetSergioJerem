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
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.database.repository.ExcursionRepository;
import com.example.applicationprojetsergiojerem.exo.database.repository.GuideRepository;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

import java.util.List;

public class ExcursionListViewModel extends AndroidViewModel {

    private Application application;

    private ExcursionRepository repository;

    private final MediatorLiveData<List<Excursion>> observableExcursions;
    private final MediatorLiveData<List<Excursion>> observableOwnExcursion;

    /**
     * Constructeur
     * @param application
     * @param guideId Guide à recupérer
     * @param guideRepository
     * @param excursionRepository
     */
    public ExcursionListViewModel(@NonNull Application application,
                                  final int guideId,
                                  GuideRepository guideRepository,
                                  ExcursionRepository excursionRepository) {


        super(application);

        this.application = application;

        repository = excursionRepository;

        observableExcursions = new MediatorLiveData<>();
        observableOwnExcursion = new MediatorLiveData<>();

        observableExcursions.setValue(null);
        observableOwnExcursion.setValue(null);

        LiveData<List<Excursion>> allExcursions = (LiveData<List<Excursion>>) repository.getAllExcursions();

        // observe the changes of the entities from the database and forward them
        observableExcursions.addSource(allExcursions, observableExcursions::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;

        private final GuideRepository guideRepository;

        private final ExcursionRepository excursionRepository;

        /**
         * Constructeur de la Factory
         * @param application
         */
        public Factory(@NonNull Application application) {
            this.application = application;
            guideRepository = ((BaseApp) application).getGuideRepository();
            excursionRepository = ((BaseApp) application).getExcursionRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {

            return (T) new ExcursionListViewModel(application, 1, guideRepository, excursionRepository);
        }
    }

    /**
     * Recupère toutes les excursions
     * @return
     */
    public LiveData<List<Excursion>> getAllExcursions() {
        return observableExcursions;
    }

    /**
     * Efface une excursion de la db
     * @param excursion Excursion à effacer
     * @param callback
     */
    public void deleteExcursion(Excursion excursion, OnAsyncEventListener callback) {
        repository.delete(excursion, callback);
    }

    }