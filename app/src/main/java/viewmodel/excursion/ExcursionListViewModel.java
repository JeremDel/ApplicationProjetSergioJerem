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
                                  final int ownerId,
                                  GuideRepository guideRepository,
                                  ExcursionRepository excursionRepository) {


        super(application);

        this.application = application;

        repository = excursionRepository;

        observableGuideExcursion = new MediatorLiveData<>();
        observableOwnExcursion = new MediatorLiveData<>();

        observableGuideExcursion.setValue(null);
        observableOwnExcursion.setValue(null);

        LiveData<List<GuideWithExcursions>> guideExcursion =
                (LiveData<List<GuideWithExcursions>>) guideRepository.getGuideById(ownerId, application);
        LiveData<List<Excursion>> ownExcursion = (LiveData<List<Excursion>>) repository.getExcursionsByGuide(ownerId, application);

        observableGuideExcursion.addSource(ownExcursion, observableOwnExcursion::setValue);
        observableOwnExcursion.addSource(ownExcursion, observableOwnExcursion::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;

        private final int ownerId;

        private final GuideRepository guideRepository;

        private final ExcursionRepository excursionRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            this.ownerId = ownerId;
            guideRepository = ((BaseApp) application).getGuideRepository();
            excursionRepository = ((BaseApp) application).getExcursionRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {

            return (T) new ExcursionListViewModel(application, ownerId, guideRepository, excursionRepository);
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