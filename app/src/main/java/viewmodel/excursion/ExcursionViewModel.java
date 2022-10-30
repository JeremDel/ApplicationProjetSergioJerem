package viewmodel.excursion;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.applicationprojetsergiojerem.exo.BaseApp;
import com.example.applicationprojetsergiojerem.exo.database.entity.Excursion;
import com.example.applicationprojetsergiojerem.exo.database.repository.ExcursionRepository;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

public class ExcursionViewModel extends AndroidViewModel {

    private Application application;

    private ExcursionRepository repository;

    private final MediatorLiveData<Excursion> observableExcursion;

    public ExcursionViewModel(@NonNull Application application,
                              final int id, ExcursionRepository excursionRepository )
    {

        super(application);

        this.application = application;

        repository = excursionRepository;

        observableExcursion = new MediatorLiveData<>();

        observableExcursion.setValue(null);

        LiveData<Excursion> excursion = (LiveData<Excursion>) repository.getExcursion(id, application);

        observableExcursion.addSource(excursion, observableExcursion::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int id;

        private final ExcursionRepository repository;

        public Factory(@NonNull Application application, int id) {
            this.application = application;
            this.id = id;
            repository = ((BaseApp) application).getExcursionRepository();
        }
    }

        public LiveData<Excursion> getExcursion() {
            return observableExcursion;
        }

        public void createExcursion(Excursion account, OnAsyncEventListener callback) {
            repository.insert(account, callback, application);
        }

        public void updateAccount(Excursion excursion, OnAsyncEventListener callback) {
            repository.update(excursion, callback, application);
        }

}
