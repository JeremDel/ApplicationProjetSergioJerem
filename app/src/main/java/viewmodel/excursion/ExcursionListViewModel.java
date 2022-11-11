package viewmodel.excursion;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.applicationprojetsergiojerem.exo.BaseApp;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.peugeot.GuideWithExcursions;
import com.example.applicationprojetsergiojerem.exo.database.repository.ExcursionRepository;
import com.example.applicationprojetsergiojerem.exo.database.repository.GuideRepository;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

import java.util.List;

public class ExcursionListViewModel extends AndroidViewModel {

    private Application application;

    private ExcursionRepository repository;

    private final MediatorLiveData<List<GuideWithExcursions>> observableGuideExcursion;
    private final MediatorLiveData<List<Excursion>> observableOwnExcursion;

    public ExcursionListViewModel(@NonNull Application application,
                                  final int guideId,
                                  GuideRepository guideRepository,
                                  ExcursionRepository excursionRepository) {


        super(application);

        this.application = application;

        repository = excursionRepository;

        observableGuideExcursion = new MediatorLiveData<>();
        observableOwnExcursion = new MediatorLiveData<>();

        observableGuideExcursion.setValue(null);
        observableOwnExcursion.setValue(null);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;

        private final GuideRepository guideRepository;

        private final ExcursionRepository excursionRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            guideRepository = ((BaseApp) application).getGuideRepository();
            excursionRepository = ((BaseApp) application).getExcursionRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {

            return (T) new ExcursionListViewModel(application, 1, guideRepository, excursionRepository); // TODO Replace the hardcoded 1...
        }
    }

    public LiveData<List<GuideWithExcursions>> getGuideExcursion() {
        return observableGuideExcursion;
    }

    public LiveData<List<Excursion>> getOwnExcursion() {
        return observableOwnExcursion;
    }

    public void deleteExcursion(Excursion excursion, OnAsyncEventListener callback) {
        repository.delete(excursion, callback, application);
    }

    }