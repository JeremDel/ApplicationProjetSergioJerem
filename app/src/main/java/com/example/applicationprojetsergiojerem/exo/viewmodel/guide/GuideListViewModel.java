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

import java.util.List;

public class GuideListViewModel extends AndroidViewModel {

    private Application application;
    private GuideRepository repository;
    private final MediatorLiveData<List<Guide>> observableGuides;

    /**
     * Constructeur
     * @param application
     * @param guideRepository
     */
    public GuideListViewModel(@NonNull Application application,
                                  GuideRepository guideRepository) {


        super(application);
        this.application = application;

        repository = guideRepository;

        observableGuides = new MediatorLiveData<>();
        observableGuides.setValue(null);

        LiveData<List<Guide>> guides = (LiveData<List<Guide>>) guideRepository.getAllGuides(application);

        observableGuides.addSource(guides, observableGuides::setValue);
    }

    /**
     * Constructeur de la Factory
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private final GuideRepository guideRepository;

        public Factory(@NonNull Application application) {
            this.application = application;

            //guideRepository = new BaseApp().getGuideRepository();
            guideRepository = ((BaseApp) application).getGuideRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {

            return (T) new GuideListViewModel(application, guideRepository);
        }
    }

    /**
     * Recup??re tous les guides de la db
     * @return
     */
    public LiveData<List<Guide>> getGuides() {
        return observableGuides;
    }

    /**
     * Efface un guide de la db
     * @param guide Guide ?? effacer
     * @param callback
     */
    public void deleteGuide(Guide guide, OnAsyncEventListener callback) {
        repository.delete(guide, callback, application);
    }

}