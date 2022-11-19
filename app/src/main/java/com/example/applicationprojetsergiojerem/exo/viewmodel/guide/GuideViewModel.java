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

    public GuideViewModel(@NonNull Application application,
                            final int id, GuideRepository guideRepository){
        super(application);

        this.application = application;

        repository = guideRepository;

        observableGuide = new MediatorLiveData<>();

        observableGuide.setValue(null);

        LiveData<Guide> guide = repository.getGuideById(id, application);

        observableGuide.addSource(guide, observableGuide::setValue);
    }
    public static class Factory implements ViewModelProvider.Factory {

        @NonNull
        private Application applicationBase;

        private final int id;
        private Application application;

        private final GuideRepository repository;

        public Factory(@NonNull Application application, int id) {
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

    public LiveData<Guide> getGuide() {
        return observableGuide;
    }

    public void createGuide(Guide guide, OnAsyncEventListener callback) {
        repository.insert(guide, callback, application);
    }

    public void updateGuide(Guide guide, OnAsyncEventListener callback) {
        repository.update(guide, callback, application);
    }

    public void deleteGuide(Guide guide, OnAsyncEventListener callback) {
        repository.delete(guide, callback, application);

    }
}
