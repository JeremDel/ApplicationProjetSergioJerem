package viewmodel;

import android.app.Application;

import com.example.applicationprojetsergiojerem.exo.database.AppDatabase;
import com.example.applicationprojetsergiojerem.exo.database.repository.ExcursionRepository;
import com.example.applicationprojetsergiojerem.exo.database.repository.GuideRepository;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public ExcursionRepository getAccountRepository() {
        return ExcursionRepository.getInstance();
    }

    public GuideRepository getClientRepository() {
        return GuideRepository.getInstance();
    }
}
