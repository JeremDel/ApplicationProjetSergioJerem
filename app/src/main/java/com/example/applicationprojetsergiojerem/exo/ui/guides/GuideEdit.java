package com.example.applicationprojetsergiojerem.exo.ui.guides;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.applicationprojetsergiojerem.R;
import com.example.applicationprojetsergiojerem.exo.database.async.ImageLoadTask;
import com.example.applicationprojetsergiojerem.exo.database.entity.Guide;
import com.example.applicationprojetsergiojerem.exo.ui.BaseActivity;
import com.example.applicationprojetsergiojerem.exo.util.OnAsyncEventListener;

import com.example.applicationprojetsergiojerem.exo.viewmodel.guide.GuideListViewModel;
import com.example.applicationprojetsergiojerem.exo.viewmodel.guide.GuideViewModel;

public class GuideEdit extends BaseActivity {

    private Guide guide;
    private boolean isEditMode;
    private Toast toast;

    private GuideViewModel viewModel;

    private EditText etName, etLastName, etDescription, etAddress, etEmail, etPicPath, etBirthDate, etPhoneNumber;
    private ImageView imageGuide;
    private ImageButton btnDelete;
    private Button btnTestImg, btnSave;

    /**
     * Création de la page permettant d'éditer ou d'ajouter un guide. R
     *Récupération et affichage des informations de celui-ci.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);

        }else{
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_guide);

        // Get every EditText object from the layout
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etDescription = findViewById(R.id.etDescription);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        etPicPath = findViewById(R.id.url);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etBirthDate = findViewById(R.id.etBirthDate);

        imageGuide = findViewById(R.id.imageGuide);

        btnDelete = findViewById(R.id.btnDelete);

        btnTestImg = findViewById(R.id.btnTestImg);
        btnSave = findViewById(R.id.btnSave);

        // When clicking on Save button, save changes
        btnSave.setOnClickListener(View -> {
            saveChanges(etName.getText().toString(), etLastName.getText().toString(), etDescription.getText().toString(),
                    etAddress.getText().toString(), etEmail.getText().toString(), etPicPath.getText().toString(),
                    etPhoneNumber.getText().toString().replace(" ", ""), etBirthDate.getText().toString());
            onBackPressed();
            toast.show();
        });

        // When clicking on testImg button we set the url image to the imageview
        btnTestImg.setOnClickListener(View -> {
            if (! etPicPath.getText().toString().equals(""))
                new ImageLoadTask(etPicPath.getText().toString(), imageGuide).execute();
            else
                Toast.makeText(this, "You need to insert an URL first!", Toast.LENGTH_LONG).show();
        });

        // Get guide id from the intent. If there is none, -1 is automatically assigned
        String guideId = getIntent().getStringExtra("guideID");

        // If there was no guide id in the intent, we're in create new guide mode, else we're in edit guide mode
        if (guideId == null){
            setTitle(getString(R.string.title_activity_create_guide));
            toast = Toast.makeText(this, getString(R.string.guide_created), Toast.LENGTH_LONG);
            isEditMode = false;
        } else {
            setTitle(getString(R.string.title_edit_guide));
            btnSave.setText((R.string.action_update));
            toast = Toast.makeText(this, getString(R.string.guide_eddited), Toast.LENGTH_LONG);
            isEditMode = true;
        }

        if (isEditMode){
            btnDelete.setOnClickListener(view -> {
                AlertDialog ad = new AlertDialog.Builder(this).create();
                ad.setTitle("Are you sure you want to delete this guide?");
                ad.setMessage("This action is not reversible. Once you deleted the guide there is no way to get it back.");
                ad.setCancelable(false);
                ad.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", ((dialog, which) -> deleteGuide()));
                ad.setButton(AlertDialog.BUTTON_NEGATIVE, "No", ((dialog, which) -> ad.dismiss()));

                ad.show();
            });
        }

        GuideViewModel.Factory factory = new GuideViewModel.Factory(getApplication(), guideId);
        viewModel = new ViewModelProvider(new ViewModelStore(), factory).get(GuideViewModel.class);

        // If we got a guide id, we assign the guide values to the edit text objects
        if(isEditMode){
            viewModel.getGuide().observe(this, guideEntity -> {
                if (guideEntity != null){
                    guide = guideEntity;

                    etName.setText(guide.getName());
                    etLastName.setText(guide.getLastName());
                    etBirthDate.setText(guide.getBirthdate());
                    etPhoneNumber.setText(String.valueOf(guide.getPhoneNumber()));
                    etEmail.setText(guide.getEmail());
                    etPicPath.setText(guide.getPicPath());
                    etAddress.setText(guide.getAddress());
                    etDescription.setText(guide.getDescription());

                    new ImageLoadTask(guide.getPicPath(), imageGuide).execute();
                }
            });
        }
    }

    /**
     * Efface le guide précédent.
     */
    private void deleteGuide(){
        Toast deleteToast = Toast.makeText(this, "Guide deleted", Toast.LENGTH_LONG);

        GuideListViewModel.Factory factory = new GuideListViewModel.Factory(getApplication());
        GuideListViewModel guides = new ViewModelProvider(new ViewModelStore(), (ViewModelProvider.Factory) factory).get(GuideListViewModel.class);
        guides.deleteGuide(guide, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.i("EDIT GUIDE", "Guide deleted");
                deleteToast.show();
                onBackPressed();
            }

            @Override
            public void onFailure(Exception exception) {
                Log.i("EDIT GUIDE", "Error while deleting guide", exception);
            }
        });
    }

    /**
     * Enregistre les nouvelles informations éditées du guide.
     * @param name
     * @param lastName
     * @param description
     * @param address
     * @param email
     * @param picPath
     * @param phoneNumber
     * @param birthDate
     */
    private void saveChanges(String name, String lastName, String description, String address,
                             String email, String picPath, String phoneNumber,String birthDate){
        if (isEditMode){
            guide.setName(name);
            guide.setLastName(lastName);
            guide.setDescription(description);
            guide.setAddress(address);
            guide.setEmail(email);
            guide.setPicPath(picPath);
            guide.setPhoneNumber(Integer.parseInt(phoneNumber));
            guide.setBirthdate(birthDate);

            viewModel.updateGuide(guide, new OnAsyncEventListener(){
               @Override
               public void onSuccess(){
                   Log.d("EDIT GUIDE", "Update guide success");
               }

               @Override
                public void onFailure(Exception e){
                   Log.d("EDIT GUIDE", "Update guide failure", e);
               }
            });
        } else {
            String phoneS = etPhoneNumber.getText().toString();
            int phone = Integer.parseInt(phoneS);

            guide = new Guide(phone, etBirthDate.getText().toString(),
                    etName.getText().toString(), etLastName.getText().toString(), etDescription.getText().toString(),
                    etAddress.getText().toString(), etEmail.getText().toString(), picPath);

            viewModel.createGuide(guide, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d("CREATE GUIDE", "Guide creation success");
                }

                @Override
                public void onFailure(Exception exception) {
                    Log.d("CREATE GUIDE", "Guide creation failure", exception);
                }
            });
        }
    }
}
